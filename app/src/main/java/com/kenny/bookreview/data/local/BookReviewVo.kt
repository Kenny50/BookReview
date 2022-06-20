package com.kenny.bookreview.data.local

data class BookReviewVo(
    val title: String,
    val id: Int,
    val content: String,
    val author: String,
    val authorId: Int,
    val rate: Int,
    val bookTitle: String,
    val bookId: Int,
    val img: String,
)

data class PagingBookReviewsVo(
    val bookReviews: List<BookReviewVo>
)
