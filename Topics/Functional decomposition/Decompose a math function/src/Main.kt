fun f(x: Double): Double {
    // call your implemented functions here
    return when {
        x <= 0 -> f1(x)
        x > 0 && x < 1 -> f2(x)
        x >= 1 -> f3(x)
        else -> throw RuntimeException("Oops")
    }
}

// implement your functions here
fun f1(x: Double) = x * x + 1

fun f2(x: Double) = 1.0 / x / x

fun f3(x: Double) = x * x - 1