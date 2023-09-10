package function

fun main() {
    var num = 5
    num += 1
    val plusOne: () -> Unit = { num += 1 } // 람다식에서 외부의 변경 가능한 num1을 가져왔다. -> 자바에서는 불가능
    // 이때 Closure는 람다식에서 외부 변수에 접근하기 위해 일시적으로 외부 정보를 포획해두는 개념
}



fun compute(
    num1: Int,
    num2: Int,
    op: (Int, Int) -> Int = fun(n1, n2) = n1 + n2,
): Int {
    return op(num1, num2)
}
