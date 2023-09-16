package function

class Lec13 {
}

fun main() {
    compute(5, 6) { n1, n2 ->
        n1 + n2
    }
    compute(5, 6, fun(n1: Int, n2: Int) = n1 + n2)
    compute(5, 6, fun(n1, n2) = n1 + n2)

    iterate(listOf(1, 2, 3, 4, 5, 6), fun(num) {
        if (num == 3) {
            return
        }
        println(num)
    })
    iterate(listOf(1, 2, 3, 4, 5, 6)) { num ->
        if (num == 3) return@iterate
        println(num)
    }

}


fun calculate(num1: Int, num2: Int, op: Operator): Int = op(num1, num2)
enum class Operator(
    private val oper: Char,
    val calcFun: (Int, Int) -> Int,
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    MULTIPLY('*', { a, b -> a * b }),
    DIVIDE('/', { a, b ->
        if (b == 0) throw IllegalArgumentException()
        else {
            a / b
        }
    }),
    ;

    operator fun invoke(num1: Int, num2: Int): Int {
        return calcFun(num1, num2)
    }
}
