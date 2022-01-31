package com.example.myloginapp.services

import com.example.myloginapp.model.BaseResponseModel
import com.example.myloginapp.model.BaseResponseModelLowerCase
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

fun String.parseStringToJson() : BaseResponseModel  {
    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
    val jsonString= gsonBuilder.toJson(JsonParser.parseString(this))
    return gsonBuilder.fromJson(jsonString, BaseResponseModel::class.java)
}

fun String.parseStringToJson2() : BaseResponseModelLowerCase  {
    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
    val jsonString= gsonBuilder.toJson(JsonParser.parseString(this))
    return gsonBuilder.fromJson(jsonString, BaseResponseModelLowerCase::class.java)
}