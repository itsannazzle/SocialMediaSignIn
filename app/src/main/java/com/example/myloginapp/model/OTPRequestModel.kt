package com.example.myloginapp.model

import com.example.myloginapp.extentions.STRING_EMAIL
import com.example.myloginapp.extentions.STRING_OTP
import com.example.myloginapp.extentions.emailBrinsValidation
import com.example.myloginapp.services.WebService
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class OTPRequestModel (
    var Email : String? = null,
    var OTP : String? = null
        )
{
    private val _jsonObject = JSONObject()
    private val _gson = Gson()

    fun dataValidation() : Boolean =  Email?.emailBrinsValidation() ?: false

    fun webRequestService(stringUrl : String, jsonObject: JSONObject) : BaseResponseModelLowerCase?
    {
        var modelBaseResponse = BaseResponseModelLowerCase() ?: null
        runBlocking()
        {
            launch(Dispatchers.IO)
            {
                modelBaseResponse = WebService.requestPostHttp(stringUrl, jsonObject)
            }
        }
        return modelBaseResponse
    }

    fun parsingToJson() : String
    {
        _jsonObject.put(STRING_EMAIL,Email)
        _jsonObject.put(STRING_OTP,OTP)
        return _gson.toJson(JsonParser.parseString(_jsonObject.toString()))
    }

}