package com.hnp.gplx600.navigation

sealed class Screen(val route: String) {
  object Home : Screen(route = "home")
  object Login : Screen(route = "login")
}