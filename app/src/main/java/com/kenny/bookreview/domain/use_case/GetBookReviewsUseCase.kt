package com.kenny.bookreview.domain.use_case

import com.kenny.bookreview.domain.BookRepository
import javax.inject.Inject

/**
 * get newest book review, wrapper the result with [Result].
 *
 * @property repository
 */
class GetBookReviewsUseCase @Inject constructor(
    private val repository: BookRepository
) {

    suspend operator fun invoke(page: Int, size: Int) =
        try {
            Result.success(
                repository.getBookReviewDto(page = page, pageSize = size).bookReviews
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
}