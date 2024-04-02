//package au.com.deanpike.ui.ability
//
//import androidx.compose.ui.test.junit4.ComposeContentTestRule
//import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags
//import au.com.deanpike.uitestshared.util.assertTextDisplayed
//import au.com.deanpike.uitestshared.util.clickOn
//
//class PersonListItemAbility(private val composeTestRule: ComposeContentTestRule) {
//    fun assertNameLabelDisplayed(position: Int) {
//        composeTestRule.assertTextDisplayed(tag = "${PersonListItemTesTags.PERSON_LIST_ITEM_NAME_LABEL}_$position", text = "Name")
//    }
//
//    fun assertNameDisplayed(position: Int, text: String) {
//        composeTestRule.assertTextDisplayed(tag = "${PersonListItemTesTags.PERSON_LIST_ITEM_NAME}_$position", text = text)
//    }
//
//    fun assertAgeLabelDisplayed(position: Int) {
//        composeTestRule.assertTextDisplayed(tag = "${PersonListItemTesTags.PERSON_LIST_ITEM_AGE_LABEL}_$position", text = "Age")
//    }
//
//    fun assertAgeDisplayed(position: Int, text: String) {
//        composeTestRule.assertTextDisplayed(tag = "${PersonListItemTesTags.PERSON_LIST_ITEM_AGE}_$position", text = text)
//    }
//
//    fun clickOnItem(position: Int) {
//        composeTestRule.clickOn("${PersonListItemTesTags.PERSON_LIST_ITEM_LAYOUT}_$position")
//    }
//}