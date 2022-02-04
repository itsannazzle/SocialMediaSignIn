package com.example.myloginapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bytedance.sdk.open.tiktok.TikTokOpenApiFactory
import com.bytedance.sdk.open.tiktok.api.TikTokOpenApi
import com.bytedance.sdk.open.tiktok.common.handler.IApiEventHandler
import com.bytedance.sdk.open.tiktok.common.model.BaseReq
import com.bytedance.sdk.open.tiktok.common.model.BaseResp
import android.widget.Toast

import com.bytedance.sdk.open.tiktok.authorize.model.Authorization




class TikTokEntryActivity : Activity(), IApiEventHandler {
    private lateinit var _tikTokOpenApi : TikTokOpenApi
    override fun onCreate(savedInstanceState: Bundle?) {
        _tikTokOpenApi = TikTokOpenApiFactory.create(this)
        _tikTokOpenApi.handleIntent(intent,this)
        super.onCreate(savedInstanceState)

    }
    override fun onReq(baseRequest : BaseReq?) {

    }

    override fun onResp(baseResponse : BaseResp?) {
        if (baseResponse is Authorization.Response) {
            val response = baseResponse
            Toast.makeText(
                this,
                " code：" + response.errorCode + " errorMessage：" + response.errorMsg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onErrorIntent(intent: Intent?) {
        Toast.makeText(this, "Intent Error", Toast.LENGTH_LONG).show()
    }
}