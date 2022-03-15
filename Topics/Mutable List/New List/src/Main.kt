fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val mutableList = numbers.toMutableList()
    mutableList.add(number)
    return mutableList
}