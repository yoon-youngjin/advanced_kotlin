package function

fun add3(a: Int, b: Int) = a + b
fun main() {
    val add1 = ::add3
    val filter: StringFilter = object : StringFilter {
        override fun predicate(str: String?): Boolean {
            return str?.startsWith("A") ?: false
        }
    }

    val filter2 = StringFilter { s -> s.startsWith("A") }
    consumeFilter(Filter { s: String -> s.startsWith("A") })
}

fun consumeFilter(filter: StringFilter) {}

fun <T> consumeFilter(filter: Filter<T>) {}