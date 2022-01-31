package com.example.myloginapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myloginapp.R
import com.example.myloginapp.databinding.ActivityMainBinding
import com.example.myloginapp.extentions.replaceFragment
import com.example.myloginapp.fragment.HomeFragment
import com.example.myloginapp.fragment.LoginFragment
import com.twitter.sdk.android.core.*


/*

Attribute - Start
Description : Attribute for lead model, used for link to product and relationship manager.
Author      : Anna Karenina.
Created on  : Tuesday, 25 November 2022.                  Updated on : Tuesday, 17 November 2020.
Created by  : Anna Karenina.                              Updated by : Anna Karenina.
Version     : 1.0.0.

*/

class MainActivity : AppCompatActivity()
{


    //region DECLARATION

    private var _bindingMainActivity: ActivityMainBinding? = null
    private val _getBindingMainActivity get() = _bindingMainActivity
    private var _handlerTimeout: Handler? = null
    private var _runnableInteractionTimeout: Runnable? = null
    private val _fragmentLogin : LoginFragment = LoginFragment()

    //endregion


    //region ONCREATE

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Twitter.initialize(this)
        _bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraintLayoutMain, _fragmentLogin)
            .commitNow()
        setContentView(_getBindingMainActivity?.root)
        _handlerTimeout =  Handler();
        _runnableInteractionTimeout =  Runnable {
            //System.exit(0)
        }
        startHandler()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        _fragmentLogin._getBindingLoginFragment?.buttonTwitter?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetHandler()
    }

    //endregion


    //region HANDLER

    private fun resetHandler() {
        _runnableInteractionTimeout?.let { _handlerTimeout?.removeCallbacks(it) };
        _runnableInteractionTimeout?.let { _handlerTimeout?.postDelayed(it, 10*1000) }; //for 10 second

    }

    private fun startHandler() {
        _runnableInteractionTimeout?.let { _handlerTimeout?.postDelayed(it, 10*1000) }; //for 10 second
    }

    //endregion


    //region ONDESTROY

    override fun onDestroy()
    {
        _bindingMainActivity = null
        super.onDestroy()
    }

    //endregion


}