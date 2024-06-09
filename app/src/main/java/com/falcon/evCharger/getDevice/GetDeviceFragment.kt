package com.falcon.evCharger.getDevice

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.adapters.VehicleListAdapter
import com.falcon.evCharger.getDevice.viewModel.GetDeviceFragmentViewModel
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evCharger.response.GetVehicleListResponse
import com.falcon.evCharger.response.UserList
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.GetDeviceFragmentBinding
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GetDeviceFragment : HomeBaseFragment() {

    private lateinit var getDeviceBinding: GetDeviceFragmentBinding

    private val getDeviceFragmentViewModel: GetDeviceFragmentViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()
    private var mobileNo: String? = null

    private var vehicleList : List<UserList>?=null
    private var selectedVehicle : String?=null
    private var vehicleListResponse: GetVehicleListResponse? = null
    private val vehicleListAdapter by lazy {
        VehicleListAdapter(requireContext(), mutableListOf())
    }


    //Class to Handle all the button click
    enum class ViewOnClick {
        GET_VEHICLES, SIGN_UP, SCAN_QR_BARCODE,
    }

    enum class UpdateEvent {
        SUCCESS, FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        getDeviceBinding = GetDeviceFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { getDeviceFragmentViewModel.init(it) }

        getDeviceBinding.viewModel = getDeviceFragmentViewModel
        getDeviceBinding.tvAvailableBalance.text = sharePrefRepo.balance.toString()

        return getDeviceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response: GetDeviceResponse? = arguments?.getParcelable(Constants.DEVICE_RESPONSE)

        if (response != null) {
            updateViewComponents(response)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewComponents(response: GetDeviceResponse) {
        sharePrefRepo.balance = 199
        getDeviceBinding.tvAvailableBalance.text = "₹ ${sharePrefRepo.balance}"

        val textColor = context?.let { Constants.getColor(sharePrefRepo.balance, it) }
        if (textColor != null) {
            getDeviceBinding.tvAvailableBalance.setTextColor(textColor)
        }

        getDeviceBinding.tvChargerName.text = response.User_Details.Device_Name
        getDeviceBinding.tvMaxPower.text = response.User_Details.Max_Power.toString() +" V"
        getDeviceBinding.tvPerUnitCharges.text =  getString(R.string.charges_per_unit, response.User_Details.ChargesPerUnit.toString())

        showDialog()
        mobileNo = sharePrefRepo.getString(Constants.Phone_Number)

        getVehicleList(mobileNo);
    }

    private fun getVehicleList(mobileNo: String?) {
        var gtVehicleListRequest: GetVehicleListRequest = GetVehicleListRequest()
        gtVehicleListRequest.Phone_Number = mobileNo

        getDeviceFragmentViewModel.getVehicleList(gtVehicleListRequest)
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
            ViewOnClick.GET_VEHICLES -> {
                Log.e("get_vehicles_log", ":clicked  GET_VEHICLES:")
                if (vehicleList?.isEmpty() == true) {
                    showDialog()
                    getVehicleList(mobileNo)
                } else {
                    showVehiclesDialog()
                }
            }
            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(vehicleListResponse: GetVehicleListResponse) {
        Log.e("get_vehicle_list_log", ":" + Gson().toJson(vehicleListResponse))

        val data = vehicleListResponse.User_List
        val items = data.map { it.Vehicle_No }.toTypedArray()
        Log.e("vehicle_data_log",""+items)

        hideDialog()

        vehicleListResponse.User_List.let { it ->
            vehicleList = it
        }

        if (vehicleList?.isEmpty() == true) {
            showVehiclesDialog()
        } else {
            vehicleListResponse.User_List?.forEachIndexed { position, vehicleData ->

                if (mobileNo == vehicleData.Phone_Number) {
                    vehicleList!![position].selected = true
                    getDeviceBinding.tvSelectVehicle.text = vehicleData.Vehicle_No
                } else {
                    vehicleList!![position].selected = false
                }
            }
        }
    }

    private fun showVehiclesDialog() {
        Log.e("showVehiclesDialog", ":" + vehicleList?.size)

        val vehicleDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        vehicleDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = vehicleDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = vehicleDialog.findViewById<TextView>(R.id.tv_cancel)
        val statesRecyclerView = vehicleDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = vehicleDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_vehicle)

        var tempSelectedStatesData: UserList? = null

        vehicleListResponse?.User_List?.forEachIndexed { position, vehicleData ->

            if (mobileNo == vehicleData.Phone_Number) {
                vehicleList!![position].selected = true
                getDeviceBinding.tvSelectVehicle.text = vehicleData.Vehicle_No
            } else {
                vehicleList!![position].selected = false
            }
        }

        statesRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        statesRecyclerView.adapter = vehicleListAdapter

        vehicleList?.let { vehicleListAdapter.setData(it) }

        vehicleListAdapter.setClickListener(object : VehicleListAdapter.CareAlertListener {

            override fun onItemClick(vehicleData: UserList?, isChecked: Boolean) {

                Log.e("SelectedVehicleLog", ":" + Gson().toJson(vehicleData))

                tempSelectedStatesData = vehicleData

                Log.e(
                    "SelectedVehicleLog", "  : =>  :  " + Gson().toJson(tempSelectedStatesData)
                )

                vehicleListResponse?.User_List?.forEachIndexed { position, vehicleData ->

                    if (mobileNo == vehicleData.Phone_Number) {
                        vehicleList!![position].selected = true
                        selectedVehicle = vehicleData.Vehicle_No

                    } else {
                        vehicleList!![position].selected = false
                    }
                }
                vehicleList?.let { vehicleListAdapter.setData(it) }
                getDeviceBinding.tvSelectVehicle.text = vehicleData?.Vehicle_No
                vehicleListAdapter.notifyDataSetChanged()
            }
        })

        cancel.setOnClickListener {

            vehicleDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            selectedVehicle = tempSelectedStatesData?.Vehicle_No

            Log.e("SelectedStatesLogOnYes", ":" + Gson().toJson(selectedVehicle))

            vehicleDialog.dismiss()

            getDeviceBinding.tvSelectVehicle.text = selectedVehicle
        }

        vehicleDialog.show()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            GetDeviceFragment.UpdateEvent.FAILED -> {
            }

            else -> {

            }
        }
    }
}