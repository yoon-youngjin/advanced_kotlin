package delegate

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class PersonTest1 {

    private val person = Person()

    @Test
    fun isYoonTest() {
        // given
        val person = person.apply { name = "윤영진" }

        // when & then
        assertThat(person.isYoon).isTrue()
    }

    @Test
    fun maskingNameTest() {
        // given
        val person = person.apply { name = "유녕진" }

        // when & then
        assertThat(person.maskingName).isEqualTo("유**")
    }
}