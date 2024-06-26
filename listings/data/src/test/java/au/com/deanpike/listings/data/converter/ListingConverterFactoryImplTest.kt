package au.com.deanpike.listings.data.converter

import au.com.deanpike.datashared.type.ListingType
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingConverterFactoryImplTest {
    private val propertyConverter: PropertyConverter = mockk()
    private val projectConverter: ProjectConverter = mockk()
    private lateinit var factory: ListingConverterFactory

    @BeforeEach
    fun setupTest() {
        factory = ListingConverterFactoryImpl(
            propertyConverter = propertyConverter,
            projectConverter = projectConverter
        )
    }

    @Test
    fun `should return property converter`() {
        val converter = factory.getConverter(ListingType.PROPERTY)
        assertThat(converter).isInstanceOf(PropertyConverter::class.java)
    }

    @Test
    fun `should return project converter`() {
        val converter = factory.getConverter(ListingType.PROJECT)
        assertThat(converter).isInstanceOf(ProjectConverter::class.java)
    }

    @Test
    fun `should not return a conveter`() {
        val converter = factory.getConverter(ListingType.TOPSPOT)
        assertThat(converter).isNull()
    }
}