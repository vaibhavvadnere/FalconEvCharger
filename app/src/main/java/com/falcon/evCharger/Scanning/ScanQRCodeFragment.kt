package com.falcon.evCharger.Scanning

import BarcodeScannerHandler
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.login.viewModel.ScanQrCodeViewModel
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evCharger.util.NetConnection
import com.falcon.evcharger.R
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetDeviceRequest
import com.journeyapps.barcodescanner.CompoundBarcodeView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ScanQRCodeFragment : HomeBaseFragment(), BarcodeScannerHandler.OnBarcodeScannedListener{
    private lateinit var barcodeView: CompoundBarcodeView

    private val scanQrCodeViewModel: ScanQrCodeViewModel by activityViewModels()
    private var barcodeScannerHandler: BarcodeScannerHandler? = null
    enum class UpdateEvent {
        SCAN_SUCCESS, SCAN_FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan_qr_code, container, false)
        mActivity?.let { scanQrCodeViewModel.init(it) }
        barcodeView = view.findViewById(R.id.barcode_scanner)

        barcodeScannerHandler = BarcodeScannerHandler(requireContext(), barcodeView,this)
        return view
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }


    override fun onResume() {
        super.onResume()
        barcodeScannerHandler?.resumeBarcodeScanner();
        // Start barcode scanning
        barcodeScannerHandler?.startBarcodeScanning();
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerHandler?.pauseBarcodeScanner();

    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(getDeviceResponse: GetDeviceResponse) {
        hideDialog()
        Log.e("getDeviceResponseLogs", ":" + Gson().toJson(getDeviceResponse))
        val bundle = Bundle()
        bundle.putParcelable(Constants.DEVICE_RESPONSE, getDeviceResponse)
        mActivity?.navController?.navigate(R.id.action_get_device,bundle)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.SCAN_FAILED -> {
                barcodeView.decodeContinuous(null)
                hideDialog()

                mActivity?.navController?.navigate(R.id.action_menu_in)
            }

            else -> {

            }
        }
}
    override fun onBarcodeScanned(barcode: String?) {
        Log.e("qr_scanned", ":success:")
        if (NetConnection.checkConnection(requireActivity())) {
            val qrCodeText = barcode?.substringAfter("-", "")
            var getDeviceRequest: GetDeviceRequest = GetDeviceRequest()
            getDeviceRequest.Device_ID = qrCodeText
            showDialog()
            scanQrCodeViewModel.getDevice(getDeviceRequest)
        } else {
            hideDialog()
            Toast.makeText(
                requireContext(),
                context?.resources?.getString(R.string.internet_issue),
                Toast.LENGTH_SHORT
            ).show()
            mActivity?.navController?.navigate(R.id.action_menu_in)
        }
    }
}