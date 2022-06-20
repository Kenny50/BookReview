package com.kenny.bookreview.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kenny.bookreview.R


sealed class BottomNavScreen(
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val route: String
) {
    object Home : BottomNavScreen(R.string.home, Icons.Filled.Home, "home")
    object Search : BottomNavScreen(R.string.search, Icons.Filled.Search, "search")
}

sealed class Screen(val route: String) {
    object BookDetailScreen : Screen("book_detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)

            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}