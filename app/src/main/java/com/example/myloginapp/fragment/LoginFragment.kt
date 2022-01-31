package com.example.myloginapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myloginapp.constant.STRING_BUNDLE_USERNAME
import com.example.myloginapp.databinding.FragmentLoginBinding
import com.example.myloginapp.dialog.InstagramAuthenticationDialog
import com.example.myloginapp.extentions.*
import com.example.myloginapp.interfaces.InstagramAuthenticationInterface
import com.example.myloginapp.model.AuthResponseModel
import com.example.myloginapp.model.OTPRequestModel
import com.example.myloginapp.model.UserModel
import com.example.myloginapp.model.instagrammodel.InstagramAccessTokenResponseModel
import com.example.myloginapp.model.instagrammodel.InstagramUsernameRequestModel
import com.example.myloginapp.model.instagrammodel.InstragramAccessTokenRequestModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.twitter.sdk.android.core.*
import org.json.JSONObject


class LoginFragment : Fragment(), InstagramAuthenticationInterface
{


    //region DECLARATION

    private var _bindingLoginFragment : FragmentLoginBinding? = null
    val _getBindingLoginFragment get() = _bindingLoginFragment
    private val _otpRequestModel = OTPRequestModel()
    private val _instagramGraphRequestModel = InstagramUsernameRequestModel()
    private val _instagramAccessTokenReqModel = InstragramAccessTokenRequestModel()
    private val _jsonObject = JSONObject()
    private val _gson = GsonBuilder().setPrettyPrinting().create()
    private var _counter = 0
    private val _handler = Handler (Looper.getMainLooper())
    private lateinit var _instagramInstagramAuthenticationDialog : InstagramAuthenticationDialog

    //endregion


    //region ONCREATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _bindingLoginFragment = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return _getBindingLoginFragment?.root
    }

    //endregion


    //region TWITTERLOGIN

    fun sessionTwitter(sessionTwitter : TwitterSession)
    {
        val bundle = Bundle()
        bundle.putString(STRING_BUNDLE_USERNAME,sessionTwitter.userName)
        replaceFragment(requireFragmentManager(),HomeFragment(),bundle)
    }


   private fun callbackTwitter()
    {
        _getBindingLoginFragment?.buttonTwitter?.callback = object : Callback<TwitterSession>()
        {
            override fun success(result: Result<TwitterSession>?)
            {
                val sessionTwitter = TwitterCore.getInstance().sessionManager.activeSession
                sessionTwitter(sessionTwitter)
            }

            override fun failure(exception: TwitterException?)
            {
                Toast.makeText(requireContext(),"Login failed. $exception",Toast.LENGTH_LONG).show()
            }
        }
    }

    //endregion


    //region INSTAGRAMLOGIN

    override fun onCodeReceived(accessToken : String)
    {
        requestAccessToken(accessToken)
        _getBindingLoginFragment?.progressBarLogin?.visibility = View.VISIBLE
    }


    private fun requestAccessToken(stringCode : String)
    {
        _instagramAccessTokenReqModel.client_id = INSTAGRAM_CONSUMER_KEY
        _instagramAccessTokenReqModel.client_secret = INSTAGRAM_CONSUMER_KEY_SECRET
        _instagramAccessTokenReqModel.grant_type = STRING_INSTAGRAM_GRANT_TYPE
        _instagramAccessTokenReqModel.redirect_uri = STRING_INSTAGRAM_REDIRECT_URL
        _instagramAccessTokenReqModel.code = stringCode
        val accessToken = _instagramAccessTokenReqModel.requestAccessToken()
        if (accessToken != null)
        {
            requestUsername(accessToken)
        } else
        {
            Toast.makeText(requireContext(),"Request token error",Toast.LENGTH_LONG).show()
            _getBindingLoginFragment?.progressBarLogin?.visibility = View.GONE
        }


    }

    private fun requestUsername(responseModel: InstagramAccessTokenResponseModel)
    {
        _instagramGraphRequestModel.AccessToken = responseModel.access_token
        _instagramGraphRequestModel.IdUser = responseModel.user_id
        val responseUsername = _instagramGraphRequestModel.requestUsername()
        if (responseUsername != null) {
            val bundle = Bundle()
            bundle.putString(STRING_BUNDLE_USERNAME, responseUsername.username)
            replaceFragment(requireFragmentManager(), HomeFragment(), bundle)
            _getBindingLoginFragment?.progressBarLogin?.visibility = View.GONE
        } else
        {
            _getBindingLoginFragment?.progressBarLogin?.visibility = View.GONE
            Toast.makeText(requireContext(),"Request username error",Toast.LENGTH_LONG).show()
        }
    }

    private fun loginInstagram()
    {
        _instagramInstagramAuthenticationDialog = context?.let { InstagramAuthenticationDialog(it) }!!
        _instagramInstagramAuthenticationDialog.instagramAuthenticationDialog(instagramAuthenticationInterface = this)
        _getBindingLoginFragment?.imageButtonLinkedIn?.setOnClickListener()
        {
            _instagramInstagramAuthenticationDialog.setCancelable(true)
            _instagramInstagramAuthenticationDialog.show()
        }
    }

    //endregion


    //region ONVIEWCREATED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
            _getBindingLoginFragment?.textViewForgotPassword?.setOnClickListener()
        {
            replaceFragment(requireFragmentManager(), UserRegisterFragment())
        }
        _getBindingLoginFragment?.buttonLogin?.setOnClickListener()
        {
            _otpRequestModel.Email =  _getBindingLoginFragment?.editTextEmail?.text?.toString()
            _otpRequestModel.OTP = _getBindingLoginFragment?.editTextPassword?.text?.toString()

            _handler.postDelayed(
                {
                verifyOTP()
            }, 5000)
        }

        callbackTwitter()

        loginInstagram()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        _getBindingLoginFragment?.buttonTwitter?.onActivityResult(requestCode, resultCode, data)
    }

    //endregion


    //region USERLOGINWITHTOKEN

    private fun userDataLoginValidation()
    {
        val modelUser = UserModel()
        modelUser.Email = _getBindingLoginFragment?.editTextEmail?.text.toString()
        modelUser.Token = _getBindingLoginFragment?.editTextPassword?.text.toString()
        if (modelUser.loginValidation())
        {
            userLoginWithToken(modelUser)
        } else
        {
//            _getBindingLoginFragment?.textInputLayoutEmail?.helperText = getString(R.string)
//            _getBindingLoginFragment?.textInputLayoutPassword?.helperText = getString(R.string.helper_password)
        }
    }

    private fun userLoginWithToken(modelUser: UserModel)
    {
        modelUser.parsingToJson()
        val modelUserResponse = modelUser.postLogin()
        if (modelUserResponse == null)
        {
            Toast.makeText(requireContext(),"Login failed", Toast.LENGTH_LONG).show()
        } else
        {
            val bundle = Bundle()
            bundle.putString(STRING_USER_EMAIL,modelUserResponse.Email)
            replaceFragment(requireFragmentManager(), HomeFragment(), bundle)
        }
    }

    //endregion


    //region USERLOGINWITHOTP

    private fun verifyOTP()
    {
        if (_counter < 3)
        {
            val stringDataUserOtp = _otpRequestModel.parsingToJson()
            _jsonObject.put(STRING_DATA,stringDataUserOtp)
            val modelOtpResponse = OTPRequestModel().webRequestService(
                STRING_WEB_URL_AUTH,_jsonObject)
            if (modelOtpResponse?.serviceResponseCode == null)
            {
                _counter++
                Toast.makeText(requireContext(),"Your otp is wrong",Toast.LENGTH_SHORT).show()
            }
            if (modelOtpResponse?.data != null)
            {
                val jsonStringOTP= _gson.toJson(JsonParser.parseString(modelOtpResponse.data))
                val modelAuth = _gson.fromJson(jsonStringOTP, AuthResponseModel::class.java)
                val bundle = Bundle()
                bundle.putString(STRING_USER_EMAIL,modelAuth.Email)
                replaceFragment(requireFragmentManager(),HomeFragment(), bundle)
            }

        } else
        {
            Toast.makeText(requireContext(),"Account blocked, 3x wrong otp",Toast.LENGTH_LONG).show()
        }
    }




    //endregion


    //region ONDESTROY

    override fun onDestroy()
    {
        super.onDestroy()
        _bindingLoginFragment = null
    }

    //endregion


}