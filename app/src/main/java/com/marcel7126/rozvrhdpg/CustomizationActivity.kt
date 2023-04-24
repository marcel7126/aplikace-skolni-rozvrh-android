package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.marcel7126.rozvrhdpg.databinding.ActivityCustomizationBinding
import com.marcel7126.rozvrhdpg.databinding.ActivityMainBinding
import com.marcel7126.rozvrhdpg.databinding.ActivitySubjectSelectionBinding
import com.marcel7126.rozvrhdpg.databinding.FragmentTimetableBinding

open class CustomizationActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCustomizationBinding

    private lateinit var toggles: Array<com.marcel7126.rozvrhdpg.CheckableButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPrefs()
        onClicks()

    }

    override fun onResume() {
        super.onResume()


    }

    private fun onClicks() {
        binding.btnAlwaysShowClassrooms.setOnClickListener {
            binding.btnHideClassrooms.isChecked = false
        }
        binding.btnHideClassrooms.setOnClickListener {
            binding.btnAlwaysShowClassrooms.isChecked = false
        }
    }

    private fun loadPrefs() {
        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        toggles = arrayOf(
            binding.btnAlwaysShowClassrooms,
            binding.btnHideClassrooms,
            binding.btnHideDash,
            binding.btnHideTime,
            binding.btnShowClassNumber,
            binding.btnShowDays
        )
        toggles.forEach { button ->
            if (prefs.getBoolean(button.tag.toString(), false)) {
                button.isChecked = true
            }
        }
    }

    override fun onStop() {

        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val editor = prefs.edit()
        var count = 0

        toggles.forEach { button ->
            if (button.isChecked) {
                editor.putBoolean(button.tag.toString(), true)
                count++
            }
            else
                editor.putBoolean(button.tag.toString(), false)
        }
        editor.apply()

        super.onStop()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);

    }

}