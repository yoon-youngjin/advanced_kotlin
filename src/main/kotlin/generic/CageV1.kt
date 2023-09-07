package generic

open class CageV1<T : Animal> {
    fun addAnimal(animal: T) {

    }
}

class CageV2_1<T : Animal> : CageV1<T>()
class CageV2_2 : CageV1<GoldFish>()
