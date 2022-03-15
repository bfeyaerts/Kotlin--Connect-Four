fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    while (str in strings) {
        strings[strings.indexOf(str)] = "Banana"
    }
    return strings
}