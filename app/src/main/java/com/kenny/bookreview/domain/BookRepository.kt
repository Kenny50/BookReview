package com.kenny.bookreview.domain

import com.kenny.bookreview.data.local.BookVo
import com.kenny.bookreview.data.local.PagingBookReviewsVo

interface BookRepository {

    suspend fun getBookReviewDto(page: Int, pageSize: Int): PagingBookReviewsVo

    suspend fun getPopularBook(): List<BookVo>
}