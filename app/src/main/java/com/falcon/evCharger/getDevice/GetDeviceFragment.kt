package com.falcon.evCharger.getDevice

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.getDevice.adapters.UnitsListAdapter
import com.falcon.evCharger.getDevice.adapters.VehicleListAdapter
import com.falcon.evCharger.getDevice.viewModel.GetDeviceFragmentViewModel
import com.falcon.evCharger.response.GetDeviceResponse
import com.falcon.evCharger.response.GetStartChargingResponse
import com.falcon.evCharger.response.GetVehicleListResponse
import com.falcon.evCharger.response.UnitsData
import com.falcon.evCharger.response.UserList
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.GetDeviceFragmentBinding
import com.google.gson.Gson
import com.iSay1.roamstick.data.model.request.GetStartChargingRequest
import com.iSay1.roamstick.data.model.request.GetVehicleListRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GetDeviceFragment : HomeBaseFragment() {

    private lateinit var getDeviceBinding: GetDeviceFragmentBinding

    private val getDeviceFragmentViewModel: GetDeviceFragmentViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()
    private var mobileNo: String? = null

    private var vehicleList: List<UserList>? = null
    private var selectedVehicle: UserList? = null
    private var selectedUnit: UnitsData? = null

    private var vehicleListResponse: GetVehicleListResponse? = null

    private val vehicleListAdapter by lazy {
        VehicleListAdapter(requireContext(), mutableListOf())
    }
    private val unitsListAdapter by lazy {
        UnitsListAdapter(requireContext(), mutableListOf())
    }

    //Class to Handle all the button click
    enum class ViewOnClick {
        GET_VEHICLES, SIGN_UP, SELECT_UNITS, START_CHARGING,
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
        Log.e("responseLogs", " : " + response)

        sharePrefRepo.balance = 199
        getDeviceBinding.tvAvailableBalance.text = "₹ ${sharePrefRepo.balance}"

        getDeviceBinding.tvSocietyName.text = response.User_Details.Society_Name ?: "NA"


        val textColor = context?.let { Constants.getColor(sharePrefRepo.balance, it) }
        if (textColor != null) {
            getDeviceBinding.tvAvailableBalance.setTextColor(textColor)
        }

        getDeviceBinding.tvChargerName.text = response.User_Details.Device_Name + " - ${response.User_Details.Device_ID ?: "NA"}"
        getDeviceBinding.tvMaxPower.text = "3.3 KW - " + response.User_Details.Max_Power.toString() + " V"
        getDeviceBinding.tvChargerId.text = response.User_Details.Device_ID.toString()
        getDeviceBinding.tvPerUnitCharges.text = getString(R.string.charges_per_unit, response.User_Details.ChargesPerUnit.toString())

        showDialog()
        mobileNo = sharePrefRepo.getString(Constants.Phone_Number)

        getVehicleList(mobileNo);
    }

    private fun getVehicleList(mobileNo: String?) {
        val gtVehicleListRequest: GetVehicleListRequest = GetVehicleListRequest()
        gtVehicleListRequest.Phone_Number = mobileNo

        getDeviceFragmentViewModel.getVehicleList(gtVehicleListRequest)
    }

    private fun getStartCharging(
        deviceId: String?,
        selectedVehicle: UserList?,
        selectedUnit: UnitsData?
    ) {
        val getStartChargingRequest: GetStartChargingRequest = GetStartChargingRequest()
        getStartChargingRequest.Device_ID = deviceId
        getStartChargingRequest.Vehicle_No = selectedVehicle?.Vehicle_No
        getStartChargingRequest.Unit = selectedUnit?.unitName
        Log.e(
            "onclick_start_charging_log", "getStartCharging -> device id -> " + SharePrefRepo.getInstance().deviceId +
                    "Vehicle_No -> " + selectedVehicle + " -> Unit " + selectedUnit
        )

        getDeviceFragmentViewModel.getStartCharging(getStartChargingRequest)
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

            ViewOnClick.SELECT_UNITS -> {
                Log.e("get_vehicles_log", ":clicked  GET_VEHICLES:")


                if (selectedVehicle == null) {
                    Toast.makeText(mActivity, "Please select vehicle first", Toast.LENGTH_SHORT).show()
                } else {
                    showUnitsDialog()
                }/*if (vehicleList?.isEmpty() == true) {
                    showDialog()
                    getVehicleList(mobileNo)
                } else {
                    showVehiclesDialog()
                }*/
            }

            ViewOnClick.START_CHARGING -> {
                Log.e("onclick_start_charging_log", ":clicked  START_CHARGING:")

                if (selectedVehicle != null && selectedUnit != null) {

                    if (validate()) {
                        showDialog()
                        getStartCharging(SharePrefRepo.getInstance().deviceId, selectedVehicle, selectedUnit)
                    }
                }
            }

            else -> {

            }
        }
    }

    private fun validate(): Boolean {

        if (selectedVehicle == null) {
            Toast.makeText(mActivity, "Please select the vehicle", Toast.LENGTH_SHORT).show()
            return false
        } else if (selectedUnit == null) {
            Toast.makeText(mActivity, "Please select the unit", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(response: getDeviceResponses) {
        when (response) {
            is getDeviceResponses.VehicleListResponse -> handleVehicleListResponse(response.vehicleListResponse)
            is getDeviceResponses.StartChargingResponse -> handleStartChargingResponse(response.startChargingResponse)
            else -> {}
        }
    }

    private fun handleStartChargingResponse(response: GetStartChargingResponse?) {
        hideDialog()
        Log.e("getVehicleListLog", " : log : charging started successfully ")

        if (response?.Result == true) {
            val bundle = Bundle()
            bundle.putString(Constants.VEHICLE_NO, selectedVehicle?.Vehicle_No)
            bundle.putString(Constants.VEHICLE_TYPE, selectedVehicle?.Vehicle_Type)
            bundle.putString(Constants.DEVICE_ID, SharePrefRepo.getInstance().deviceId)
            mActivity?.navController?.navigate(R.id.action_success_in, bundle)
        }
    }

    private fun handleVehicleListResponse(vehicleListResponse: GetVehicleListResponse?) {
        Log.e("getVehicleListLog", " : log vehile: " + Gson().toJson(vehicleListResponse))

        val data = vehicleListResponse?.User_List
        val items = data?.map { it.Vehicle_No }?.toTypedArray()
        Log.e("vehicle_data_log", "" + items)

        hideDialog()

        vehicleListResponse?.User_List.let { it ->
            vehicleList = it
        }

        if (vehicleList?.isEmpty() == true) {
            showVehiclesDialog()
        } else {
            vehicleList?.forEachIndexed { position, vehicleData ->

                if (selectedVehicle != null && selectedVehicle?.Vehicle_No == vehicleData.Vehicle_No) {
                    vehicleList!![position].selected = true
                    getDeviceBinding.tvSelectVehicle.text = vehicleData.Vehicle_No + " - ${selectedVehicle?.Vehicle_Type ?: ""}"
                } else {
                    vehicleList!![position].selected = false
                }
            }
        }
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(vehicleListResponse: GetVehicleListResponse) {
        Log.e("getVehicleListLog", " : log : " + Gson().toJson(vehicleListResponse))

        val data = vehicleListResponse.User_List
        val items = data.map { it.Vehicle_No }.toTypedArray()
        Log.e("vehicle_data_log", "" + items)

        hideDialog()

        vehicleListResponse.User_List.let { it ->
            vehicleList = it
        }

        if (vehicleList?.isEmpty() == true) {
            showVehiclesDialog()
        } else {
            vehicleList?.forEachIndexed { position, vehicleData ->

                if (selectedVehicle != null && selectedVehicle?.Vehicle_No == vehicleData.Vehicle_No) {
                    vehicleList!![position].selected = true
                    getDeviceBinding.tvSelectVehicle.text = vehicleData.Vehicle_No
                } else {
                    vehicleList!![position].selected = false
                }
            }
        }
    }*/

    @SuppressLint("SetTextI18n")
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

        vehicleList?.forEachIndexed { position, vehicleData ->

            if (selectedVehicle?.Vehicle_No == vehicleData.Vehicle_No) {
                vehicleList!![position].selected = true
            } else {
                vehicleList!![position].selected = false
            }
        }

        statesRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        statesRecyclerView.adapter = vehicleListAdapter

        vehicleList?.let { vehicleListAdapter.setData(it) }

        vehicleListAdapter.setClickListener(object : VehicleListAdapter.CareAlertListener {

            override fun onItemClick(vehicleData: UserList?) {

                Log.e("SelectedVehicleLog", ":" + Gson().toJson(vehicleData))

                tempSelectedStatesData = vehicleData

                Log.e(
                    "SelectedVehicleLog", "  : =>  :  " + Gson().toJson(tempSelectedStatesData)
                )

                vehicleList?.forEachIndexed { position, vehicleData ->

                    if (tempSelectedStatesData?.Vehicle_No == vehicleData.Vehicle_No) {
                        vehicleList!![position].selected = true
                    } else {
                        vehicleList!![position].selected = false
                    }
                }

                vehicleList?.let { vehicleListAdapter.setData(it) }

                vehicleListAdapter.notifyDataSetChanged()
            }
        })

        cancel.setOnClickListener {

            vehicleDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            selectedVehicle = tempSelectedStatesData

            Log.e("SelectedVehicleLogOnYes", " : " + Gson().toJson(selectedVehicle))

            vehicleDialog.dismiss()

            if (selectedVehicle != null) getDeviceBinding.tvSelectVehicle.text = selectedVehicle?.Vehicle_No + " - ${selectedVehicle?.Vehicle_Type ?: ""}"
        }

        vehicleDialog.show()
    }

    private fun showUnitsDialog() {

        val unitsList: ArrayList<UnitsData> = ArrayList()
        if (selectedVehicle?.Vehicle_Type.equals("Two Wheeler")|| selectedVehicle?.Vehicle_Type.equals("Three Wheeler")) {
            unitsList.clear()

            val unitsData1: UnitsData = UnitsData()
            unitsData1.unitName = "1"
            val unitsData3: UnitsData = UnitsData()
            unitsData3.unitName = "3"
            val unitsData5: UnitsData = UnitsData()
            unitsData5.unitName = "5"
            val unitsDataFC: UnitsData = UnitsData()
            unitsDataFC.unitName = "Full Charge"
            unitsList.addAll(listOf(unitsData1, unitsData3, unitsData5, unitsDataFC))

        } else if (selectedVehicle?.Vehicle_Type.equals("Four Wheeler")) {
            unitsList.clear()

            val unitsData5: UnitsData = UnitsData()
            unitsData5.unitName = "5"
            val unitsData10: UnitsData = UnitsData()
            unitsData10.unitName = "10"
            val unitsData15: UnitsData = UnitsData()
            unitsData15.unitName = "15"
            val unitsData20: UnitsData = UnitsData()
            unitsData20.unitName = "20"
            val unitsData25: UnitsData = UnitsData()
            unitsData25.unitName = "25"
            val unitsData30: UnitsData = UnitsData()
            unitsData30.unitName = "30"
            val unitsDataFC: UnitsData = UnitsData()
            unitsDataFC.unitName = "Full Charge"
            unitsList.addAll(listOf(unitsData5, unitsData10, unitsData15, unitsData20, unitsData25, unitsData30, unitsDataFC))
        }


        Log.e("showUnitsDialog", ":" + unitsList?.size)

        val unitDialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        unitDialog.setContentView(R.layout.lay_select_option_dialog)
        val dialogButton = unitDialog.findViewById<View>(R.id.tv_done) as TextView
        val cancel = unitDialog.findViewById<TextView>(R.id.tv_cancel)
        val unitsRecyclerView = unitDialog.findViewById<RecyclerView>(R.id.rl_list)
        val tvHeader = unitDialog.findViewById<TextView>(R.id.tv_header)

        tvHeader.text = mActivity?.resources?.getString(R.string.select_unit)

        var tempSelectedUnitData: UnitsData? = null

        unitsList.forEachIndexed { position, unitData ->

            if (selectedUnit?.unitName == unitData.unitName) {
                unitsList[position].selected = true
            } else {
                unitsList[position].selected = false
            }
        }

        unitsRecyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        unitsRecyclerView.adapter = unitsListAdapter

        unitsList.let { unitsListAdapter.setData(it) }

        unitsListAdapter.setClickListener(object : UnitsListAdapter.ItemClickListener {

            override fun onItemClick(unitsData: UnitsData?) {

                Log.e("SelectedVehicleLog", ":" + Gson().toJson(unitsData))

                tempSelectedUnitData = unitsData

                Log.e(
                    "SelectedVehicleLog", "  : =>  :  " + Gson().toJson(tempSelectedUnitData)
                )

                unitsList.forEachIndexed { position, unitData ->

                    if (tempSelectedUnitData?.unitName == unitData.unitName) {
                        unitsList[position].selected = true
                    } else {
                        unitsList[position].selected = false
                    }
                }

                unitsList.let { unitsListAdapter.setData(it) }

                unitsListAdapter.notifyDataSetChanged()
            }
        })

        cancel.setOnClickListener {

            unitDialog.dismiss()
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            selectedUnit = tempSelectedUnitData

            Log.e("SelectedUnitLogOnYes", " : " + Gson().toJson(selectedUnit))

            unitDialog.dismiss()

            if (selectedUnit != null) getDeviceBinding.tvSelectUnit.text = selectedUnit?.unitName
        }

        unitDialog.show()
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