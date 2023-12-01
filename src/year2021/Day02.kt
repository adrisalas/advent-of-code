package year2021

private enum class Direction(val value: String) {
    FORWARD("forward"), DOWN("down"), UP("up");

    companion object {
        infix fun from(value: String): Direction? = entries.firstOrNull { it.value == value }
    }
}

private class Command(val direction: Direction, val unit: Int)

private fun part1(commands: List<Command>): Int {
    var depth = 0
    var horizontal = 0
    for (command in commands) {
        when (command.direction) {
            Direction.FORWARD -> horizontal += command.unit
            Direction.DOWN -> depth += command.unit
            Direction.UP -> depth = if (depth - command.unit > 0) (depth - command.unit) else (0)
        }
    }
    return depth * horizontal
}

private fun part2(commands: List<Command>): Int {
    var depth = 0
    var horizontal = 0
    var aim = 0
    for (command in commands) {
        when (command.direction) {
            Direction.FORWARD -> {
                horizontal += command.unit
                depth += command.unit * aim
            }

            Direction.DOWN -> aim += command.unit
            Direction.UP -> aim -= command.unit
        }
    }
    return depth * horizontal
}

private fun String.firstWord() = this.split(" ")[0]
private fun String.secondWord() = this.split(" ")[1]

fun main() {
    val testCommands = readInput("Day02_test").map {
        Command(Direction.from(it.firstWord())!!, it.secondWord().toInt())
    }
    check(part1(testCommands) == 150)
    check(part2(testCommands) == 900)

    val commands = readInput("Day02").map {
        Command(Direction.from(it.firstWord())!!, it.secondWord().toInt())
    }
    println(part1(commands))
    println(part2(commands))
}