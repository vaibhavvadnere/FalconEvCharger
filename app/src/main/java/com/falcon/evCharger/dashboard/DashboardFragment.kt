package com.falcon.evCharger.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.dashboard.viewModel.DashboardViewModel
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.response.WalletResponse
import com.falcon.evCharger.util.NetConnection
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.DashboardFragmentBinding
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DashboardFragment : HomeBaseFragment() {

    private lateinit var dashboardFragmentBinding: DashboardFragmentBinding

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    //Class to Handle all the button click
    enum class ViewOnClick {
        SCAN_QR
    }

    //Class to Handle all the Events
    enum class UpdateEvent {
        API_FAILED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dashboardFragmentBinding = DashboardFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { dashboardViewModel.init(it) }

        mActivity!!.unRegisterOnBackPress()

        dashboardFragmentBinding.viewModel = dashboardViewModel

        Log.e("balanceLogsN", "   :  " + sharePrefRepo.balance)
        //put the value of available balance from api
        dashboardFragmentBinding.tvAvailableBalance.text = "₹ ${sharePrefRepo.balance}"

        val textColor = context?.let { Constants.getColor(sharePrefRepo.balance.toFloat(), it) }
        if (textColor != null) {
            dashboardFragmentBinding.tvAvailableBalance.setTextColor(textColor)
        }
        return dashboardFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()

        showDialog()

        val getTransactionHistoryRequest = GetTransactionHistoryRequest()
        getTransactionHistoryRequest.UserID = SharePrefRepo.getInstance().getInt(Constants.ID).toString()

        dashboardViewModel.getWalletDetails(getTransactionHistoryRequest)


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
                Log.e("onSignInClick", ":clicked  SIGN_IN:")/*val intent = Intent(context, ScanQRCodeActivity::class.java)
                mActivity?.startActivityForResult(intent, 1) // Activity is started with requestCode 2
                hideDialog()*/

                requestCameraPermission()
            }
        }
    }


    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {
            UpdateEvent.API_FAILED -> {
                hideDialog()
            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(walletResponse: WalletResponse) {

        Log.e("c", "  : " + walletResponse)

        hideDialog()

        try {
            sharePrefRepo.setBalance(walletResponse.Balance.toString())

            dashboardFragmentBinding.amountInput.setText(walletResponse.Balance.toString())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Permission already granted, start QR code scanning
            startQrCodeScanning()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {


            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Camera permission granted, start QR code scanning
                startQrCodeScanning()
            } else {
                // Camera permission denied, show a message or handle accordingly
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startQrCodeScanning() {
        Log.e("qr_scanning", ":clicked  startQrCodeScanning:")
        if (NetConnection.checkConnection(requireActivity())) {
            mActivity?.navController?.navigate(R.id.action_scan_qr)
        } else {
            Toast.makeText(
                requireContext(), context?.resources?.getString(R.string.internet_issue), Toast.LENGTH_SHORT
            ).show()

        }
    }
}

