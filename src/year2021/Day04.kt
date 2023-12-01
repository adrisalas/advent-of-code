package year2021

private class Box(val number: Int) {
    var checked = false
        private set

    fun check() {
        this.checked = true
    }
}

private class Board(val rows: List<List<Box>>) {
    fun checkNumber(number: Int) {
        rows.forEach { row ->
            row.forEach { box ->
                if (box.number == number) {
                    box.check()
                }
            }
        }
    }

    fun checkWin(): Boolean {
        var won = false

        rows.forEach { row ->
            if (row.all { it.checked }) won = true
        }

        if (!won) {
            for (i in rows.indices) {
                var column = true
                for (row in rows) {
                    column = column && row[i].checked
                }
                won = column
                if (won) {
                    break
                }
            }
        }

        return won
    }
}

private fun part1(input: List<String>): Int {
    val numbers = readNumbers(input)
    val boards: List<Board> = readBoards(input)

    for (number in numbers) {
        boards.forEach { it.checkNumber(number) }
        val board = boards.firstOrNull { it.checkWin() }

        if (board != null) {
            return sumUnmarkedNumbers(board) * number
        }
    }

    throw RuntimeException("No board won")
}

fun readNumbers(input: List<String>): List<Int> {
    return input[0]
        .split(",")
        .map { Integer.valueOf(it) }
}

private fun readBoards(input: List<String>): List<Board> {
    val boards: MutableList<Board> = ArrayList()
    var boardBuffer: MutableList<MutableList<Box>> = ArrayList()

    for (i in 2 until input.size) {
        if (input[i].isNotEmpty()) {
            val line: MutableList<Box> = ArrayList()
            input[i].split(" ")
                .filter { it != "" }
                .forEach { line.add(Box(Integer.valueOf(it))) }
            boardBuffer.add(line)
        } else {
            boards.add(Board(boardBuffer))
            boardBuffer = ArrayList()
        }
    }

    if (boardBuffer.isNotEmpty()) {
        boards.add(Board(boardBuffer))
    }

    return boards
}

private fun sumUnmarkedNumbers(board: Board): Int {
    return board.rows.fold(0) { boardAcc, row ->
        boardAcc + row.fold(0) { rowAcc, box ->
            if (!box.checked) (rowAcc + box.number) else rowAcc
        }
    }
}

private fun part2(input: List<String>): Int {
    val numbers = readNumbers(input)
    var boards: List<Board> = readBoards(input)

    var lastBoardToWinScore: Int? = null
    var lastNumberToWin: Int? = null
    for (number in numbers) {
        boards.forEach { it.checkNumber(number) }
        val board = boards.firstOrNull { it.checkWin() }

        if (board != null) {
            lastBoardToWinScore = sumUnmarkedNumbers(board)
            lastNumberToWin = number
        }
        boards = boards.filter { !it.checkWin() }
    }

    if (lastBoardToWinScore == null || lastNumberToWin == null) {
        throw RuntimeException("No board won")
    }

    return lastBoardToWinScore * lastNumberToWin
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
