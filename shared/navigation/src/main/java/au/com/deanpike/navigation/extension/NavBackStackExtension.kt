package au.com.deanpike.navigation.extension

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.navigation.keys.ProjectDetailScreenKey
import au.com.deanpike.navigation.keys.PropertyDetailScreenKey

fun NavBackStack<NavKey>.addDetail(detailRoute: NavKey) {

    // Remove any existing detail routes, then add the new detail route
    removeIf { it is PropertyDetailScreenKey || it is ProjectDetailScreenKey }
    add(detailRoute)
}