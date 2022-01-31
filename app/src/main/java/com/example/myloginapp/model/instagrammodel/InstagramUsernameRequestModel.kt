package com.example.myloginapp.model.instagrammodel

import com.example.myloginapp.extentions.*
import com.example.myloginapp.services.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InstagramUsernameRequestModel
    (
    var AccessToken : String? = null,
    var IdUser : String? = null
            )
{
        fun requestUsername() : InstagramUsernameResponseModel?
        {
            val field : MutableList<RequestValueModel> = ArrayList<RequestValueModel>()
            field.add(RequestValueModel(INSTAGRAM_FIELDS, STRING_INSTAGRAM_FIELDS))
            field.add(RequestValueModel(INSTAGRAM_ACCESS_TOKEN, AccessToken))
            var modelInstagramUsernameResponseModel : InstagramUsernameResponseModel? = InstagramUsernameResponseModel()

            runBlocking()
            {
                launch(Dispatchers.IO)
                {
                    modelInstagramUsernameResponseModel = WebService.requestUsernameInstagram(
                        "$STRING_INSTAGRAM_GRAPH_API$IdUser?$INSTAGRAM_FIELDS=$STRING_INSTAGRAM_FIELDS" +
                                "&$INSTAGRAM_ACCESS_TOKEN=" +
                                AccessToken)
                }
            }
            return modelInstagramUsernameResponseModel
        }

}