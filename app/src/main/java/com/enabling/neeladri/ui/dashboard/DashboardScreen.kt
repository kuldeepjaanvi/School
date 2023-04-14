package com.enabling.neeladri.ui.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.enabling.neeladri.network.model.Dashboard
import com.enabling.neeladri.network.model.ItemViewType
import com.enabling.neeladri.R
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowDashboard(list: List<Dashboard.Item>) {
    LazyColumn {
        item {
            Column(
                Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(
                        color = colorResource(R.color.colorPrimaryDark),
                    )
            ) {
                list.forEachIndexed { index, dataBlock ->
                    when (list[index].viewType) {
                       "bannerHorizontal"-> {
                            key(index) {
                                BannerSection(dataBlock)
                            }
                        }
                        "categoryHorizontal"-> {
                            key(index) {
                                CategorySection(dataBlock)
                            }
                        }
                       "categoryVertical"-> {
                            key(index) {
                                CategorySection(dataBlock)
                            }
                        }
                        else -> {
                            BlankWidget()
                        }
                    }
                }
            }

        }
    }
}



@Composable
fun BlankWidget(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {}
}


//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding))
//    ) {
//        itemsIndexed(items = data) { index, item ->
//            when (item.viewType) {
//                ItemViewType.HorizontalScroll -> ShowHorizontalElements(
//                    item = item
//                )
//                ItemViewType.VerticalScroll -> ShowVerticalElements(
//                    item = item
//                )
//
//            }
//            if (index != item.data.size) Spacer(modifier = Modifier.height(10.dp))
//        }
//    }






















/*
@Composable
private fun ShowHorizontalElements(item: Dashboard.Item) {
    item.header?.let {
        ShowHeader(title = it.title, hasMore = it.hasMore)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding))
    ) {
            itemsIndexed(item.data) { index, data ->
                when (data.viewType) {
                    SubItemViewType.Banner -> ShowBannerElement(
                        item = data
                    )
                    SubItemViewType.Category -> ShowCategoryElement(
                        item = data
                    )
                    else -> {
                        // do nothing
                    }
                }
                if (index != item.data.size) Spacer(modifier = Modifier.width(10.dp))
            }
    }
    AdvertView()
}

@Composable
private fun ShowVerticalElements(item: Dashboard.Item) {
    item.header?.let {
        ShowHeader(title = it.title, hasMore = it.hasMore)
    }
    item.data.forEachIndexed { index, data ->
        when (data.viewType) {
            SubItemViewType.Restaurant -> ShowRestaurantElement(
                item = data
            )
            else -> {
                // do nothing
            }
        }
        if (index != item.data.size) ShowVerticalDivider()
    }
    AdvertView()
}
@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "Advert Here",
        )
    } else {
        AndroidView(
            modifier = modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.BANNER
                    adUnitId = context.getString(R.string.ad_id_banner)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
*/