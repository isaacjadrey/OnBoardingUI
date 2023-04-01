package com.cwj.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cwj.onboarding.presentation.navigation.OnBoardingNavHost

@Composable
fun OnBoardingApp(navController: NavHostController = rememberNavController()) {
    OnBoardingNavHost(navController = navController)
}