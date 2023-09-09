package example

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import kotlin.random.Random

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 수행 시간 확인 (평균 시간)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class SequenceTest {
    private val fruits = mutableListOf<Fruit>()

    @Setup
    fun init() {
        (1..100).forEach { _ -> fruits.add(Fruit.random()) }
    }

    @Benchmark
    fun kotlinSequence() {
        val avg = fruits.asSequence()
            .filter { it.name == "사과" }
            .map { it.price }
            .take(10_000)
            .average()
    }
    @Benchmark
    fun kotlinIterator() {
        val avg = fruits
            .filter { it.name == "사과" }
            .map { it.price }
            .take(10_000)
            .average()
    }

}

data class Fruit(
    val name: String,
    val price: Long,
) {
    companion object {
        private val NAME_CANDIDATES = listOf("사과", "바나나", "수박", "채리", "오렌지")
        fun random(): Fruit {
            val randomNum1 = Random.nextInt(0, 5)
            val randomNum2 = Random.nextLong(1000, 20001)
            return Fruit(
                name = NAME_CANDIDATES[randomNum1],
                price = randomNum2,
            )
        }
    }

}
