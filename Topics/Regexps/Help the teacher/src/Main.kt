fun main() {
    val report = readLine()!!
    val regex = "\\d wrong answers?".toRegex()

    println(if (report.matches(regex)) "true"
        else "false")
}