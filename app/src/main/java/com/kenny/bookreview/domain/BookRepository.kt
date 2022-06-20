package com.kenny.bookreview.domain

import com.kenny.bookreview.data.local.PagingBookReviewsVo

interface BookRepository {

    suspend fun getBookReviewDto(page: Int, pageSize: Int): PagingBookReviewsVo

}