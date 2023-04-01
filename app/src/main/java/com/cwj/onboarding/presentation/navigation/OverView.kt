package com.cwj.onboarding.presentation.navigation

sealed class OverView (
    val route: String,
    val title: String
) {
    object OnBoarding: OverView(
        route = "onBoarding",
        title = "onBoarding Screen"
    )

    object Home: OverView(
        route = "home",
        title = "Home"
    )
}