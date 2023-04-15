package com.enabling.neeladri.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.enabling.neeladri.R
import com.enabling.neeladri.network.model.Dashboard

@Composable
fun SingleBannerView(dataBlock: Dashboard.Item, onItemClick: (String) -> Unit) {
    when (!dataBlock.data.isNullOrEmpty()) {
        true -> {
            SingleBanner(dataBlock.data,onItemClick)
        }
        false -> {
            BlankWidget()
        }
    }
}

@Composable
fun SingleBanner(first: List<Dashboard.Item.SubItem>,onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val height = dimensionResource(id = R.dimen._150sdp)

        Box(modifier = Modifier
            .clickable(
                onClick = {
                    onItemClick(first.first().action.value?:"")
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
                        .data(data = first?.first().imageUrl)
                        .allowHardware(false)
                        .build()
                )
            }
//            Image(
//                painter = painterResource(R.drawable.ic_logo),
//                contentDescription = "content",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(dimensionResource(id = R.dimen._110sdp)),
//                alignment = Alignment.Center,
//            )
            Image(
                painter = painter,
                contentDescription = "content",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center,
            )
        }

}
