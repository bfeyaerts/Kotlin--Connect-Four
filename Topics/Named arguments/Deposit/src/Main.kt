import kotlin.math.pow

fun main() {
    // write your code here
    val argument = readLine()!!
    val value = readLine()!!.toInt()

    println(
        when (argument) {
            "amount" -> finalAmount(amount = value).toInt()
            "percent" -> finalAmount(percent = value).toInt()
            "years" -> finalAmount(years = value).toInt()
            else -> "ERROR"
        }
    )
}

fun finalAmount(amount: Int = 1000, percent: Int = 5, years: Int = 10) =
    amount * ((100.0 + percent) / 100).pow(years.toDouble())