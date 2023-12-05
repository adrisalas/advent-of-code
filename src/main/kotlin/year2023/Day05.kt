package year2023

fun main() {
    val input = readInput("Day05")
    Day05.part1(input).println()
    Day05.part2(input).println()
}

object Day05 {

    fun part1(input: List<String>): Long {
        val seeds = input[0]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        val seedsToSoil = mutableSetOf<Decoder>()
        val soilsToFertilizer = mutableSetOf<Decoder>()
        val fertilizerToWater = mutableSetOf<Decoder>()
        val waterToLight = mutableSetOf<Decoder>()
        val lightToTemperature = mutableSetOf<Decoder>()
        val temperatureToHumidity = mutableSetOf<Decoder>()
        val humidityToLocation = mutableSetOf<Decoder>()

        var mode = MODE.SEEDS_TO_SOIL

        var i = 2
        while (i < input.size - 1) {
            i++
            val line = input[i].trim()

            if (line.isBlank()) continue
            if (line in SPECIAL_LINES) {
                mode = line.extractMode()
                continue
            }

            val (c1, c2, c3) = line.split(" ")
            val decoder = Decoder(
                from = c2.toLong(),
                to = c1.toLong(),
                quantity = c3.toLong()
            )

            when (mode) {
                MODE.SEEDS_TO_SOIL -> seedsToSoil.add(decoder)
                MODE.SOIL_TO_FERTILIZER -> soilsToFertilizer.add(decoder)
                MODE.FERTILIZER_TO_WATER -> fertilizerToWater.add(decoder)
                MODE.WATER_TO_LIGHT -> waterToLight.add(decoder)
                MODE.LIGHT_TO_TEMPERATURE -> lightToTemperature.add(decoder)
                MODE.TEMPERATURE_TO_HUMIDITY -> temperatureToHumidity.add(decoder)
                MODE.HUMIDITY_TO_LOCATION -> humidityToLocation.add(decoder)
            }
        }

        return seeds
            .asSequence()
            .map { seedsToSoil.decode(it) }
            .map { soilsToFertilizer.decode(it) }
            .map { fertilizerToWater.decode(it) }
            .map { waterToLight.decode(it) }
            .map { lightToTemperature.decode(it) }
            .map { temperatureToHumidity.decode(it) }
            .map { humidityToLocation.decode(it) }
            .min()
    }

    private val SPECIAL_LINES = listOf(
        "seed-to-soil map:",
        "soil-to-fertilizer map:",
        "fertilizer-to-water map:",
        "water-to-light map:",
        "light-to-temperature map:",
        "temperature-to-humidity map:",
        "humidity-to-location map:"
    )

    private enum class MODE {
        SEEDS_TO_SOIL,
        SOIL_TO_FERTILIZER,
        FERTILIZER_TO_WATER,
        WATER_TO_LIGHT,
        LIGHT_TO_TEMPERATURE,
        TEMPERATURE_TO_HUMIDITY,
        HUMIDITY_TO_LOCATION
    }

    private fun String.extractMode(): MODE {
        return when (this) {
            SPECIAL_LINES[0] -> MODE.SEEDS_TO_SOIL
            SPECIAL_LINES[1] -> MODE.SOIL_TO_FERTILIZER
            SPECIAL_LINES[2] -> MODE.FERTILIZER_TO_WATER
            SPECIAL_LINES[3] -> MODE.WATER_TO_LIGHT
            SPECIAL_LINES[4] -> MODE.LIGHT_TO_TEMPERATURE
            SPECIAL_LINES[5] -> MODE.TEMPERATURE_TO_HUMIDITY
            SPECIAL_LINES[6] -> MODE.HUMIDITY_TO_LOCATION
            else -> error("Bad input")
        }
    }

    private data class Decoder(val from: Long, val quantity: Long, val to: Long)

    private fun Decoder.decode(value: Long): Long {
        return to + value - from
    }

    private fun Set<Decoder>.decode(value: Long): Long {
        return this
            .firstOrNull {
                val (from, quantity, _) = it
                val end = from + quantity
                value in from..end
            }
            ?.decode(value)
            ?: value
    }

    fun part2(input: List<String>): Long {
        var rangedSeeds = input[0]
            .split(":")[1]
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
            .chunked(2)
            .map { Pair(it[0], it[0] + it[1]) }

        val seedsToSoil = mutableSetOf<Decoder>()
        val soilsToFertilizer = mutableSetOf<Decoder>()
        val fertilizerToWater = mutableSetOf<Decoder>()
        val waterToLight = mutableSetOf<Decoder>()
        val lightToTemperature = mutableSetOf<Decoder>()
        val temperatureToHumidity = mutableSetOf<Decoder>()
        val humidityToLocation = mutableSetOf<Decoder>()

        var mode = MODE.SEEDS_TO_SOIL

        var i = 2
        while (i < input.size - 1) {
            i++
            val line = input[i].trim()

            if (line.isBlank()) continue
            if (line in SPECIAL_LINES) {
                mode = line.extractMode()
                continue
            }

            val (c1, c2, c3) = line.split(" ")
            val decoder = Decoder(
                from = c2.toLong(),
                to = c1.toLong(),
                quantity = c3.toLong()
            )

            when (mode) {
                MODE.SEEDS_TO_SOIL -> seedsToSoil.add(decoder)
                MODE.SOIL_TO_FERTILIZER -> soilsToFertilizer.add(decoder)
                MODE.FERTILIZER_TO_WATER -> fertilizerToWater.add(decoder)
                MODE.WATER_TO_LIGHT -> waterToLight.add(decoder)
                MODE.LIGHT_TO_TEMPERATURE -> lightToTemperature.add(decoder)
                MODE.TEMPERATURE_TO_HUMIDITY -> temperatureToHumidity.add(decoder)
                MODE.HUMIDITY_TO_LOCATION -> humidityToLocation.add(decoder)
            }
        }

        rangedSeeds = seedsToSoil.decode(rangedSeeds)
        rangedSeeds = soilsToFertilizer.decode(rangedSeeds)
        rangedSeeds = fertilizerToWater.decode(rangedSeeds)
        rangedSeeds = waterToLight.decode(rangedSeeds)
        rangedSeeds = lightToTemperature.decode(rangedSeeds)
        rangedSeeds = temperatureToHumidity.decode(rangedSeeds)
        rangedSeeds = humidityToLocation.decode(rangedSeeds)

        return rangedSeeds.minOfOrNull { it.first } ?: 46
    }


    private fun Set<Decoder>.decode(rangedSeeds: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
        val pairs = mutableListOf<Pair<Long, Long>>()
        for (decoder in this) {
            val (from, quantity, _) = decoder
            for (range in rangedSeeds) {
                val intersectionMin = maxOf(from, range.first)
                val intersectionMax = minOf(from + quantity, range.second)
                if (intersectionMin > intersectionMax) {
                    continue
                }
                pairs.add(Pair(decoder.decode(intersectionMin), decoder.decode(intersectionMax)))
            }
        }
        return pairs
    }
}

