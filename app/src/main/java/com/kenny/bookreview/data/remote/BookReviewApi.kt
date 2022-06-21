package com.kenny.bookreview.data.remote

import com.kenny.bookreview.data.local.PagingBookReviewsVo
import retrofit2.http.GET

interface BookReviewApi {

    @GET("")
    suspend fun getBookReviews(): PagingBookReviewsVo
}