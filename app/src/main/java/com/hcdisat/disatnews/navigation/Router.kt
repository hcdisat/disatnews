package com.hcdisat.disatnews.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hcdisat.onboarding.OnboardingRoute
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface Router {
    @Composable
    fun SetupNavGraph(startDestination: String, navHostController: NavHostController)
}

class RouterImpl @Inject constructor(
    private val onboardingRoute: OnboardingRoute,
    @ApplicationContext private val context: Context
) : Router {
    @Composable
    override fun SetupNavGraph(startDestination: String, navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = startDestination) {
            onboardingRoute.register(this) {
                Toast.makeText(context, "Onboarding completed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}