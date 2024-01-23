package com.hcdisat.onboarding.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hcdisat.onboarding.R
import com.hcdisat.onboarding.components.Navigator
import com.hcdisat.onboarding.components.OnboardingDimen
import com.hcdisat.onboarding.components.OnboardingPage
import com.hcdisat.onboarding.model.NavigatorLabels
import com.hcdisat.onboarding.model.OnboardingAction
import com.hcdisat.onboarding.model.pages
import com.hcdisat.presentation.NightDayPreview
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OnboardingScreen(
    modifier: Modifier = Modifier,
    sendAction: (OnboardingAction) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { pages.size }

    val start = stringResource(R.string.btn_get_started)
    val next = stringResource(R.string.btn_next)
    val back = stringResource(R.string.btn_back)
    val navigatorLabels by remember {
        derivedStateOf {
            val state = NavigatorLabels()
            when (pagerState.currentPage) {
                0 -> state.copy(nextText = next, backText = "")
                1 -> state.copy(backText = back, nextText = next)
                2 -> state.copy(nextText = start, backText = back)
                else -> state
            }
        }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { step ->
            OnboardingPage(page = pages[step])
        }

        Navigator(
            modifier = Modifier
                .padding(horizontal = OnboardingDimen.smallPadding())
                .padding(
                    top = OnboardingDimen.largePadding(),
                    bottom = OnboardingDimen.mediumPadding()
                )
                .navigationBarsPadding(),
            size = pagerState.pageCount,
            selectedIndex = pagerState.currentPage,
            labels = navigatorLabels,
            onNext = {
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    sendAction(OnboardingAction.CompleteOnboarding)
                } else {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            onBack = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }
        )
    }
}

@NightDayPreview
@Composable
private fun OnboardingScreePreview() {
    DisatNewsTheme {
        OnboardingScreen()
    }
}