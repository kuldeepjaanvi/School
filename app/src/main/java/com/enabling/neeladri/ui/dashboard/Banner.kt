package com.enabling.neeladri.ui.dashboard


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.enabling.neeladri.R
import com.enabling.neeladri.components.DotsIndicator
import com.enabling.neeladri.network.model.Dashboard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun BannerSection(dataBlock: Dashboard.Item) {
    when (!dataBlock.data.isNullOrEmpty()) {
        true -> {
            Banner(dataBlock.data)
        }
        false -> {
            BlankWidget()
        }
    }
}


@Composable
fun Banner(first: List<Dashboard.Item.SubItem>) {
    val context = LocalContext.current
    val height = dimensionResource(id = R.dimen._120sdp)

    if(first.size==1){
        Box(modifier = Modifier
                .clickable(
                    onClick = {

                    },
                )
                .fillMaxWidth()
                .height(height)
                .background(color = colorResource(R.color.colorPrimaryDark))) {
            val painter = if (first?.first().imageUrl.isNullOrEmpty()) {
                painterResource(R.drawable.ic_logo)
            } else {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = first?.first())
                        .allowHardware(false)
                        .build()
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "content",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen._110sdp)),
                alignment = Alignment.Center,
            )
            Image(
                painter = painter,
                contentDescription = "content",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                alignment = Alignment.Center,
            )
        }
    }
    else{
        BannerCarouselSection(first)
    }
}




@Composable
fun BannerCarouselSection(first: List<Dashboard.Item.SubItem>
) {
    when (!first.isNullOrEmpty()) {
        true -> {
            BannerCarousel(first)
        }
        false -> {
            BlankWidget()
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerCarousel(first: List<Dashboard.Item.SubItem>) {
    val context = LocalContext.current
    val height = dimensionResource(id = R.dimen._120sdp)
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(3000)
        var newPosition: Int = pagerState.currentPage + 1
        if (newPosition > (first.size ?: 0) - 1) newPosition = 0
        pagerState.scrollToPage(newPosition)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .clickable(
                    onClick = {

                    },
                )
                .height(height)
                .background(color = colorResource(R.color.cardview_dark_background))
        ) {
            HorizontalPager(
                count = first.size,
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val painter =
                    if (first[pagerState.currentPage].imageUrl.isNullOrEmpty()) {
                        painterResource(R.drawable.ic_logo)
                    } else {
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = first[pagerState.currentPage].imageUrl)
                                .allowHardware(false)
                                .build()
                        )
                    }
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "content",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen._110sdp)),
                    alignment = Alignment.Center,
                )
                Image(
                    painter = painter,
                    contentDescription ="",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    alignment = Alignment.Center,
                )

            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight(Alignment.Bottom),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen._6sdp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DotsIndicator(
                        totalDots = pagerState.pageCount,
                        selectedIndex = pagerState.currentPage,
                        selectedColor = colorResource(R.color.colorPrimary),
                        unSelectedColor = colorResource(R.color.colorAccent)
                    )
                }
            }
        }
    }

}















/*
@Composable
fun ShowBannerElement(item: Dashboard.Item.SubItem) {
    Box(
        modifier = Modifier
            .size(width = 300.dp, height = 300.dp)
            .clip(RoundedCornerShape(5.dp))
    
    ) {
        BannerImage(url = item.imageUrl)
        item.title?.let {
            BannerText(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = it
            )

        }

    }

}


@Composable
private fun BannerImage(url: String) {
    LoadImage(
        modifier = Modifier.fillMaxSize(),
        image = url
    )
}

@Composable
private fun BannerText(
    modifier: Modifier,
    title: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(10.dp),
        text = title,
        style = MaterialTheme.typography.subtitle2.copy(
            color = Color.White,
            fontSize = 12.sp
        )
    )
}

 */