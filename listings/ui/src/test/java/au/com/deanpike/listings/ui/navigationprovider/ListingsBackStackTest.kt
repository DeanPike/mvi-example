package au.com.deanpike.listings.ui.navigationprovider

import androidx.navigation3.runtime.NavKey
import au.com.deanpike.navigation.keys.ListingScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey
import au.com.deanpike.navigation.keys.SearchScreenKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ListingsBackStackTest {

    private val search = SearchScreenKey()
    private val filterSearch = SearchScreenKey(status = "BUY", dwellingTypes = listOf("ALL"))
    private val buyResults = ListingScreenKey(status = "BUY", dwellingTypes = listOf("ALL"))
    private val rentResults = ListingScreenKey(status = "RENT", dwellingTypes = listOf("HOUSE"))

    @Test
    fun `should replace initial search with results`() {
        val backStack = mutableListOf<NavKey>(search)

        backStack.showListingResults(buyResults)

        assertThat(backStack).containsExactly(buyResults)
    }

    @Test
    fun `should open search above results when filter clicked`() {
        val backStack = mutableListOf<NavKey>(buyResults)

        backStack.showSearch(filterSearch)

        assertThat(backStack).containsExactly(buyResults, filterSearch)
    }

    @Test
    fun `should not open a second search when search already on top`() {
        val backStack = mutableListOf<NavKey>(buyResults, filterSearch)

        backStack.showSearch(filterSearch)

        assertThat(backStack).containsExactly(buyResults, filterSearch)
    }

    @Test
    fun `should replace previous results and search when searching from filter`() {
        val backStack = mutableListOf<NavKey>(buyResults, filterSearch)

        backStack.showListingResults(rentResults)

        assertThat(backStack).containsExactly(rentResults)
    }

    @Test
    fun `should remove details opened from previous results when searching again`() {
        val detail = PropertyDetailScreenKey(propertyId = 1L, address = "1 Test St")
        val backStack = mutableListOf<NavKey>(buyResults, detail, filterSearch)

        backStack.showListingResults(rentResults)

        assertThat(backStack).containsExactly(rentResults)
    }

    @Test
    fun `should keep only the results screen across repeated filter and search`() {
        val backStack = mutableListOf<NavKey>(search)

        repeat(3) {
            backStack.showListingResults(buyResults)
            backStack.showSearch(filterSearch)
        }
        backStack.showListingResults(rentResults)

        assertThat(backStack).containsExactly(rentResults)
    }
}
