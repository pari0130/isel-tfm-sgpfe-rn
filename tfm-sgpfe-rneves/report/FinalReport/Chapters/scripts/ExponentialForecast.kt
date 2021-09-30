package isel.meic.tfm.fei.forecast

import java.math.BigDecimal

class ExponentialForecast {

    companion object {
        private const val NUMBER_OF_DIGITS_AFTER_DECIMAL_POINT: Int = 2

        fun singleExponentialForecast(data: List<Double>, alpha: Double, numForecasts: Int): List<Double> {
            val forecast = ArrayList<Double>()
            forecast.add(round(data[0]))
            var i = 1
            while (i < data.size) {
                forecast.add(round(alpha * data[i - 1] + (1 - alpha) * forecast[i - 1]))
                i++
            }
            var j = 0
            while (j < numForecasts) {
                forecast.add(round(alpha * data[data.size - 1] + (1 - alpha) * forecast[i - 1]))
                j++
                i++
            }
            return forecast
        }

        private fun round(value: Double): Double {
            var bigDecimal = BigDecimal(value)
            bigDecimal = bigDecimal.setScale(NUMBER_OF_DIGITS_AFTER_DECIMAL_POINT, BigDecimal.ROUND_HALF_UP)
            return bigDecimal.toDouble()
        }
    }
}