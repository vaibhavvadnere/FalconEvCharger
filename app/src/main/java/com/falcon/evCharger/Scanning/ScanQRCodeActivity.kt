/*
package com.sensorcall.carealertandroid_new.thirdPartyDevices.addThirdPartyDevices.Scanning

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.Result
import com.sensorcall.carealertandroid_new.R
import com.sensorcall.carealertandroid_new.databinding.ActivityScanQrCodeBinding
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.core.ViewFinderView
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanQRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var activityScanQrCodeBinding: ActivityScanQrCodeBinding

    var context: Context = this@ScanQRCodeActivity
    var mActivity: ScanQRCodeActivity? = this@ScanQRCodeActivity

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_scan_qr_code)

        activityScanQrCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_qr_code)

        */
/* activityScanQrCodeBinding.ivBack.setOnClickListener(View.OnClickListener {
         })*//*


        //        val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
        mScannerView = object : ZXingScannerView(this) {
            override fun createViewFinderView(context: Context): IViewFinder {
                return CustomViewFinderView(context)
            }
        }

        (mScannerView as ZXingScannerView).startCamera()
        (mScannerView as ZXingScannerView).toggleFlash()
        activityScanQrCodeBinding.contentFrame.addView(mScannerView)
        (mScannerView as ZXingScannerView).setResultHandler(context as ZXingScannerView.ResultHandler)

        setEvents();

    }

    private fun setEvents() {

        activityScanQrCodeBinding.tvEnterManually.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.enter_manually_bottomsheet)
            bottomSheetDialog.setCancelable(true)
            bottomSheetDialog.setCanceledOnTouchOutside(true)

            val edtMacId = bottomSheetDialog.findViewById<EditText>(R.id.edt_mac_id)
            val btnContinue = bottomSheetDialog.findViewById<Button>(R.id.btn_continue)

            btnContinue?.setOnClickListener() {

                if (!edtMacId?.text?.trim().toString().equals("")) {
                    bottomSheetDialog.dismiss()

                    val intent = Intent()
                    intent.putExtra("scanned_card_id", edtMacId?.text?.trim().toString())
                    setResult(1, intent)
                    finish()

                } else {
                    Toast.makeText(mActivity, "Enter the MAC ID.", Toast.LENGTH_SHORT).show()
                }
            }

            bottomSheetDialog.show()

            */
/*val enterManuallyBottomSheetFragment = EnterManuallyBottomSheetFragment(mActivity!!)
            enterManuallyBottomSheetFragment.isCancelable = false
            enterManuallyBottomSheetFragment.registerListener(this)
            enterManuallyBottomSheetFragment.show(this, Constants.MODAL_BOTTOM_SHEET)*//*

        }


    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        */
/*Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();*//*

        Log.e("rawResult_Log", rawResult.text) // Prints scan results
        Log.e("rawResult_Log", rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        if (rawResult.text.contains("MAC")) {

            val resultArray: Array<String> = rawResult.text.split(",".toRegex()).toTypedArray()
            Log.e("resultArrayLog", ":" + resultArray.size)
            if (resultArray[0].contains("MAC")) {
                val macArray: Array<String> = resultArray[0].split("MAC:".toRegex()).toTypedArray()

                Log.e("MacIcScannedLog", " : 0 : " + macArray[0])
                Log.e("MacIcScannedLog", " : 1 : " + macArray[1])

                val intent = Intent()
                //            intent.putExtra("scanned_card_id", rawResult.text)
                intent.putExtra("scanned_card_id", macArray[1])
                setResult(1, intent)
                finish() //finishing activity
                val handler = Handler()
                handler.postDelayed({ mScannerView!!.resumeCameraPreview(this@ScanQRCodeActivity) }, 2000)
            } else {
                intent.putExtra("scanned_card_id", rawResult.text)
                setResult(1, intent)
                finish() //finishing activity
                //                Toast.makeText(mActivity, "Kindly scan valid QR/Bar code", Toast.LENGTH_SHORT).show()
            }
        } else {
            intent.putExtra("scanned_card_id", rawResult.text)
            setResult(1, intent)
            finish() //finishing activity
            //            Toast.makeText(mActivity, "Kindly scan valid QR/Bar code", Toast.LENGTH_SHORT).show()
        }
    }

    private class CustomViewFinderView : ViewFinderView {
        val PAINT = Paint()

        constructor(context: Context?) : super(context) {
            init()
        }

        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
            init()
        }

        private fun init() {
            PAINT.color = Color.WHITE
            PAINT.isAntiAlias = true
            val textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                                                          TRADE_MARK_TEXT_SIZE_SP.toFloat(), resources.displayMetrics)
            PAINT.textSize = textPixelSize
            setSquareViewFinder(true)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            drawTradeMark(canvas)
        }

        private fun drawTradeMark(canvas: Canvas) {
            val framingRect = framingRect
            val tradeMarkTop: Float
            val tradeMarkLeft: Float
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.textSize + 10
                tradeMarkLeft = framingRect.left.toFloat()
            } else {
                tradeMarkTop = 10f
                tradeMarkLeft = canvas.height - PAINT.textSize - 10
            }
            canvas.drawText("", tradeMarkLeft, tradeMarkTop, PAINT)
        }

        companion object {
            const val TRADE_MARK_TEXT = "ZXing"
            const val TRADE_MARK_TEXT_SIZE_SP = 40
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}*/
