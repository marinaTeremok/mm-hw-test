package utils

import kotlin.math.round

fun calculateRating(overall:String, numberOfRatings: String): String{
    //val df = DecimalFormat("#.##")
    //df.roundingMode = RoundingMode.CEILING
    //return round((overall?.toDouble()*10)/numberOfRatings?.toDouble())

    return (overall?.toDouble()/10).format(1)
}

fun convertLowestPricePerNightToEur(value: String, currency: String, conversionRate: Double): Double{
    when(currency?.toUpperCase()){
        "VEF" -> return round(value?.toDouble()/conversionRate)
        else -> {
            return value?.toDouble()
        }
    }
}

fun Double.format(digits: Int): String = this.asDynamic().toFixed(digits)
