package com.usefulness.slidr.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(lightColorScheme()) {
                val navController = rememberNavController(SlidableComposeNavigator())
                SlidableNavHost(navController = navController, startDestination = "list") {
                    slidableComposable("list") { ListView(navController) }
                    slidableComposable(
                        route = "details/{sdkInt}",
                        arguments = listOf(navArgument("sdkInt") { type = NavType.IntType }),
                        content = { backStackEntry ->
                            val sdkInt = backStackEntry.arguments?.getInt("sdkInt") ?: return@slidableComposable

                            DetailsView(navController, sdkInt)
                        },
                    )
                }
            }
        }
    }
}
