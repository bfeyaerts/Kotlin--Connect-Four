import java.time.ZoneId
import java.util.*

fun main() {
    //val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
    val calendar = Calendar.getInstance()
    //println("${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}")
    val totalSeconds = System.currentTimeMillis() / 1000 // dont change this line
    // enter your code
    val seconds = totalSeconds % 60
    val totalMinutes = totalSeconds / 60
    val minutes = totalMinutes % 60
    val totalHours = totalMinutes / 60
    val hours = totalHours % 24

    //println("$totalHours:$minutes:$seconds")

    println("${calendar.get(Calendar.HOUR_OF_DAY)}:$minutes:$seconds")
}