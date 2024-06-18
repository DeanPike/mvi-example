package au.com.deanpike.mviexample.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.ui.screen.list.ListingListScreen
import au.com.deanpike.uishared.theme.MviExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MviExampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MviApp(navController)
                }
            }
        }
    }
}

@Composable
fun MviApp(navController: NavHostController) {

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