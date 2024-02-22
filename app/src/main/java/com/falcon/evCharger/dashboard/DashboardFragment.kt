package com.falcon.evCharger.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.dashboard.viewModel.DashboardViewModel
import com.falcon.evcharger.databinding.DashboardFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DashboardFragment : HomeBaseFragment() {

    private lateinit var dashboardFragmentBinding: DashboardFragmentBinding

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        SCAN_QR
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        dashboardFragmentBinding = DashboardFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { dashboardViewModel.init(it) }

        dashboardFragmentBinding.viewModel = dashboardViewModel



        return dashboardFragmentBinding.root
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
            ViewOnClick.SCAN_QR -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                /*val intent = Intent(context, ScanQRCodeActivity::class.java)
                mActivity?.startActivityForResult(intent, 1) // Activity is started with requestCode 2
                hideDialog()*/
            }

            else -> {

            }
        }
    }


}

