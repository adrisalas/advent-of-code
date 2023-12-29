package year2023

import year2023.Day20.Pulse.HIGH
import year2023.Day20.Pulse.LOW

fun main() {
    val input = readInput("Day20")
    Day20.part1(input).println()
    Day20.part2(input).println()
}

//832957356
//240162699605221
object Day20 {
    fun part1(input: List<String>): Int {
        val dataStructures = initDataStructures(input)
        val startButton = Button()
        startButton.receivers.forEach { dataStructures.usageCount[it] = (dataStructures.usageCount[it] ?: 0) + 1 }

        return (1..1000)
            .map { round -> startButton.press(round, dataStructures) }
            .fold(0 to 0) { (sumHigh, sumLow), (high, low) ->
                sumHigh + high to sumLow + low
            }
            .let { (high, low) -> high * low }
    }

    fun part2(input: List<String>): Long {
        val (dictionary, _, _) = initDataStructures(input)

        val flipFlops = dictionary.values.filterIsInstance<FlipFlop>().map { it.name }.distinct()
        val binaryCounterResults =
            dictionary["broadcaster"]
                ?.receivers
                ?.map { name ->
                    val start = dictionary[name]
                    val conjunction = start?.receivers?.first { dictionary[it] is Conjunction }
                    generateSequence(start) { module -> dictionary[module.receivers.firstOrNull { it in flipFlops }] }
                        .map { if (conjunction in it.receivers) 1 else 0 }
                        .foldIndexed(0L) { index, acc, i -> acc + (i shl index) }
                }
        return binaryCounterResults?.lcm() ?: 0L
    }

    private data class DataStructureWrapper(
        val dictionary: Map<String, Module>,
        val queue: ArrayDeque<Signal>,
        val usageCount: MutableMap<String, Int>
    )

    private fun initDataStructures(input: List<String>): DataStructureWrapper {
        val dictionary = input
            .map { Pair(it.split(" -> ")[0], it.split(" -> ")[1].split(", ")) }
            .map {
                val name = it.first.drop(1)
                val downstream = it.second
                when (it.first[0]) {
                    '%' -> FlipFlop(name, downstream)
                    '&' -> Conjunction(name, downstream)
                    else -> BroadCaster(downstream)
                }
            }
            .associateBy { it.name }

        val usageCount = mutableMapOf<String, Int>()
        dictionary.values.forEach { module ->
            module.receivers.forEach {
                usageCount[it] = (usageCount[it] ?: 0) + 1
            }
        }

        return DataStructureWrapper(
            dictionary = dictionary,
            queue = ArrayDeque(),
            usageCount = usageCount
        )
    }

    private enum class Pulse { HIGH, LOW }

    private data class Signal(
        val sender: String,
        val receivers: List<String>,
        val pulse: Pulse,
        val dataStructures: DataStructureWrapper
    ) {
        fun send(round: Int) {
            receivers.forEach { name -> dataStructures.dictionary[name]?.processSignal(this, round) }
        }
    }

    private interface Module {
        val name: String
        val receivers: List<String>

        fun processSignal(signal: Signal, round: Int)
    }

    private data class Button(
        override val name: String = "button",
        override val receivers: List<String> = listOf("broadcaster")
    ) : Module {

        override fun processSignal(signal: Signal, round: Int) {
            val out = Signal(name, receivers, LOW, signal.dataStructures)

            signal.dataStructures.queue.add(out)
        }

        fun press(
            round: Int,
            dataStructures: DataStructureWrapper
        ): Pair<Int, Int> {
            val firstSignal = Signal(name, receivers, LOW, dataStructures)

            processSignal(firstSignal, 0)

            return generateSequence { dataStructures.queue.removeFirstOrNull() }
                .fold(0 to 0) { (high, low), signal ->
                    signal.send(round)
                    if (signal.pulse == HIGH) {
                        high + signal.receivers.size to low
                    } else {
                        high to low + signal.receivers.size
                    }
                }
        }
    }

    private data class BroadCaster(
        override val receivers: List<String>
    ) : Module {
        override val name = "broadcaster"

        override fun processSignal(signal: Signal, round: Int) {
            val out = Signal(name, receivers, signal.pulse, signal.dataStructures)

            signal.dataStructures.queue.add(out)
        }
    }

    private data class FlipFlop(
        override val name: String,
        override val receivers: List<String>,
        private var on: Boolean = false
    ) : Module {

        override fun processSignal(signal: Signal, round: Int) {
            if (signal.pulse == LOW) {
                on = !on
                val pulse = if (on) HIGH else LOW
                val out = Signal(name, receivers, pulse, signal.dataStructures)


                signal.dataStructures.queue.add(out)
            }
        }
    }

    private data class Conjunction(
        override val name: String,
        override val receivers: List<String>
    ) : Module {
        private val previousPulses = mutableMapOf<String, Pulse>()

        override fun processSignal(signal: Signal, round: Int) {
            val countModulesCallingMe = signal.dataStructures.usageCount[name]

            previousPulses[signal.sender] = signal.pulse
            val isLOW = previousPulses.values.all { it == HIGH } && countModulesCallingMe == previousPulses.size
            val pulse = if (isLOW) LOW else HIGH
            val out = Signal(name, receivers, pulse, signal.dataStructures)

            signal.dataStructures.queue.add(out)
        }
    }

}

