object Day03 {
    val name: String = this::class.simpleName!!

    data class Location(val row: Int, val col: Int) {
        val neighbours: Sequence<Location> = sequence {
            yield(Location(row - 1, col - 1))
            yield(Location(row - 1, col))
            yield(Location(row - 1, col + 1))
            yield(Location(row, col - 1))
            yield(Location(row, col + 1))
            yield(Location(row + 1, col - 1))
            yield(Location(row + 1, col))
            yield(Location(row + 1, col + 1))
        }
    }

    object Tiles {
        const val EMPTY_SPACE = '.'
        const val COG = '*'
    }

    data class Part(val tile: Char, val location: Location)

    data class PartNumber(val value: Int, val locations: Set<Location>)

    class EngineSchematic(val parts: Set<Part>, private val locationToNumber: Map<Location, PartNumber>) {
        fun partNumberAt(location: Location): PartNumber? = locationToNumber[location]
    }

    fun parseEngineSchematics(input: List<String>): EngineSchematic {
        val parts = mutableSetOf<Part>()
        val locationToNumber = mutableMapOf<Location, PartNumber>()

        for ((row, rowStr) in input.withIndex()) {

            val currentNumber = StringBuilder()
            val currentNumberLocations = mutableSetOf<Location>()

            fun saveDigit(location: Location, digit: Char) {
                currentNumber.append(digit)
                currentNumberLocations.add(location)
            }

            fun endDigit() {
                if (currentNumber.isEmpty()) return

                val partNumber = currentNumber.toString().toInt()
                val partLocations = currentNumberLocations.toSet()

                for (numberLoc in currentNumberLocations) {
                    locationToNumber.put(numberLoc, PartNumber(partNumber, partLocations))
                }

                currentNumber.clear()
                currentNumberLocations.clear()
            }

            for ((col, symbol) in rowStr.withIndex()) {
                val location = Location(row, col)

                if (symbol.isDigit()) {
                    saveDigit(location, symbol)
                } else {
                    endDigit()
                }

                if (!symbol.isDigit() && symbol != Tiles.EMPTY_SPACE) {
                    parts.add(Part(symbol, location))
                }
            }

            endDigit()
        }

        return EngineSchematic(parts, locationToNumber)
    }


    fun part1(input: List<String>): Int {
        val schematics = parseEngineSchematics(input)

        val adjacentPartNumbers = mutableSetOf<PartNumber>()

        for (part in schematics.parts) {
            for (neighbour in part.location.neighbours) {
                val partNumberAt = schematics.partNumberAt(neighbour) ?: continue
                adjacentPartNumbers += partNumberAt
            }
        }

        return adjacentPartNumbers.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val schematics = parseEngineSchematics(input)

        var sumOfRatios = 0

        for (part in schematics.parts) {
            if (part.tile != Tiles.COG) continue

            val numbersAround = part.location.neighbours.mapNotNull { schematics.partNumberAt(it) }.toSet()
            if (numbersAround.size != 2) continue

            val ratio = numbersAround.map { it.value }.reduce(Int::times)
            sumOfRatios += ratio
        }

        return sumOfRatios
    }

    @JvmStatic
    fun main(args: Array<String>) {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput("${name}_test")
        check(part1(testInput) == 4361)
        check(part2(testInput) ==  467835)

        val input = readInput(name)
        part1(input).println()
        part2(input).println()
    }
}
