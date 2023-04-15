package com.enabling.neeladri.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.enabling.neeladri.components.AppTopBar
import com.enabling.neeladri.components.ShowError
import com.enabling.neeladri.components.ShowLoading
import com.enabling.neeladri.data.Result
import com.enabling.neeladri.ui.JetDeliveryTheme
import com.enabling.neeladri.ui.dashboard.ShowDashboard
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import  com.enabling.neeladri.R

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
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun JetDeliveryApp(viewModel: MainViewModel) {
    var showRandom by remember { mutableStateOf(false) }
    val context= LocalContext.current
    viewModel.loadData(showRandom)

    JetDeliveryTheme {
        Scaffold(
                    topBar = {
                AppTopBar(
                    name = stringResource(id = R.string.app_name),
                    showRandom = showRandom,
                    callClick = {
                        call(context)
                    }
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = colorResource(id = R.color.colorPrimary),
                    contentColor = Color.White,
                    onClick = {
                        openWHatsApp(context)

                    }) {
                    Icon(Icons.Filled.Call,"")
                }
            }
                    // displays Test Admob with blue background
        )

        {

            val data = viewModel.dashboardItems.observeAsState().value
            when (val data = viewModel.dashboardItems.observeAsState().value) {
                is Result.Loading -> {
                    ShowLoading()
                }
                is Result.Success -> {
                    ShowDashboard(
                         data.data ?: emptyList()
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

fun openWHatsApp(ctx:Context) {
        val contact = "+91 8871722186" // use country code with your phone number

        val url = "https://api.whatsapp.com/send?phone=$contact"
        try {
            val pm: PackageManager = ctx.getPackageManager()
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
           ctx. startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(
               ctx,
                "Whatsapp app not installed in your phone",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }

}

fun call(ctx:Context) {
    val u = Uri.parse("tel:" + "8871722186")
    val i = Intent(Intent.ACTION_DIAL, u)
    try {
        ctx.startActivity(i)
    } catch (s: SecurityException) {
        Toast.makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
            .show()
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