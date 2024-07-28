package au.com.deanpike.mviexample.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.ui.screen.list.ListingListScreen

@Composable
fun MviCompactApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable(route = "list") {
            ListingListScreen(
                onPropertyClicked = { propertyId ->
                    navController.navigate("property-detail/$propertyId")
                },
                onProjectClicked = { projectId ->
                    navController.navigate("project-detail/$projectId")
                }
            )
        }
        composable(
            route = "property-detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            PropertyDetailScreen(
                propertyId = backStackEntry.arguments!!.getInt("id"),
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "project-detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            ProjectDetailScreen(
                projectId = backStackEntry.arguments!!.getInt("id"),
                onCloseClicked = {
                    navController.popBackStack()
                },
                onProjectChildClicked = { propertyId ->
                    navController.navigate("property-detail/$propertyId")
                }
            )
        }
    }
}