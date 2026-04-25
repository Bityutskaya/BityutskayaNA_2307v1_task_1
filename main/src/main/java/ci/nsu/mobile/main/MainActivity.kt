package com.example.shoppingcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ci.nsu.mobile.main.database.AppDatabase
import ci.nsu.mobile.main.data.ShoppingListRepository
import ci.nsu.mobile.main.ui.screens.CalendarScreen
import ci.nsu.mobile.main.ui.screens.ShoppingListScreen
import ci.nsu.mobile.main.viewmodels.MainViewModel
import ci.nsu.mobile.main.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = ShoppingListRepository(database.shoppingListDao())

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingCalendarApp(repository)
                }
            }
        }
    }
}

@Composable
fun ShoppingCalendarApp(repository: ShoppingListRepository) {
    val factory = MainViewModelFactory(repository)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "calendar"
    ) {
        composable("calendar") {
            CalendarScreen(navController, viewModel)
        }

        composable(
            "shopping_list/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            ShoppingListScreen(navController, date, viewModel)
        }
    }
}