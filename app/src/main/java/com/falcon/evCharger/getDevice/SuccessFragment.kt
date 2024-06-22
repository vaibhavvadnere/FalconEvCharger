package com.falcon.evCharger.getDevice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.viewModel.SuccessViewModel
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.SuccessFragmentBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SuccessFragment : HomeBaseFragment() {

    private lateinit var successFragmentBinding: SuccessFragmentBinding

    private val successViewModel: SuccessViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()
    enum class ViewOnClick {
        GET_VEHICLES, SIGN_UP, SELECT_UNITS,START_CHARGING,
    }

    enum class UpdateEvent {
        SUCCESS, FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        successFragmentBinding = SuccessFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { successViewModel.init(it) }

        successFragmentBinding.viewModel = successViewModel

        return successFragmentBinding.root
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vehicle_no: String? = arguments?.getString(Constants.VEHICLE_NO)
        val device_id: String? = arguments?.getString(Constants.DEVICE_ID)

        val message = context?.getString(R.string.falcon_success_sub_header, vehicle_no,device_id)
        successFragmentBinding.tvSubHeader.text = message
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            SuccessFragment.UpdateEvent.FAILED -> {
            }

            else -> {

            }
        }
    }
}