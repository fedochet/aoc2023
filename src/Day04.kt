import kotlin.math.pow

object Day04 {
    val name: String = this::class.simpleName!!

    class Game(val winningNumbers: Set<Int>, val guesses: Set<Int>)

    fun parseNumbers(numbers: String): Set<Int> {
        return numbers.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    }

    fun parseGame(game: String): Game {
        val (_, numbersStr) = game.split(":")
        val (winningStr, guessesStr) = numbersStr.split("|")

        val winning = parseNumbers(winningStr)
        val guesses = parseNumbers(guessesStr)

        return Game(winning, guesses)
    }

    fun countMatches(game: Game): Int {
        return game.guesses.count { it in game.winningNumbers }
    }

    fun countGameScore(game: Game): Int {
        val goodGuesses = countMatches(game)
        if (goodGuesses == 0) return 0

        return 2.0.pow(goodGuesses - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        val games = input.map(::parseGame)
        return games.sumOf(::countGameScore)
    }

    fun part2(input: List<String>): Int {
        val games = input.map(::parseGame)
        val scores = MutableList(games.size) { 1 }

        for ((idx, game) in games.withIndex()) {
            val currentScore = scores[idx]

            val matches = countMatches(game)

            for (next in 1..matches) {
                if (idx + next > scores.lastIndex) break

                scores[idx + next] += currentScore
            }
        }

        return scores.sum()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput("${name}_test")
        check(part1(testInput) == 13)
        check(part2(testInput) == 30)

        val input = readInput(name)
        part1(input).println()
        part2(input).println()
    }
}
