package com.hcdisat.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.hcdisat.presentation.R

interface Dimen {
    @Composable
    fun tinyPadding(): Dp = dimensionResource(id = R.dimen.padding_tiny)
    @Composable
    fun xSmallPadding(): Dp = dimensionResource(id = R.dimen.padding_x_small)
    @Composable
    fun smallPadding(): Dp = dimensionResource(id = R.dimen.padding_small)
    @Composable
    fun mediumPadding(): Dp = dimensionResource(id = R.dimen.padding_medium)
    @Composable
    fun largePadding(): Dp = dimensionResource(id = R.dimen.padding_large)
    @Composable
    fun xLargePadding(): Dp = dimensionResource(id = R.dimen.padding_x_large)
}

object DimenDefault: Dimen