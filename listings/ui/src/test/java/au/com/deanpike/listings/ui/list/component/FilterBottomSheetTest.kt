package au.com.deanpike.listings.ui.list.component

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.robot.FilterBottomSheetRobot
import au.com.deanpike.listings.ui.robot.FilterBottomSheetRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterBottomSheetTest : RobolectricTestBase() {
    private val robot = FilterBottomSheetRobot(composeTestRule)

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.RENT,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .assertBuyButtonDisplayed()
            .assertRentButtonDisplayed()
            .assertSoldButtonDisplayed()
            .assertAllPropertyTypesDisplayed()
            .assertHousePropertyTypeDisplayed()
            .assertTownhousePropertyTypeDisplayed()
            .assertApartmentPropertyTypeDisplayed()
            .assertApplyButtonDisplayed()
    }

    @Test
    fun `should select buy button`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.RENT,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .clickBuyButton()
            .clickApplyButton()

        assertEquals(StatusType.BUY, robot.selectedStatusType)
        assertEquals(DwellingType.ALL, robot.selectedDwellingTypes?.firstOrNull())
    }

    @Test
    fun `should select rent button`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.BUY,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .clickRentButton()
            .clickApplyButton()

        assertEquals(StatusType.RENT, robot.selectedStatusType)
        assertEquals(DwellingType.ALL, robot.selectedDwellingTypes?.firstOrNull())
    }

    @Test
    fun `should select sold button`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.BUY,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .clickSoldButton()
            .clickApplyButton()

        assertEquals(StatusType.SOLD, robot.selectedStatusType)
        assertEquals(DwellingType.ALL, robot.selectedDwellingTypes?.firstOrNull())
    }

    @Test
    fun `should select multiple dwelling types`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.BUY,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .assertAllDwellingTypeSelected(true)
            .clickHouseDwellingType()
            .assertAllDwellingTypeSelected(false)
            .assertHouseDwellingTypeSelected(true)
            .clickTownhouseDwellingType()
            .assertHouseDwellingTypeSelected(true)
            .assertTownhouseDwellingTypeSelected(true)
            .clickApartmentDwellingType()
            .assertHouseDwellingTypeSelected(true)
            .assertTownhouseDwellingTypeSelected(true)
            .assertApartmentDwellingTypeSelected(true)
            .clickAllDwellingType()
            .assertAllDwellingTypeSelected(true)
            .assertHouseDwellingTypeSelected(false)
            .assertTownhouseDwellingTypeSelected(false)
            .assertApartmentDwellingTypeSelected(false)
    }

    @Test
    fun `should select house and townhouse dwelling types`() {
        robot
            .setupComponent(
                data = FilterBottomSheetRobotInitData(
                    statusType = StatusType.BUY,
                    dwellingTypes = listOf(DwellingType.ALL)
                )
            )
            .assertLayoutDisplayed()
            .assertAllDwellingTypeSelected(true)
            .clickHouseDwellingType()
            .clickTownhouseDwellingType()
            .assertAllDwellingTypeSelected(false)
            .assertHouseDwellingTypeSelected(true)
            .assertTownhouseDwellingTypeSelected(true)
            .clickApplyButton()

        assertEquals(StatusType.BUY, robot.selectedStatusType)
        assertEquals(2, robot.selectedDwellingTypes!!.size)
        assertEquals(DwellingType.HOUSE, robot.selectedDwellingTypes!![0])
        assertEquals(DwellingType.TOWNHOUSE, robot.selectedDwellingTypes!![1])
    }

}