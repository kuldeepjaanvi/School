package com.enabling.neeladri.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.enabling.neeladri.components.ShowVerticalDivider
import com.enabling.neeladri.network.model.Dashboard
import com.enabling.neeladri.network.model.ItemViewType
import com.enabling.neeladri.network.model.SubItemViewType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.enabling.neeladri.R
@Composable
fun ShowDashboard(data: List<Dashboard.Item>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding))
    ) {
        itemsIndexed(items = data) { index, item ->
            when (item.viewType) {
                ItemViewType.HorizontalScroll -> ShowHorizontalElements(
                    item = item
                )
                ItemViewType.VerticalScroll -> ShowVerticalElements(
                    item = item
                )
            }
            if (index != item.data.size) Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

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