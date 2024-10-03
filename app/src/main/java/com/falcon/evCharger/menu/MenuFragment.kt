package com.falcon.evCharger.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.menu.viewModel.MenuViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.MenuFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MenuFragment : HomeBaseFragment() {

    private lateinit var menuFragmentBinding: MenuFragmentBinding

    private val menuViewModel: MenuViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        SIGN_IN, SIGN_UP, SCAN_QR_BARCODE, LOGOUT_USER,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        menuFragmentBinding = MenuFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { menuViewModel.init(it) }

        mActivity?.registerOnBackPress { this }

        menuFragmentBinding.viewModel = menuViewModel

        return menuFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.SIGN_IN -> {
                /*Log.e("onSignInClick", ":clicked  SIGN_IN:")
                mActivity?.navController?.navigate(R.id.action_sign_in)*/
            }

            ViewOnClick.SIGN_UP -> {
            }

            ViewOnClick.LOGOUT_USER -> {

            }

            else -> {

            }
        }
    }


}

