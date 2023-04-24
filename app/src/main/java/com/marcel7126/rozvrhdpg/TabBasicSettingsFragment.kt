package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import com.marcel7126.rozvrhdpg.databinding.FragmentTabBasicSettingsBinding

class TabBasicSettingsFragment: Fragment(R.layout.fragment_tab_basic_settings) {

    private lateinit var binding: FragmentTabBasicSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        val tridy = resources.getStringArray(R.array.tridy)

        val arrayAdapterTrida = ArrayAdapter(requireContext(), R.layout.item_dropdown_trida, tridy)
        binding.ddtvTrida.setAdapter(arrayAdapterTrida)

        val profiles = arrayOf("Student", "Profesor")

        val arrayAdapterProfile = ArrayAdapter(requireContext(), R.layout.item_dropdown_profile, profiles)
        binding.ddtvProfile.setAdapter(arrayAdapterProfile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabBasicSettingsBinding.bind(view)


        val sharedPref = this.requireActivity().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()









        val tridy = arrayOf(
            "pr",
            "sk",
            "te",
            "kr",
            "kn",
            "sx",
            "sp",
            "ok",
            "i",
            "ii",
            "iii",
            "iv"
        )

        val profile = arrayOf(
            "student",
            "teacher",
        )

        binding.ddtvProfile.setText( UtilityOther.classrooms[sharedPref.getString("profile", "Profil")]?.get(0).toString() )

        binding.ddtvTrida.setOnItemClickListener { adapterView, view, i, l ->
            editor.putString("profile", profile[i])
            editor.apply()
        }

        binding.ddtvTrida.setText( UtilityOther.classrooms[sharedPref.getString("trida", "Třída")]?.get(0).toString() )

        binding.ddtvTrida.setOnItemClickListener { adapterView, view, i, l ->
            //binding.textView.text = adapterView.getItemAtPosition(i).toString()
            editor.putString("trida", tridy[i])
            editor.apply()
            UtilityOther.nacteno = false
        }

        binding.ddtvSubjects.setOnClickListener {
            showDialog()
        }




        val count = sharedPref.getInt("SubjectCount", 0)
        if (count > 0)
            binding.ddtvSubjects.setText(count.toString() + " Předmětů")


    }

    private fun showDialog() {
    }

}