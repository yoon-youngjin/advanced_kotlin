package reflection

import generic.Animal
import generic.Carp
import generic.GoldFish
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.cast

fun main() {
//    val cage = TypeSafeCage()
//    typeSafeCage.putOne(Carp::class, Carp("잉어"))
//    typeSafeCage.getOne(Carp::class)
//    cage.putOne(Carp("잉어"))
//    val one: Carp = cage.getOne()

    val superTypeToken1 = object : SuperTypeToken<List<GoldFish>>() {}
    val superTypeToken2 = object : SuperTypeToken<List<GoldFish>>() {}
    val superTypeToken3 = object : SuperTypeToken<List<Carp>>() {}

    println(superTypeToken1.equals(superTypeToken2))
    println(superTypeToken1.equals(superTypeToken3))

    val test1 = listOf<GoldFish>()
    val test2 = listOf<GoldFish>()
    val test3 = listOf<Carp>()

    println(test1.equals(test2))
    println(test1.equals(test3))

    val superTypeSafeCage = SuperTypeSafeCage()
    superTypeSafeCage.putOne(superTypeToken2, listOf(GoldFish("금붕어1"), GoldFish("금붕어2")))
    val result = superTypeSafeCage.getOne(superTypeToken3)
    println(result)

}

class TypeSafeCage {
    private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

    fun <T: Animal> getOne(type: KClass<T>): T {
        return type.cast(animals[type])
    }

    fun <T : Animal> putOne(type: KClass<T>, animal: T) {
        animals[type] = type.cast(animal)
    }

    inline fun <reified T : Animal> getOne(): T {
        return this.getOne(T::class)
    }

    inline fun <reified T : Animal> putOne(animal: T) {
        return this.putOne(T::class, animal)
    }
}

// SuperTypeToken을 구현한 클래스가 인스턴화 되자마자 T 정보를 내부 변수에 저장해버린다.
// T <- List<Int> 를 넣는다고 가정하면 type에 List<Int> 정보를 기억하게된다.
abstract class SuperTypeToken<T> { // 인스턴스화를 막기 위한 abstract
    // abstract 클래스이기 때문에 아래의 this는 SuperTypeToken을 상속받은 클래스가 된다.
//    val type: KType = this::class.supertypes[0] // SuperTypeToken을 상속받은 클래스의 SuperType이 담긴다.
//    val type: KType = this::class.supertypes[0].arguments[0] // 제네릭 타입 매개변수를 가져오기 때문에 T를 가져온다.
    val type: KType = this::class.supertypes[0].arguments[0].type!! // 순수한 T 타입을 가져온다.

    override fun equals(other: Any?): Boolean {
        if(this === other) return true

        other as SuperTypeToken<*>
        if (type != other.type) return false
        return true
    }
    override fun hashCode(): Int {
        return type.hashCode()
    }
}


class SuperTypeSafeCage {
    private val animals: MutableMap<SuperTypeToken<*>, Any> = mutableMapOf()

    fun <T: Any> getOne(token: SuperTypeToken<T>): T? { // 타입 제약을 Any로 바꿔야한다. 그 이유는 List<*>, Map, ... 다양한 타입이 들어갈 수 있기 때문이다
        if (this.animals[token] == null) return null
        return this.animals[token] as T
    }

    fun <T : Any> putOne(token: SuperTypeToken<T>, animal: T) {
        animals[token] = animal
    }
}
