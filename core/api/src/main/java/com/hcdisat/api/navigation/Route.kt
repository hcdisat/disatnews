package com.hcdisat.api.navigation

import androidx.navigation.NavGraphBuilder

interface Route<out T : Any?> {
    val route: String
    fun register(builder: NavGraphBuilder, onEvent: (T) -> Unit)
}