package com.falcon.evCharger.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.util.NetConnection.checkConnection
import com.falcon.evcharger.R

abstract class HomeBaseFragment : Fragment() {
    protected abstract fun connectionAvailable()
    private var progressDialog: ProgressDialog? = null

    var mActivity: EVMainActivity? = null

    private fun checkConnectionIsAvailable() {
        if (checkConnection(mActivity!!)) {
            connectionAvailable()
        } else {
            showNoInternetConnectionDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as EVMainActivity
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showNoInternetConnectionDialog() {
        Log.e("Testing net Connection", "Entering showNoInternetConnectionDialog Method")
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Whoops! Its seems you don't have internet connection, please try again later!")
            .setTitle("No Internet Connection")
            .setCancelable(false)
            .setNeutralButton("Retry") { dialog, id -> checkConnectionIsAvailable() }
        val alert = builder.create()
        alert.show()
        //  Log.e("Testing net Connection", "Showed NoIntenetConnectionDialog");
    }

    fun showDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.show()
            if (progressDialog!!.window != null) {
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            progressDialog!!.setContentView(R.layout.progress_dialog)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
        } else {
            progressDialog!!.show()
        }
    }

    fun hideDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }
}