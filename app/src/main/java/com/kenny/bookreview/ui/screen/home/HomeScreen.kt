package com.kenny.bookreview.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kenny.bookreview.R
import com.kenny.bookreview.data.local.BookReviewVo
import com.kenny.bookreview.data.local.BookVo
import com.kenny.bookreview.domain.paginator.ScreenState
import com.kenny.bookreview.ui.animate.linearSlideInVertical
import com.kenny.bookreview.ui.animate.linearSlideOutVertical
import com.kenny.bookreview.ui.components.BookReview
import com.kenny.bookreview.ui.components.TopicSection
import com.kenny.bookreview.ui.theme.BookReviewTheme
import com.kenny.bookreview.ui.theme.MinContrastOfPrimaryVsSurface
import com.kenny.bookreview.util.DynamicThemeBackgroundColorFromImage
import com.kenny.bookreview.util.contrastAgainst
import com.kenny.bookreview.util.rememberDominantColorState
import com.kenny.bookreview.util.verticalGradientScrim
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onBookReviewClick: (Int) -> Unit
) {
    HomeScreen(
        vm.state,
        vm.recentPopularBookList.collectAsState(),
        vm::loadNextItem,
        onBookReviewClick
    )
}

@Composable
fun HomeScreen(
    pagingState: ScreenState<BookReviewVo>,
    headerState: State<List<BookVo>>,
    loadNext: () -> Unit,
    onBookReviewClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            item {
                TopicSection(text = R.string.popular_now) {
                    DynamicBackgroundHeader(headerState.value)
                }
            }
            item {
                TopicSection(text = R.string.recent_review) {

                }
            }
            items(pagingState.items.size) { i ->
                if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                    loadNext()
                }
                BookReview(
                    bookReview = pagingState.items[i],
                    modifier = Modifier.clickable { onBookReviewClick(i) }
                )
            }
            item {
                if (pagingState.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        val showScrollToTopButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }
        val coroutineScope = rememberCoroutineScope()

        AnimatedVisibility(
            visible = showScrollToTopButton,
            enter = linearSlideInVertical(),
            exit = linearSlideOutVertical()
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.scroll_to_top)
                )
            }
        }

    }
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "dark mode",
    widthDp = 320
)
@Composable
fun PreviewHome() {
    BookReviewTheme {
        HomeScreen(
            pagingState = ScreenState(),
            headerState = remember { mutableStateOf(emptyList()) },
            loadNext = {},
            onBookReviewClick = {}
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DynamicBackgroundHeader(
    popularBookList: List<BookVo>,
    modifier: Modifier = Modifier,
) {
    val surfaceColors = MaterialTheme.colors.surface

    /**
     * make sure the valid color contrast value to background is greater than [MinContrastOfPrimaryVsSurface]
     */
    val dominantColorsState = rememberDominantColorState { color ->
        color.contrastAgainst(surfaceColors) >= MinContrastOfPrimaryVsSurface
    }

    DynamicThemeBackgroundColorFromImage(
        dominantColorState = dominantColorsState
    ) {
        val pagerState = rememberPagerState()
        val selectedBookImageUrl = popularBookList.getOrNull(pagerState.currentPage)?.cover

        LaunchedEffect(selectedBookImageUrl) {
            if (selectedBookImageUrl != null) {
                dominantColorsState.updateColorsFromImageUrl(selectedBookImageUrl)
            } else {
                dominantColorsState.reset()
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        ) {
            HeaderPager(
                popularBookList,
                state = pagerState
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HeaderPager(
    list: List<BookVo>,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState()
) {
    HorizontalPager(
        count = list.size,
        state = state,
        modifier = modifier.height(200.dp)
    ) { index ->
        val book = list[index]

        AsyncImage(
            model = book.cover,
            contentDescription = book.bookTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .size(150.dp)
        )
    }
}
