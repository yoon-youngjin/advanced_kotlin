package dsl

import kotlin.properties.Delegates

fun main() {
    dockerCompose {
        version { 3 }
    }

//    yml.render()
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
    val dockerCompose = DockerCompose()
    dockerCompose.init()
    return dockerCompose
}


class DockerCompose {
    private var version: Int by Delegates.notNull()
    fun version(init: () -> Int) {
        version = init()
    }

    fun render(indent: String): String {
        val builder = StringBuilder()
        return ""
    }
}

fun StringBuilder.appendBew(str: String, indent: String = "", times: Int = 0) {
    (1..times).forEach { _ -> this.append(indent) }
    this.append(str)
    this.append("\n")
}


fun String.addIndent(indent: String, times: Int = 0): String { // 여러 줄짜리 문자열을 한 번에 들여쓰기 해주는 함수
    val allIndent = (1..times).joinToString("") { indent }
    return allIndent
}