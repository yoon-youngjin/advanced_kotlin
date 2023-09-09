package delegate

class Person {
    lateinit var name: String

    val isYoon: Boolean
        get() = this.name.startsWith("")

    val maskingName: String
        get() = name[0] + (1 until name.length).joinToString("") { "*" }
}

