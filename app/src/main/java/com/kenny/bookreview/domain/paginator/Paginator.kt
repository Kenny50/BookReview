package com.kenny.bookreview.domain.paginator

/**
 * Define the paginator abstract behavior.
 *
 * @param Key index or keyword of page.
 * @param Item return type of paging request
 */
interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}