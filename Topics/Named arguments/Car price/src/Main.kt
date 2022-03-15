// complete this function, add default values
const val DEFAULT_OLD = 5
const val DEFAULT_KILOMETERS = 100_000
const val DEFAULT_SPEED = 120

const val INITIAL_PRICE = 20_000
const val DECREMENT_PER_YEAR = 2_000
const val DELTA_SPEED = 100
const val STEP_KILOMETERS = 10_000
const val DECREMENT_KILOMETERS = 200
const val INCREMENT_AUTOMATIC = 1500

fun carPrice(
    old: Int = DEFAULT_OLD,
    kilometers: Int = DEFAULT_KILOMETERS,
    maximumSpeed: Int = DEFAULT_SPEED,
    automatic: Boolean = false
) {
    var actualPrice = INITIAL_PRICE
    for (i in 1..old) {
        actualPrice -= DECREMENT_PER_YEAR
    }
    actualPrice += (maximumSpeed - DEFAULT_SPEED) * DELTA_SPEED
    for (i in STEP_KILOMETERS .. kilometers step STEP_KILOMETERS) {
        actualPrice -= DECREMENT_KILOMETERS
    }
    if (automatic) {
        actualPrice += INCREMENT_AUTOMATIC
    }
    println(actualPrice)
}
