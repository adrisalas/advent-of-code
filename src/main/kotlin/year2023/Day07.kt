package year2023

fun main() {
    val input = readInput("Day07")
    Day07.part1(input).println()
    Day07.part2(input).println()
}


object Day07 {
    fun part1(input: List<String>): Long {
        val cardValues = { card: Char ->
            when (card) {
                'A' -> 14
                'K' -> 13
                'Q' -> 12
                'J' -> 11
                'T' -> 10
                '9' -> 9
                '8' -> 8
                '7' -> 7
                '6' -> 6
                '5' -> 5
                '4' -> 4
                '3' -> 3
                '2' -> 2
                else -> 0
            }
        }
        val typeGenerator = { cards: List<Char> ->
            val cardCount = HashMap<Char, Int>()
            cards.forEach {
                cardCount[it] = (cardCount[it] ?: 0) + 1
            }
            val counts = cardCount.values
            when {
                counts.contains(5) -> Type.FIVE_OF_A_KIND
                counts.contains(4) -> Type.FOUR_OF_A_KIND
                counts.contains(3) && counts.contains(2) -> Type.FULL_HOUSE
                counts.contains(3) -> Type.THREE_OF_A_KIND
                counts.filter { it == 2 }.size == 2 -> Type.TWO_PAIR
                counts.filter { it == 2 }.size == 1 -> Type.ONE_PAIR
                else -> Type.HIGH_CARD
            }
        }

        return input
            .map {
                val parts = it.split(" ")
                val hand = Hand(parts[0].toCharArray().toList(), cardValues, typeGenerator)
                val bid = parts[1].toLong()
                Pair(hand, bid)
            }
            .sortedBy { it.first }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum()
    }

    data class Hand(
        val cards: List<Char>,
        private val cardValues: (Char) -> Int,
        private val typeGenerator: (List<Char>) -> Type
    ) : Comparable<Hand> {
        val type: Type = typeGenerator(cards)

        override fun compareTo(other: Hand): Int {
            if (this.type != other.type) {
                return this.type.value.compareTo(other.type.value)
            }
            for (i in cards.indices) {
                val compareCards = this.getCardValue(i).compareTo(other.getCardValue(i))
                if (compareCards != 0) {
                    return compareCards
                }
            }

            return 0
        }

        private fun getCardValue(position: Int): Int {
            val card = cards[position]
            return cardValues(card)
        }
    }

    enum class Type(val value: Int) {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1)
    }

    fun part2(input: List<String>): Long {
        val cardValues = { card: Char ->
            when (card) {
                'A' -> 14
                'K' -> 13
                'Q' -> 12
                'T' -> 10
                '9' -> 9
                '8' -> 8
                '7' -> 7
                '6' -> 6
                '5' -> 5
                '4' -> 4
                '3' -> 3
                '2' -> 2
                'J' -> 1
                else -> 0
            }
        }
        val typeGenerator = { cards: List<Char> ->
            val cardCount = HashMap<Char, Int>()
            cards.forEach {
                cardCount[it] = (cardCount[it] ?: 0) + 1
            }

            val cardWithHighestRepetition = cardCount.entries
                .filter { it.key != 'J' }
                .maxByOrNull { it.value }
                ?.key
                ?: 'J'
            val howManyJs = cardCount['J'] ?: 0
            cardCount['J'] = 0

            cardCount[cardWithHighestRepetition] = (cardCount[cardWithHighestRepetition] ?: 0) + howManyJs

            val counts = cardCount.values
            when {
                counts.contains(5) -> Type.FIVE_OF_A_KIND
                counts.contains(4) -> Type.FOUR_OF_A_KIND
                counts.contains(3) && counts.contains(2) -> Type.FULL_HOUSE
                counts.contains(3) -> Type.THREE_OF_A_KIND
                counts.filter { it == 2 }.size == 2 -> Type.TWO_PAIR
                counts.filter { it == 2 }.size == 1 -> Type.ONE_PAIR
                else -> Type.HIGH_CARD
            }
        }

        return input
            .map {
                val parts = it.split(" ")
                val hand = Hand(parts[0].toCharArray().toList(), cardValues, typeGenerator)
                val bid = parts[1].toLong()
                Pair(hand, bid)
            }
            .sortedBy { it.first }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum()
    }
}

