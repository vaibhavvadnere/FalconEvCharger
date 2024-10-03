package com.falcon.evCharger.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.history.adapters.TransactionsHistoryListAdapter
import com.falcon.evCharger.history.viewModel.HistoryViewModel
import com.falcon.evCharger.response.GetTransactionHistoryResponse
import com.falcon.evCharger.response.HistoryResponse
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.HistoryFragmentBinding
import com.iSay1.roamstick.data.model.request.GetTransactionHistoryRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HistoryFragment : HomeBaseFragment() {

    private lateinit var historyFragmentBinding: HistoryFragmentBinding

    private val historyViewModel: HistoryViewModel by activityViewModels()

    private val transactionsHistoryListAdapter by lazy {
        TransactionsHistoryListAdapter(requireContext(), mutableListOf())
    }

    //Class to Handle all the button click
    enum class ViewOnClick {
        SIGN_IN, SIGN_UP, SCAN_QR_BARCODE, LOGOUT_USER,
    }

    //Class to Handle all the button click
    enum class UpdateEvent {
        API_FAILED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            Log.e("isVisibleToUserLog", " : called : " + isVisibleToUser)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        historyFragmentBinding = HistoryFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { historyViewModel.init(it) }

        historyFragmentBinding.viewModel = historyViewModel

        return historyFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()

        Log.e("onResumeHistoryFrag", " : called : ")

        showDialog()

        var getTransactionHistoryRequest: GetTransactionHistoryRequest = GetTransactionHistoryRequest()

        getTransactionHistoryRequest.UserID = SharePrefRepo.getInstance().getString(Constants.USER_ID)

        historyViewModel.getTransactionHistory(getTransactionHistoryRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            ViewOnClick.SIGN_IN -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                mActivity?.navController?.navigate(R.id.action_sign_in)
            }

            ViewOnClick.SIGN_UP -> {
                mActivity?.navController?.navigate(R.id.action_sign_up)
            }

            ViewOnClick.LOGOUT_USER -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                SharePrefRepo.getInstance().clearSharePref()
                mActivity?.navController?.navigate(R.id.action_lets_in)
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(getTransactionHistoryResponse: GetTransactionHistoryResponse) {
        Log.e("getUserHistoryLogs", ":" + getTransactionHistoryResponse)
        hideDialog()
        setListAdapter(getTransactionHistoryResponse.Response)
    }

    private fun setListAdapter(historyList: List<HistoryResponse>) {
        historyFragmentBinding.rlTransactionList.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        historyFragmentBinding.rlTransactionList.adapter = transactionsHistoryListAdapter
        transactionsHistoryListAdapter.let { transactionsHistoryListAdapter.setData(historyList) }
    }

}

