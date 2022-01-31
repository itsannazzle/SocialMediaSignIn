package com.example.myloginapp.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myloginapp.R
import com.example.myloginapp.extentions.STRING_INSTAGRAM_AUTH
import com.example.myloginapp.extentions.STRING_INSTAGRAM_REDIRECT_URL
import com.example.myloginapp.interfaces.InstagramAuthenticationInterface

class InstagramAuthenticationDialog(context: Context) :
    Dialog(context)
{


    //region DECLARATION

    private var _instagramAuthenticationInterface: InstagramAuthenticationInterface? = null
    private val _stringUrl = STRING_INSTAGRAM_AUTH
    private lateinit var _webView : WebView

    //endregion


    //region ONCREATE

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.webview_authentication)
        initializeWebView()
    }

    //endregion


    //region DIALOG

    fun instagramAuthenticationDialog(instagramAuthenticationInterface: InstagramAuthenticationInterface?)
    {
        this._instagramAuthenticationInterface = instagramAuthenticationInterface!!
    }

    //endregion


    //region WEBVIEW

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeWebView()
    {
        _webView = findViewById(R.id.webView)
        _webView.settings.javaScriptEnabled = true
        _webView.loadUrl(_stringUrl)
        _webView.webViewClient = object : WebViewClient()
        {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
            {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?)
            {
                super.onPageFinished(view, url)
                if (url?.startsWith(STRING_INSTAGRAM_REDIRECT_URL) == true)
                {
                    val startIndex = url.indexOf("code=")
                    val endIndex = url.lastIndexOf("#")
                    val code = url.substring(startIndex + 5,endIndex)
                    _instagramAuthenticationInterface?.onCodeReceived(code)
                    dismiss()
                }
            }
        }
    }

    //endregion


}