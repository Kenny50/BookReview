package com.kenny.bookreview.domain.paginator

/**
 * Custom paging tool, build for handle paging request, and connect previous result and new result.
 *
 * using [Result] as request value wrapper.
 *
 * @param Key Generics type, the key pass for make single page request.
 * @param Item Return type, the value type return from request.
 *
 * @property initialKey the first page index, or the first keyword.
 * @property onLoadUpdated receive boolean, determine whether state is loading.
 * @property onRequest receive the key, and make the request.
 * @property getNextKey receive the return value, return the next key.
 * @property onError receive error.
 * @property onSuccess receive request result and next key.
 */
class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    /**
     * change state before and after the paging request.
     */
    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUpdated(false)
    }

    /**
     * reset the paginator
     */
    override fun reset() {
        currentKey = initialKey
    }
}