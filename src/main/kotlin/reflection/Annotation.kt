package reflection

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Repeatable
annotation class Shape(
    val text: String,
    val number: Int,
    val clazz: KClass<*>,
)

@Shape(text = "text", number = 25, clazz = Annotation::class)
@Shape(text = "text", number = 25, clazz = Annotation::class)
class Annotation