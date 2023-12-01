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

    val strToDigit: Map<String, Int> = buildMap {
        fun putDigit(digit: Int, vararg strings: String) {
            for (s in strings) {
                put(s, digit)
            }
        }

        putDigit(1, "1", "one")
        putDigit(2, "2", "two")
        putDigit(3, "3", "three")
        putDigit(4, "4", "four")
        putDigit(5, "5", "five")
        putDigit(6, "6", "six")
        putDigit(7, "7", "seven")
        putDigit(8, "8", "eight")
        putDigit(9, "9", "nine")
    }

    fun extractCalibrationValueAdvanced(line: String): Int {
        fun error(): Nothing = error("No digits in '$line'")

        val (_, firstKey) = line.findAnyOf(strToDigit.keys) ?: error()
        val (_, lastKey) = line.findLastAnyOf(strToDigit.keys) ?: error()

        val first = strToDigit.getValue(firstKey)
        val last = strToDigit.getValue(lastKey)

        return "$first$last".toInt()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            extractCalibrationValueAdvanced(line)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 142)
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part2(input).println()
}
