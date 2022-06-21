package com.kenny.bookreview.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenny.bookreview.R
import com.kenny.bookreview.ui.theme.BookReviewTheme
import java.util.*

/**
 * Create a section with uppercase topic text.
 *
 * @param text string resource id
 * @param modifier
 * @param content content view for this section
 */
@Composable
fun TopicSection(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = text).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.h2.copy(fontSize = 15.sp),
            modifier = Modifier
                .paddingFromBaseline(top = 28.dp, 8.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
            maxLines = 1
        )
        content()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "dark mode"
)
@Composable
fun PreviewTopicSection() {
    BookReviewTheme {
        TopicSection(text = R.string.home) {

        }
    }
}