package com.cwj.onboarding.presentation.screens

import com.cwj.onboarding.R

class OnBoardingItem(
    val image: Int,
    val tile: Int,
    val description: Int
) {
    companion object {
        fun getItems(): List<OnBoardingItem> {
            return listOf(
                OnBoardingItem(R.drawable.undraw_healthy_habit, R.string.get_biceps, R.string.get_biceps_desc),
                OnBoardingItem(R.drawable.undraw_personal_training, R.string.personal_training, R.string.personal_training_desc),
                OnBoardingItem(R.drawable.undraw_activity_tracker, R.string.activity_monitor, R.string.activity_monitor_desc)
            )
        }
    }
}