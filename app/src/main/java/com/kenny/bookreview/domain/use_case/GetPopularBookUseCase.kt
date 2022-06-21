package com.kenny.bookreview.domain.use_case

import com.kenny.bookreview.domain.BookRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get popular book list.
 *
 * @property repository
 */
class GetPopularBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke() = flow {
        emit(repository.getPopularBook())
    }
}