package com.kenny.bookreview.domain.paginator

import com.kenny.bookreview.util.Constant.FIRST_PAGE

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val items: List<T> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = FIRST_PAGE
)
