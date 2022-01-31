package com.example.myloginapp.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myloginapp.R
import com.example.myloginapp.databinding.FragmentEmailForgotPasswordBinding
import com.example.myloginapp.extentions.STRING_DATA
import com.example.myloginapp.extentions.STRING_EMAIL
import com.example.myloginapp.extentions.STRING_WEB_URL_REQ_OTP
import com.example.myloginapp.model.OTPRequestModel
import com.example.myloginapp.model.OTPResponseModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class UserRegisterFragment : Fragment()
{


    //region DECLARATION

    private var _bindingEmailForgotPasswordFragment : FragmentEmailForgotPasswordBinding? = null
    private val _getBindingEmailForgotPasswordFragment get() = _bindingEmailForgotPasswordFragment
    private lateinit var _countdownTimer : CountDownTimer

    //endregion


    //region ONCREATEVIEW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _bindingEmailForgotPasswordFragment = FragmentEmailForgotPasswordBinding.inflate(layoutInflater, container,false)
        return _getBindingEmailForgotPasswordFragment?.root
    }

    //endregion


    //region ONVIEWCREATED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val modelOtpRequest = OTPRequestModel()
        val gson = GsonBuilder().create()
        _countdownTimer = object: CountDownTimer(20000, 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                _getBindingEmailForgotPasswordFragment?.textViewTime?.visibility = View.VISIBLE
                _getBindingEmailForgotPasswordFragment?.textViewTime?.text = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
                _getBindingEmailForgotPasswordFragment?.buttonEmailForgotPassword?.isEnabled = false
            }

            override fun onFinish()
            {
                _getBindingEmailForgotPasswordFragment?.buttonEmailForgotPassword?.isEnabled = true
            }
        }

       _getBindingEmailForgotPasswordFragment?.buttonEmailForgotPassword?.setOnClickListener()
       {
           modelOtpRequest.Email = _getBindingEmailForgotPasswordFragment?.editTextForgotEmail?.text?.toString()
           requestOtp(modelOtpRequest, gson)
       }

    }


    //endregion


    //region REGISTEREMAIL

    private fun registerNewEmail()
    {
//        jsonObject.put(STRING_EMAIL, modelOtpRequest.Email)
//        val jsonString = gson.toJson(JsonParser.parseString(jsonObject.toString()))
//        jsonObject2.put(STRING_DATA, jsonString)
//        val modelRegisterResponse = OTPRequestModel().webRequestService(
//            STRING_WEB_URL_REGISTER_EMAIL, jsonObject2
//        )
//        with(modelRegisterResponse!!)
//        {
//            if (serviceResponseCode != null) {
//                Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    //endregion


    //region REQUESTOTP

    private fun requestOtp(
        modelOtpRequest: OTPRequestModel,
        gson: Gson
    )
    {
        val jsonObject3 = JSONObject()
        val jsonObject4 = JSONObject()
        jsonObject3.put(STRING_EMAIL, modelOtpRequest.Email)
        val jsonString2 = gson.toJson(JsonParser.parseString(jsonObject3.toString()))
        jsonObject4.put(STRING_DATA, jsonString2)
        val modelRegisterResponse = OTPRequestModel().webRequestService(
            STRING_WEB_URL_REQ_OTP, jsonObject4
        )
        if (modelRegisterResponse?.serviceResponseCode != null) {
            _getBindingEmailForgotPasswordFragment?.progressBarOTP?.visibility = View.GONE
            _getBindingEmailForgotPasswordFragment?.textViewOTP?.visibility = View.VISIBLE
            val jsonStringOTP = gson.toJson(JsonParser.parseString(modelRegisterResponse.data))
            val modelUser = gson.fromJson(jsonStringOTP, OTPResponseModel::class.java)
            _getBindingEmailForgotPasswordFragment?.textViewOTP?.text =
                getString(R.string.your_otp_is, modelUser.OTP)
        } else
        {
            _getBindingEmailForgotPasswordFragment?.textViewOTP?.visibility = View.VISIBLE
            _getBindingEmailForgotPasswordFragment?.textViewOTP?.text =
                modelRegisterResponse?.messageContent
        }
        _countdownTimer.start()
    }

    //endregion


    //region ONDESTROY

    override fun onDestroy()
    {
        super.onDestroy()
        _bindingEmailForgotPasswordFragment = null
    }

    //endregion


}