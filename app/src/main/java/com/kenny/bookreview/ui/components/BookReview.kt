package com.kenny.bookreview.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kenny.bookreview.data.local.BookReviewVo
import com.kenny.bookreview.ui.theme.BookReviewTheme

@Composable
fun BookReview(bookReview: BookReviewVo, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GradientTitleOnImage(
            img = bookReview.img,
            bookTitle = bookReview.title,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(100.dp)
        )
        BookReviewContent(
            bookReview.title,
            bookReview.rate,
            bookReview.content,
            Modifier.height(100.dp)
        )
    }
}

@Preview(
    widthDp = 320,
    showBackground = true
)
@Composable
fun PreviewGradient() {
    BookReviewTheme {
        BookReview(
            bookReview = BookReviewVo(
                "title", 1, "content", "", 1, 1, "", 1, ""
            )
        )
    }
}


@Composable
private fun BookReviewContent(
    title: String,
    rating: Int,
    content: String,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        Text(
            text = title + rating,
            style = MaterialTheme.typography.h4,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = content,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GradientTitleOnImage(
    img: String,
    bookTitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        AsyncImage(
            model = img,
            contentDescription = bookTitle,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 150.dp.value
                    )
                )
                .padding(5.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Book title",
                style = TextStyle(color = Color.White)
            )
        }

    }
}
