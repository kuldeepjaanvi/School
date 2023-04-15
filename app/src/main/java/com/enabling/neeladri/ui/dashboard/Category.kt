package com.enabling.neeladri.ui.dashboard

import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.enabling.neeladri.network.model.Dashboard
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.enabling.neeladri.R
import com.enabling.neeladri.components.fontDimensionResource
import com.google.accompanist.flowlayout.FlowMainAxisAlignment




@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategorySection(dataBlock: Dashboard.Item,onItemClick: (String) -> Unit) {
    when (!dataBlock.data.isNullOrEmpty()) {
        true -> {
            CategoryWidget(dataBlock.data,dataBlock.header,onItemClick)
        }
        false -> {
            BlankWidget()
        }
    }
}




@Keep
@ExperimentalPagerApi
@Composable
fun CategoryWidget(first: List<Dashboard.Item.SubItem>,header: Dashboard.Item.Header?,onItemClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .background(colorResource(R.color.colorPrimaryDark)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._10sdp)))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(id = R.dimen._10sdp))

            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    text = "${header?.title}",
                    color = colorResource(R.color.colorPrimary),
                    fontSize = fontDimensionResource(id = R.dimen._14ssp),
                    fontStyle = FontStyle.Normal,
                    maxLines = 2,
                )
            }
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen._5sdp))
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                mainAxisAlignment = FlowMainAxisAlignment.Start
            ) {
                if (first != null) {
                    first.forEachIndexed { index, element ->
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .wrapContentWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoryItem(element, index,onItemClick)
                        }
                    }
                }
            }
        }
    }
}

@Keep
@Composable
fun CategoryItem(element: Dashboard.Item.SubItem, index: Int,onItemClick: (String) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp / 3
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.colorPrimaryDark))
            .width(screenWidth.dp)
            .height(dimensionResource(id = R.dimen._140sdp))
            .padding(dimensionResource(id = R.dimen._3sdp))
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .clickable {
                    onItemClick(element.action.value ?: "")
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen._90sdp))
                    .height(dimensionResource(id = R.dimen._90sdp))
                    .clickable(
                        onClick = {
onItemClick(element.action.value?:"")
                        },
                    ), shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp)),
                elevation = dimensionResource(id = R.dimen._8sdp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    val painter = if (element?.imageUrl == null) {
                        painterResource(R.drawable.ic_logo)
                    } else {
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = element.imageUrl)
                                .allowHardware(false)
                                .build()
                        )
                    }
//                    Image(
//                        painter = painterResource(R.drawable.ic_logo),
//                        contentDescription = "content",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(),
//                        contentScale = ContentScale.FillBounds
//                    )
                    Image(
                        painter = painter,
                        alignment = Alignment.Center,
                        contentDescription = "content",
                        modifier = Modifier
                            //.padding(dimensionResource(id = R.dimen._20sdp))
                        , contentScale = ContentScale.Inside,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen._50sdp))
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen._4sdp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = element?.title ?: "",
                    color = colorResource(R.color.colorPrimary),
                    fontSize = fontDimensionResource(id = R.dimen._11ssp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    maxLines = 2, overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
















/*
@Composable
fun ShowCategoryElement(item: Dashboard.Item.SubItem) {
    Column {
        Spacer(modifier = Modifier.height(5.dp)) // added to support space for header
        CategoryImage(item = item)
        Spacer(modifier = Modifier.height(5.dp))
        CategoryInfo(
            title = item.title,
            subTitle = item.subTitle
        )
    }
}

@Composable
private fun CategoryImage(item: Dashboard.Item.SubItem) {
    val bgColor = item.meta?.bgColor?.let { color ->
        getColor(color)
    } ?: Color.Blue

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(bgColor, shape = RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        LoadImage(
            image = item.imageUrl,
            tint = Color.White
        )
    }
}

@Composable
private fun CategoryInfo(title: String?, subTitle: String?) {
    title?.let {
        PrimaryText {
            Text(
                text = it,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
    subTitle?.let {
        SecondaryText {
            Text(
                text = it,
                style = MaterialTheme.typography.overline
            )
        }
    }
}

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}

 */