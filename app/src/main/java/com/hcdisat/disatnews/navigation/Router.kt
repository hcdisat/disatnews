package com.hcdisat.disatnews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hcdisat.common.navigation.NewsEvent
import com.hcdisat.common.navigation.OnboardingEvent
import com.hcdisat.news.NewsRoute
import com.hcdisat.onboarding.OnboardingRoute
import javax.inject.Inject

interface Router {
    val isReady: Boolean
    @Composable
    fun SetupNavGraph(startDestination: String, navHostController: NavHostController)
}

class RouterImpl @Inject constructor(
    private val onboardingRoute: OnboardingRoute,
    private val newsRoute: NewsRoute
) : Router {

    private var _isReady by mutableStateOf(false)

    override val isReady: Boolean get() = _isReady

    @Composable
    override fun SetupNavGraph(startDestination: String, navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = startDestination) {
            registerOnboarding(navHostController)
            registerNews()
        }
    }

    private fun NavGraphBuilder.registerNews() {
        newsRoute.register(this) { event ->
            when (event) {
                NewsEvent.OpenArticle -> {}
                NewsEvent.OpenSearch -> {}
            }
        }
    }

    private fun NavGraphBuilder.registerOnboarding(navHostController: NavHostController) {
        onboardingRoute.register(this) { event ->
            _isReady = when (event) {
                OnboardingEvent.Completed -> {
                    navHostController.navigate(newsRoute.route)
                    true
                }

                OnboardingEvent.Pending -> true
            }
        }
    }
}