package com.example.myloginapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myloginapp.constant.STRING_BUNDLE_USERNAME
import com.example.myloginapp.databinding.FragmentHomeBinding
import com.example.myloginapp.extentions.STRING_USER_EMAIL
import com.example.myloginapp.extentions.replaceFragment
import com.twitter.sdk.android.core.TwitterCore

class HomeFragment : Fragment()
{


    //region DECLARATION

    private var _bindingHomeFragment : FragmentHomeBinding? = null
    private val _getBindingHomeFragment get() = _bindingHomeFragment

    //endregion


    //region ONCREATEVIEW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
     {
        // Inflate the layout for this fragment
         val stringEmailUser = arguments?.getString(STRING_USER_EMAIL)
         val stringTwitterUsername = arguments?.getString(STRING_BUNDLE_USERNAME)
         _bindingHomeFragment = FragmentHomeBinding.inflate(inflater,container,false)
         _getBindingHomeFragment?.textViewEmail?.text = stringTwitterUsername
         return _getBindingHomeFragment?.root
    }

    //endregion


    //region ONVIEWCREATED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _getBindingHomeFragment?.buttonLogout?.setOnClickListener ()
        {
            TwitterCore.getInstance().sessionManager.clearActiveSession()
            replaceFragment(requireFragmentManager(),LoginFragment())
        }
    }

    //endregion


}