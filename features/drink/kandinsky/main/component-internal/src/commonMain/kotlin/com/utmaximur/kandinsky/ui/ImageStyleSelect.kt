package com.utmaximur.kandinsky.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.text.TextOutlinedLabel
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.design.ui.dots.DotsIndicator
import com.utmaximur.domain.kandinsky.ImageStyle
import kandinsky.resources.Res
import kandinsky.resources.title_image_style
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ImageStyleSelectContent(
    imageStyles: List<ImageStyle>,
    onItemSelected: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imageStyles.size })
    LaunchedEffect(pagerState.currentPage, imageStyles) {
        imageStyles.getOrNull(pagerState.currentPage)
            ?.let { onItemSelected(it.name) }
    }
    ElevatedCardApp(
        contentPaddingValues = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextOutlinedLabel(
            title = stringResource(Res.string.title_image_style),
        )
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
            pageContent = { index ->
                ImageStyleItem(
                    title = imageStyles[index].title,
                    imageUrl = imageStyles[index].styleImageUrl
                )
            },
        )
        DotsIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pageCount = pagerState.pageCount,
            currentPageFraction = remember {
                derivedStateOf {
                    pagerState.currentPage + pagerState.currentPageOffsetFraction
                }
            }
        )
    }
}