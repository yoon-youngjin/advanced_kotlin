package delegate

data class MyFruit(
    val name: String,
    val price: Long,
)

fun main() {
    val fruits = listOf(
        MyFruit("사과", 1000),
        MyFruit("바나나", 2000),
    )
    val avg = fruits.asSequence()
        .filter { it.name == "사과" }
        .map { it.price }
        .take(10_000)
        .average()
}


