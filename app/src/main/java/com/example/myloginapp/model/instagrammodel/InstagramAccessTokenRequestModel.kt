package com.example.myloginapp.model.instagrammodel

import com.example.myloginapp.extentions.*
import com.example.myloginapp.services.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InstragramAccessTokenRequestModel
    (
    var client_id : String? = null,
    var client_secret : String? = null,
    var grant_type : String? = null,
    var redirect_uri : String? = null,
    var code : String? = null
        )
{
    fun requestAccessToken(model: InstragramAccessTokenRequestModel) : InstagramAccessTokenResponseModel?
    {
        with(this)
        {
            client_id = model.client_id
            client_secret = model.client_secret
            grant_type = model.grant_type
            redirect_uri = model.redirect_uri
            code = model.code
        }

        val field: MutableList<RequestValueModel> = ArrayList<RequestValueModel>()
        field.add(RequestValueModel(INSTAGRAM_CLIENT_ID,client_id))
        field.add(RequestValueModel(INSTAGRAM_CLIENT_SECRET,client_secret))
        field.add(RequestValueModel(INSTAGRAM_GRANT_TYPE,grant_type))
        field.add(RequestValueModel(INSTAGRAM_REDIRECT_URI,redirect_uri))
        field.add(RequestValueModel(INSTAGRAM_CODE,code))
//        var modelInstagramAccessTokenResponse : InstagramAccessTokenResponseModel? = InstagramAccessTokenResponseModel()
//        runBlocking()
//        {
//           launch (Dispatchers.IO)
//           {
//               modelInstagramAccessTokenResponse =
//                   WebService.requestAccessTokenInstagram(STRING_INSTAGRAM_AUTH_ACCESS_TOKEN_URL,field,
//                       STRING_FORM_URL_ENCODED)
//           }
//        }
//        return modelInstagramAccessTokenResponse
        return WebService.requestAccessTokenInstagram(STRING_INSTAGRAM_AUTH_ACCESS_TOKEN_URL,field,
                       STRING_FORM_URL_ENCODED)
    }
}

class RequestValueModel(
    var RequestKey : String? = null,
    var RequestValue : String? = null
)