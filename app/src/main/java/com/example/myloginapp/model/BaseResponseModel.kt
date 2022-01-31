package com.example.myloginapp.model

data class BaseResponseModel
	(
	var ID:Int? = null,
	var HTTPResponseCode: Int? = null,
	var ServiceResponseCode:String? = null,
	var MessageTitle:String? = null,
	var MessageContent:String? = null,
	var Data:String? = null,
	var State: Int? = null
)

data class BaseResponseModelLowerCase
	(
	var iD:Int? = null,
	var HTTPResponseCode: Int? = null,
	var serviceResponseCode:String? = null,
	var messageTitle:String? = null,
	var messageContent:String? = null,
	var data:String? = null,
	var State: Int? = null
)




