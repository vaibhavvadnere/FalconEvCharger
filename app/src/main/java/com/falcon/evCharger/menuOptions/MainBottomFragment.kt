package com.falcon.evCharger.menuOptions

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.falcon.evCharger.Constants
import com.falcon.evCharger.base.HomeBaseFragment
import com.falcon.evCharger.dashboard.DashboardFragment
import com.falcon.evCharger.data.repositry.SharePrefRepo
import com.falcon.evCharger.history.HistoryFragment
import com.falcon.evCharger.myAccount.AccountSetupFragment
import com.falcon.evCharger.menu.MenuFragment
import com.falcon.evcharger.R
import com.falcon.evcharger.databinding.FragmentMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainBottomFragment : HomeBaseFragment() {
    var navController: NavController? = null
    private val isCallingSelected = false
    var ignore_biometric = false
    private val sharePrefRepo = SharePrefRepo.getInstance()

    private lateinit var fragmentMenuBinding: FragmentMenuBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity!!.registerOnBackPress(null)
        val bundle = arguments
        if (bundle != null) {
            ignore_biometric = bundle.getBoolean("ignore_biometric")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater)

        //        fragment = new CallFragment();
        mActivity!!.unRegisterOnBackPress()
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        fragmentMenuBinding.pager.adapter = pagerAdapter
        fragmentMenuBinding.pager.isUserInputEnabled = false
        fragmentMenuBinding.pager.offscreenPageLimit = 1
        bottomNavigation()


        fragmentMenuBinding.vehicleFab.setOnClickListener {
            val menu: Menu = fragmentMenuBinding.bottomNavigation.menu
            val menuitem = menu.getItem(1)
            menuitem.isChecked = false

            fragmentMenuBinding.vehicleFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green_900))
            fragmentMenuBinding.pager.setCurrentItem(1, false)
        }

        /*fragmentMenuBinding.callFab.setOnClickListener(v -> {

            Menu menu = fragmentMenuBinding.bottomNavigation.getMenu();
            MenuItem menuitem = menu.getItem(2);
            menuitem.setChecked(false);

        });*/


        return fragmentMenuBinding!!.root
    }

    private fun bottomNavigation() {
        val menu = fragmentMenuBinding!!.bottomNavigation.menu
        val menuitem = menu.getItem(0)
        menuitem.isChecked = true

        fragmentMenuBinding.bottomNavigation.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.navigation_dashboard -> {

//                            fragmentMenuBinding.vehicleFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.bottom_dark))
                            fragmentMenuBinding.pager.setCurrentItem(0, false)
                        }

                        R.id.navigation_you -> {
//                            fragmentMenuBinding.vehicleFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green_900))
                            fragmentMenuBinding.pager.setCurrentItem(1, false)
                        }

                        R.id.navigation_menu -> {
//                            fragmentMenuBinding.vehicleFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green_900))
                            fragmentMenuBinding.pager.setCurrentItem(2, false)
                        }

                        R.id.navigation_history -> {
//                            fragmentMenuBinding.vehicleFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.bottom_dark))
                            fragmentMenuBinding.pager.setCurrentItem(3, false)
                        }

                    }
                    true
                })
    }

    override fun connectionAvailable() {}
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return DashboardFragment()
                1 -> return AccountSetupFragment()
                2 -> return MenuFragment()
                3 -> return HistoryFragment()
            }
            return DashboardFragment()
        }

        override fun getItemCount(): Int {
            return Constants.TOTAL_SCREEN
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}