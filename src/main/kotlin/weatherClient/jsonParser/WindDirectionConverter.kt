package weatherClient.jsonParser

class WindDirectionConverter {

    fun windDirCalculate(windDir: Int): String {
        return when (windDir) {
            in 0..22, in 319..360 -> "NORTH"
            in 23..68 -> "NORTH-EAST"
            in 69..112 -> "EAST"
            in 113..158 -> "SOUTH-EAST"
            in 159..202 -> "SOUTH"
            in 203..248 -> "SOUTH-WEST"
            in 249..292 -> "WEST"
            else -> "NORTH-WEST"
        }
    }
}