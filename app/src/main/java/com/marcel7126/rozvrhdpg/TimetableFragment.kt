package com.marcel7126.rozvrhdpg

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marcel7126.rozvrhdpg.databinding.FragmentTimetableBinding
import java.io.File


open class TimetableFragment: Fragment(R.layout.fragment_timetable) {

    private lateinit var binding: FragmentTimetableBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimetableBinding.bind(view)


        //enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        //exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)

        /*binding.swipeRefresh.setProgressBackgroundColorSchemeResource(R.color.refreshBackground)
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }*/


        initialize()

    }

    // ON CREATE
    private fun initialize() {
        val sharedPref = requireActivity().getSharedPreferences(requireContext().packageName, AppCompatActivity.MODE_PRIVATE)
        UtilityOther.year = sharedPref.getString("trida", "ok")!!
        if (sharedPref.getBoolean("ShowDays", false))
            binding.groupDays.visibility = View.GONE
        else {
            UtilityFun.fadeIn(requireContext(), binding.tvDayMon)
            UtilityFun.fadeIn(requireContext(), binding.tvDayTue)
            UtilityFun.fadeIn(requireContext(), binding.tvDayWed)
            UtilityFun.fadeIn(requireContext(), binding.tvDayThu)
            UtilityFun.fadeIn(requireContext(), binding.tvDayFri)
        }
        // TODO +update prefs +load data
        //Updates.nacistData(requireContext())
        if (!UtilityOther.celyTydenNacteno) {
            if (!File(requireContext().filesDir, "celyTyden.csv").isFile)
                TimeTableUpdates.processDataFromSuplo(requireContext())
            else
                TimeTableUpdates.processDataFromCelyTyden(requireContext(), requireActivity())
        } else {
            UtilityFun.rewriteTimetableButtons(requireContext(), requireActivity())
        }
    }

    // TODO: remove dependency for implementation "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02" or implementation 'com.android.support.constraint:constraint-layout:1.1.2' in gradle files ??
}