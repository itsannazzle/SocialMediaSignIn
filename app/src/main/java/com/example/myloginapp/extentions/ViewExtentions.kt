package com.example.myloginapp.extentions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myloginapp.R
import java.util.regex.Matcher
import java.util.regex.Pattern

fun replaceFragment(fragmentManager: FragmentManager, destination : Fragment, bundle: Bundle? = null)
{
    destination.arguments = bundle
    fragmentManager.beginTransaction()
        .replace(R.id.constraintLayoutMain, destination)
        .addToBackStack(null)
        .commit()
}

fun String?.emailBrinsValidation() : Boolean = this?.contains("@brins.co.id") ?: false

fun String?.passwordValidation(): Boolean
{
    val patternPassword: Pattern =
        Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#\$%!]).{8,20})")
    val matcherPassword: Matcher = patternPassword.matcher(this)
    return matcherPassword.matches()
}

fun String?.passwordMinLength(intLength : Int) : Boolean = this?.length!! >= intLength