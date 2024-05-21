package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailConverterFactoryImplTest {
    private val propertyConverter: PropertyConverter = mockk()
    private val projectConverter: ProjectConverter = mockk()
    private lateinit var factory: DetailConverterFactory

    @BeforeEach
    fun setupTest() {
        factory = DetailConverterFactoryImpl(
            propertyConverter = propertyConverter,
            projectConverter = projectConverter
        )
    }

    @Test
    fun `should return a property converter`() {
        val converter = factory.getConverter(ListingType.PROPERTY)
        Assertions.assertThat(converter).isInstanceOf(PropertyConverter::class.java)
    }

    @Test
    fun `should return a project converter`() {
        val converter = factory.getConverter(ListingType.PROJECT)
        Assertions.assertThat(converter).isInstanceOf(ProjectConverter::class.java)
    }
}