package com.cwj.onboarding.presentation.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cwj.onboarding.ui.theme.OnBoardingTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, onSkipNext: () -> Unit) {
    val items = OnBoardingItem.getItems()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Header(
            onBackItem = {
                if (pagerState.currentPage + 1 > 1)
                    coroutineScope.launch {
                        pagerState.scrollToPage(pagerState.currentPage - 1)
                    }
            },
            onSkip = onSkipNext, isFirstItem = pagerState.currentPage == 0
        )
        HorizontalPager(
            pageCount = items.size,
            state = pagerState,
            modifier = modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { pageItem ->
            OnBoardScreenItems(items = items[pageItem])
        }
        Footer(
            size = items.size,
            index = pagerState.currentPage
        ) {
            if (pagerState.currentPage + 1 < items.size)
                coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            else {
                onSkipNext()
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onBackItem: () -> Unit = {},
    onSkip: () -> Unit,
    isFirstItem: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        if (isFirstItem) {
            TextButton(
                onClick = onSkip,
                modifier = modifier.align(Alignment.CenterEnd),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            IconButton(onClick = onBackItem, modifier.align(Alignment.CenterStart)) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "previous item"
                )
            }

            TextButton(
                onClick = onSkip,
                modifier = modifier.align(Alignment.CenterEnd),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun OnBoardScreenItems(modifier: Modifier = Modifier, items: OnBoardingItem) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp)
                .size(150.dp),
            painter = painterResource(id = items.image),
            contentDescription = "OnBoarding Image"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(items.tile), style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(items.description),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )
    }
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    size: Int,
    index: Int,
    onNextItem: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Indicators(size = size, index = index)
        FloatingActionButton(
            onClick = onNextItem,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(15.dp))
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "next item",
//                tint = Color.White
            )
        }
    }
}

@Composable
fun BoxScope.Indicators(modifier: Modifier = Modifier, size: Int, index: Int) {
    Row(
        modifier = modifier.align(Alignment.CenterStart),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(size) {
            OnBoardingIndicator(isCurrent = it == index)
        }
    }
}

@Composable
fun OnBoardingIndicator(modifier: Modifier = Modifier, isCurrent: Boolean) {
    val indicatorWidth by animateDpAsState(
        targetValue = if (isCurrent) 25.dp else 10.dp, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    )
    val background = if (isCurrent) MaterialTheme.colorScheme.onPrimary else Color.DarkGray

    Box(
        modifier = modifier
            .height(10.dp)
            .width(indicatorWidth)
            .clip(CircleShape)
            .background(background)
    )
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoardingTheme {
//        Column(modifier = Modifier.padding(16.dp)) {
//            OnBoardingIndicator(isCurrent = true)
//            Footer(size = 3, index = 3, onNextItem = {})
//            OnBoardScreenItems(
//                items = OnBoardingItem(
//                    R.drawable.undraw_activity_tracker,
//                    R.string.activity_monitor,
//                    R.string.activity_monitor_desc
//                )
//            )
//        }
        OnBoardingScreen {

        }
    }
}