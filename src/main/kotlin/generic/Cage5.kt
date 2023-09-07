package generic

fun main() {
    val cage = Cage5(mutableListOf(
        Eagle(),
        Sparrow()
    ))
    cage.printAfterSorting()
}

abstract class Bird(
    name: String,
    private val size: Int,
) : Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int {
        return this.size.compareTo(other.size)
    }
}

class Sparrow : Bird("참새", 100)
class Eagle : Bird("참새", 500)

class Cage5<T>(
    private val animals: MutableList<T> = mutableListOf(),
) where T : Animal, T : Comparable<T> {

    fun printAfterSorting() {
        this.animals.sorted()
            .map { it.name }
            .let(::println)
    }

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage5<out T>) {
        this.animals.addAll(cage.animals)
    }

    fun moveTo(otherCage: Cage5<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}

fun <T> List<T>.hasIntersection(other: List<T>): Boolean {
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}