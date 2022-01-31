package com.example.myloginapp.model

import com.example.myloginapp.extentions.*
import com.example.myloginapp.services.UserLoginWithToken
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.twitter.sdk.android.core.models.User
import org.json.JSONObject

data class UserModel
    (
    var Email : String? = null,
    var Token : String? = null
)
{
    private val _jsonObject = JSONObject()
    private val _gson = Gson()
    private val networkCallLogin = UserLoginWithToken()

    fun loginValidation() : Boolean
    {
        return Email.emailBrinsValidation() && Token.passwordMinLength(6)
    }

    fun parsingToJson() : String
    {
        _jsonObject.put(STRING_EMAIL,Email)
        _jsonObject.put(STRING_OTP,Token)
        return _gson.toJson(JsonParser.parseString(_jsonObject.toString()))
    }

    fun postLogin() : UserResponseModel?
    {
        var modelUserResponse : UserResponseModel? = null
        val userLoginWithTokenResponse = networkCallLogin.postLogin(STRING_WEB_URL_LOGIN_TOKEN,_jsonObject,
            STRING_APPLICATION_TYPE)
        if (userLoginWithTokenResponse?.HTTPResponseCode == 200)
        {
            val userDataToJson = _gson.toJson(JsonParser.parseString(userLoginWithTokenResponse.Data))
            modelUserResponse = _gson.fromJson(userDataToJson,UserResponseModel::class.java)
        }
        else
        {
            modelUserResponse = null
        }
        return modelUserResponse
    }

}