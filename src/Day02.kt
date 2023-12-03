fun main() {
    fun Map<String, Int>.fitsInto(other: Map<String, Int>): Boolean =
        this.all { (color, count) ->
            other.getValue(color) >= count
        }

    fun unite(first: Map<String, Int>, second: Map<String, Int>): Map<String, Int> {
        val allKeys = first.keys + second.keys

        return allKeys.associateWith { key ->
            listOfNotNull(first[key], second[key]).max()
        }
    }

    fun parseSingleAttempt(attempt: String): Map<String, Int> {
        val balls = attempt.split(",").map { it.trim() }

        return balls.associate {
            val (count, color) = it.split(" ")
            color to count.toInt()
        }
    }

    fun Map<String, Int>.power(): Int = values.reduce(Int::times)

    fun parseGame(game: String): Pair<Int, List<Map<String, Int>>> {
        val (gameName, triesEncoded) = game.split(":")

        val gameId = gameName.removePrefix("Game").trim().toInt()
        val tries = triesEncoded.split(";").map { parseSingleAttempt(it) }

        return gameId to tries
    }

    fun part1(input: List<String>): Int {
        val totalGameBag = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )

        var sum = 0

        for (game in input) {
            val (id, tries) = parseGame(game)
            val commonBag = tries.reduce(::unite)

            if (commonBag.fitsInto(totalGameBag)) {
                sum += id
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val allBags = input.map { game ->
            val (id, tries) = parseGame(game)
            tries.reduce(::unite)
        }

        return allBags.sumOf { it.power() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) ==  8)
    check(part2(testInput) ==  2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
