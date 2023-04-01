package com.cwj.onboarding.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cwj.onboarding.presentation.screens.HomeScreen
import com.cwj.onboarding.presentation.screens.OnBoardingScreen

@Composable
fun OnBoardingNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OverView.OnBoarding.route) {
        composable(OverView.OnBoarding.route) {
            OnBoardingScreen(onSkipNext = {
                navController.navigate(OverView.Home.route) {
                    navController.popBackStack()
                }
            })
        }
        composable(OverView.Home.route) {
            HomeScreen()
        }
    }
}