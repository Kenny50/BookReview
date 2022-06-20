package com.kenny.bookreview.data.repository

import com.kenny.bookreview.data.local.BookReviewVo
import com.kenny.bookreview.data.local.PagingBookReviewsVo
import com.kenny.bookreview.domain.BookRepository
import kotlinx.coroutines.delay

class FakeRepository : BookRepository {

    private val fakePager by lazy {
        (0..1000).map { index ->
            BookReviewVo(
                bookId = index,
                content = "contentcontentcontentcontentcontentcontentcontent",
                id = index,
                rate = index,
                title = "https://i.imgur.com/1tkySVv.jpeg",
                img = "https://i.imgur.com/1tkySVv.jpeg",
                author = index.toString(),
                authorId = index,
                bookTitle = index.toString()
            )
        }.toList()
    }


    override suspend fun getBookReviewDto(page: Int, pageSize: Int): PagingBookReviewsVo {
        delay(500)
        return PagingBookReviewsVo(
            fakePager.subList(
                page.minus(1).times(pageSize),
                page.times(pageSize)
            )
        )
    }


}