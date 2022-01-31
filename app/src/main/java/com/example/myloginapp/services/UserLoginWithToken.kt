package com.example.myloginapp.services

import com.example.myloginapp.model.BaseResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class UserLoginWithToken
{
     fun postLogin(stringUrl : String, jsonObject: JSONObject, stringContentType : String) : BaseResponseModel?
     {
         var modelUserResponse = BaseResponseModel() ?: null
         runBlocking ()
         {
             launch(Dispatchers.IO)
             {
                 modelUserResponse = WebService.requestPostHttps(stringUrl, jsonObject, stringContentType)
             }
         }
         return modelUserResponse
     }

}
