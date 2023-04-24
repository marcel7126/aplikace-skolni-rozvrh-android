package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.marcel7126.rozvrhdpg.databinding.ActivityClassSelectionBinding

open class ClassSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassSelectionBinding

    private lateinit var tridy: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPrefs()

    }

    override fun onResume() {
        super.onResume()


    }

    private fun loadPrefs() {
        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        if (prefs.getString("Class", "") != "")
            binding.root.findViewWithTag<Button>(UtilityOther.rocniky_short[prefs.getString("Class", "").toString()]).setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_done_24,0)

        tridy = arrayOf(
            binding.btnPrima,
            binding.btnSekunda,
            binding.btnTercie,
            binding.btnKvarta,
            binding.btnKvinta,
            binding.btnSexta,
            binding.btnSeptima,
            binding.btnOktava,
            binding.btnIR,
            binding.btnIIR,
            binding.btnIIIR,
            binding.btnIVR
        )
        tridy.forEach { button ->
            button.setOnClickListener {
                val editor = prefs.edit()
                editor.putString("Class", button.tag.toString().lowercase())
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