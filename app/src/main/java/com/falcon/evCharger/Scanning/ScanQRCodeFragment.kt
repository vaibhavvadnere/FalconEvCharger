package com.falcon.evCharger.Scanning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.login.viewModel.ScanQrCodeViewModel
import com.falcon.evcharger.R
import com.google.zxing.ResultPoint
import com.iSay1.roamstick.data.model.request.GetDeviceRequest
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

class ScanQRCodeFragment : HomeBaseFragment() {

    private lateinit var barcodeView: CompoundBarcodeView

    private val scanQrCodeViewModel: ScanQrCodeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan_qr_code, container, false)
        barcodeView = view.findViewById(R.id.barcode_scanner)
        return view
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    val qrCodeText = it.text.substringAfter("-","")
                    Log.d("qr_code_value_log", ":) => $qrCodeText")

                    var getDeviceRequest: GetDeviceRequest = GetDeviceRequest()

                    getDeviceRequest.Device_ID = qrCodeText
                    scanQrCodeViewModel.getDevice(getDeviceRequest)

                    mActivity?.navController?.navigate(R.id.action_get_device)
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}