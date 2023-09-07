package generic

fun main() {

    val numbers = listOf(1, 2f, 3.0)
    numbers.filterIsInstance<Float>()

}

//fun <T> T.toSuperString() {
//    println("${T::class.java.name}: $this")
//}
// T::class : 클래스 정보를 가져오는 코드

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}