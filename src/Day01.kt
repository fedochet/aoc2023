fun main() {
    fun extractCalibrationValue(line: String): Int {
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }

        return "$first$last".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            extractCalibrationValue(line)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
}
