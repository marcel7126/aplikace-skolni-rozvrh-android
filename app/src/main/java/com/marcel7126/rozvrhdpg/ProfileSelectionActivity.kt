package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marcel7126.rozvrhdpg.databinding.ActivityProfileSelectionBinding

open class ProfileSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSelectionBinding

    private lateinit var tridy: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPrefs()

    }

    private fun loadPrefs() {
        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        binding.root.findViewWithTag<Button>(prefs.getString("Profile", "")).setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_done_24,0)

        tridy = arrayOf(
            binding.btnStudent,
            binding.btnTeacher
        )
        tridy.forEach { button ->
            button.setOnClickListener {
                val editor = prefs.edit()
                editor.putString("Profile", button.text.toString())
                editor.apply()
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

}