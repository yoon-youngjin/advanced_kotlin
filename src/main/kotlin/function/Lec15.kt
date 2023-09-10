package function

inline fun add(n1: Int, n2: Int) = n1 + n2
fun main() {
    iterate(listOf(1, 2, 3, 4, 5, 6)) { num ->
        if (num == 3) {
            return@iterate
        }
        println(num)
    }
}

inline fun iterate(numbers: List<Int>, crossinline exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}
inline fun repeat(
    times: Int,
    noinline exec: () -> Unit) {
    for (i in 1..times) {
        exec()
    }
}