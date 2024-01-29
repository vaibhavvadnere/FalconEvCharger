package com.falcon.evCharger.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.splash.viewmodel.SplashFragmentViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.SplashFragmentBinding

class SplashFragment : HomeBaseFragment() {

    private lateinit var splashFragmentBinding: SplashFragmentBinding

    private val splashFragmentViewModel: SplashFragmentViewModel by activityViewModels()

    private val splashTimeout: Long = 2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        splashFragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { splashFragmentViewModel.init(it) }

        splashFragmentBinding.viewModel = splashFragmentViewModel

        return splashFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
                {
                    mActivity?.navController?.navigate(R.id.action_lets_in)
                }, splashTimeout)

    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        //        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        //        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}

