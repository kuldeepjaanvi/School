package com.enabling.neeladri.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.enabling.neeladri.components.AppTopBar
import com.enabling.neeladri.components.ShowError
import com.enabling.neeladri.components.ShowLoading
import com.enabling.neeladri.data.Result
import com.enabling.neeladri.ui.JetDeliveryTheme
import com.enabling.neeladri.ui.dashboard.ShowDashboard
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.enabling.neeladri.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContent {
            JetDeliveryApp(viewModel)
        }
    }
}

@Composable
fun JetDeliveryApp(viewModel: MainViewModel) {
    var showRandom by remember { mutableStateOf(false) }

    viewModel.loadData(showRandom)

    JetDeliveryTheme {
        Scaffold(
                    topBar = {
                AppTopBar(
                    name = stringResource(id = R.string.app_name),
                    showRandom = showRandom,
//                    onShowRandomDashboardChange = {
//                        showRandom = it
//                    }

                )


            }
                    // displays Test Admob with blue background


        ) {
            when (val data = viewModel.dashboardItems.observeAsState().value) {
                is Result.Loading -> {
                    ShowLoading()
                }
                is Result.Success -> {
                    ShowDashboard(
                        data = data.data ?: emptyList()
                    )
                }
                is Result.Failure -> {
                    ShowError(
                        message = data.error.message ?: "",
                        onRetry = {
                            viewModel.loadData(showRandom)
                        }
                    )
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}

@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .background(Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = White,
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