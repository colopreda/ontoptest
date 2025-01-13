package com.andresp.ontoptest.presentation.view

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresp.ontoptest.presentation.viewmodel.SharedCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class OnTopApp : Application()


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "initial") {
        composable("initial") { InitialScreen(navController) }
        composable("list") {
            val sharedViewModel: SharedCharacterViewModel = hiltViewModel(it)
            PaginatedListScreen(navController, sharedViewModel)
        }
        composable("details") {
            val sharedViewModel: SharedCharacterViewModel = if (navController.previousBackStackEntry != null) hiltViewModel(
                navController.previousBackStackEntry!!
            ) else hiltViewModel()
            DetailsScreen(sharedViewModel) { navController.popBackStack() }
        }
        composable("capitalize") { CapitalizeScreen { navController.popBackStack() } }
        composable("password") { PasswordGeneratorScreen { navController.popBackStack() } }
    }
}
