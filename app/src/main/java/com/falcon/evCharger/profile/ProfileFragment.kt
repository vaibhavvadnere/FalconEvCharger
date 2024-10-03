package com.falcon.evCharger.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.Constants
import com.falcon.evCharger.EVMainActivity
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.menu.viewModel.MenuViewModel
import com.falcon.evCharger.profile.viewModel.ProfileViewModel
import com.falcon.evCharger.response.UserDetailsResponse
import com.falcon.evCharger.util.DTU
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.MenuFragmentBinding
import com.falcon.evcharger.databinding.ProfileFragmentBinding
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import com.iSay1.roamstick.data.model.request.GetUserDataRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProfileFragment : HomeBaseFragment() , EVMainActivity.onBackPressListener  {

    private lateinit var profileFragmentBinding: ProfileFragmentBinding

    private val profileViewModel: ProfileViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        EDIT, DOB_CLICKED, UPDATE
    }

    var isEditing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { profileViewModel.init(it) }

//        mActivity?.registerOnBackPress { this }

        mActivity?.registerOnBackPress(this)

        profileFragmentBinding.viewModel = profileViewModel
        profileFragmentBinding.lifecycleOwner = this

        return profileFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()

        Log.e("onResumeProfileFrag", " : called : ")

        showDialog()

        val getUserDataRequest: GetUserDataRequest = GetUserDataRequest()
        getUserDataRequest.PhoneNumber = SharePrefRepo.getInstance().getString(Constants.Phone_Number)

        profileViewModel.getUserDetails(getUserDataRequest)
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
            ViewOnClick.EDIT -> {
                Log.e("onEditClick", ": clicked  EDIT : ")

                enableEditProfile()

            }

            ViewOnClick.DOB_CLICKED -> {
                Log.e("onDOBClick", ": clicked  DOB_CLICKED :")

                if (isEditing) {
                    DTU.showDatePickerDialog(mActivity, DTU.FLAG_OLD_AND_NEW, profileFragmentBinding.edtDateOfBirth)
                }
            }

            ViewOnClick.UPDATE -> {
                Log.e("onUpdateClick", ":clicked  UPDATE:")

            }

            else -> {

            }
        }
    }

    private fun enableEditProfile() {

        isEditing = true

        profileFragmentBinding.edtFullName.isFocusable = true
        profileFragmentBinding.edtFullName.isFocusableInTouchMode = true

        profileFragmentBinding.edtFullName.requestFocus()

        profileFragmentBinding.edtEmail.isFocusable = true
        profileFragmentBinding.edtEmail.isFocusableInTouchMode = true

        profileFragmentBinding.edtAddress.isFocusable = true
        profileFragmentBinding.edtAddress.isFocusableInTouchMode = true

        profileFragmentBinding.edtDateOfBirth.isClickable = true

        profileFragmentBinding.btnEdit.isVisible = false

        profileFragmentBinding.btnUpdate.isVisible = true

    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(userDetailsResponse: UserDetailsResponse) {

        hideDialog()

        profileFragmentBinding.edtFullName.setText(userDetailsResponse.User.User_Name)
        profileFragmentBinding.edtEmail.setText(userDetailsResponse.User.Email_ID)
        profileFragmentBinding.edtDateOfBirth.setText(userDetailsResponse.User.DOB)
        profileFragmentBinding.edtAddress.setText(userDetailsResponse.User.Address)

    }

    override fun onBackPress() {
        Log.e("ProfileFragLog", ":Backpressed:")

        mActivity?.navController?.navigate(R.id.back_to_dashboard_from_profile)

    }

}

