package com.example.myloginapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myloginapp.extentions.INSTAGRAM_CONSUMER_KEY
import com.example.myloginapp.extentions.INSTAGRAM_CONSUMER_KEY_SECRET
import com.example.myloginapp.extentions.STRING_INSTAGRAM_GRANT_TYPE
import com.example.myloginapp.extentions.STRING_INSTAGRAM_REDIRECT_URL
import com.example.myloginapp.model.instagrammodel.InstagramAccessTokenResponseModel
import com.example.myloginapp.model.instagrammodel.InstragramAccessTokenRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class InstagramViewModel : ViewModel()
{
    private val _instagramAccessTokenRequestModel = InstragramAccessTokenRequestModel()
    private val _modelInstagramAccessTokenResponse : MutableLiveData<InstagramAccessTokenResponseModel?> = MutableLiveData()
    val _getModelInstagramAccessTokenResponse : LiveData<InstagramAccessTokenResponseModel?> get() = _modelInstagramAccessTokenResponse

    fun requestAccessToken(model : InstragramAccessTokenRequestModel)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            try
            {
                val result = _instagramAccessTokenRequestModel.requestAccessToken(model)
                _modelInstagramAccessTokenResponse.postValue(result)
            } catch (exception : Exception)
            {
                Log.d("Anna","catch ${exception.message.toString()}")
            }
        }
    }

}