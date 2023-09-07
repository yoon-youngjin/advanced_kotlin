package generic

class Cage4<T : Animal> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage4<out T>) {
        this.animals.addAll(cage.animals)
    }

    fun moveTo(otherCage: Cage4<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}