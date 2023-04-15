package com.enabling.neeladri.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.enabling.neeladri.R

class WebviewScreen : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var back: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val extras = intent.extras
        val webUrl = extras!!.getString("url")!!
        webView =findViewById(R.id.staticPagesWebView)!!
        back =findViewById(R.id.back)!!
        startWebView(webUrl)
        back.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {
                if (progress < 100) {
                   // loader show
                }
                if (progress == 100) {
                 // loader hide
                }
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.clearCache(true)
        webView.clearFocus()
        webView.clearHistory()
        webView.settings.useWideViewPort = false
        webView.settings.loadWithOverviewMode = false
        webView.loadUrl(url)
    }
}