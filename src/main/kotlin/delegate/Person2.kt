package delegate

class Person2 {
    val name: String by lazy {
        Thread.sleep(2_000)
        "윤영진"
    }
}

