package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.marcel7126.rozvrhdpg.databinding.ActivityMainBinding
import com.marcel7126.rozvrhdpg.databinding.ActivitySubjectSelectionBinding
import com.marcel7126.rozvrhdpg.databinding.FragmentTimetableBinding

open class SubjectSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectSelectionBinding

    private lateinit var predmety: Array<com.marcel7126.rozvrhdpg.CheckableButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPrefs()
        onClicks()

    }

    override fun onResume() {
        super.onResume()


    }

    private fun onClicks() {
        // SKUPINA
        binding.btnSkupinaA.setOnClickListener {
            binding.btnSkupinaB.isChecked = false
        }
        binding.btnSkupinaB.setOnClickListener {
            binding.btnSkupinaA.isChecked = false
        }
        // TELOCVIK
        binding.btnTvChlapci.setOnClickListener {
            binding.btnTvDivky.isChecked = false
        }
        binding.btnTvDivky.setOnClickListener {
            binding.btnTvChlapci.isChecked = false
        }
        // JAZYKY
        binding.btnNemcina.setOnClickListener {
            binding.btnSpanelstina.isChecked = false
            binding.btnFrancouzstina.isChecked = false
        }
        binding.btnSpanelstina.setOnClickListener {
            binding.btnNemcina.isChecked = false
            binding.btnFrancouzstina.isChecked = false
        }
        binding.btnFrancouzstina.setOnClickListener {
            binding.btnNemcina.isChecked = false
            binding.btnSpanelstina.isChecked = false
        }
    }

    private fun loadPrefs() {
        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)



        predmety = arrayOf(
            binding.btnSkupinaA,
            binding.btnSkupinaB,
            binding.btnTvChlapci,
            binding.btnTvDivky,
            binding.btnNemcina,
            binding.btnSpanelstina,
            binding.btnFrancouzstina,
            binding.btnDejepis,
            binding.btnHistorickySeminar,
            binding.btnFilozofie,
            binding.btnAntropologickySeminar,
            binding.btnCeskyJazyk,
            binding.btnCeskyJazykKomunikace,
            binding.btnCtenarskaGramotnost,
            binding.btnLiteratura,
            binding.btnLiterarniSeminar,
            binding.btnBiologie,
            binding.btnBiologieRozsirena,
            binding.btnBiologieZakladni,
            binding.btnEvolucniBiologie,
            binding.btnChemie,
            binding.btnBiochemie,
            binding.btnFyzika,
            binding.btnGeologie,
            binding.btnEkologie,
            binding.btnEtologie,
            binding.btnAplikovanaFyzika,
            binding.btnMladiDebrujari,
            binding.btnMaterialyMereni,
            binding.btnLTBiologie,
            binding.btnLTChemie,
            binding.btnMatematika,
            binding.btnMatematikaRozsirena,
            binding.btnDeskriptivniGeometrie,
            binding.btnAnglickyJazyk,
            binding.btnAnglickyJazykLiteratura,
            binding.btnAnglickyJazykRealie,
            binding.btnAnglickaKonverzace,
            binding.btnFCE,
            binding.btnNemeckyJazykNepovinny,
            binding.btnNemeckaKonverzace,
            binding.btnSpanelskyJazykNepovinny,
            binding.btnSpanelskaKonverzace,
            binding.btnFrancouzskyJazykNepovinny,
            binding.btnLatina,
            binding.btnDramatickaVychova,
            binding.btnHudebniVychova,
            binding.btnObcanskaVychova,
            binding.btnTelesnaVychova,
            binding.btnVytvarnaVychova,
            binding.btnVychovaKeZdravi,
            binding.btnInformatika,
            binding.btnDigitalniTechnika,
            binding.btnProgramovani,
            binding.btnZemepis,
            binding.btnAntropologickySeminar0,
            binding.btnSpolecenskovedniSeminar,
            binding.btnVytvarnySeminar,
            binding.btnKek,
            binding.btnVytvarnaEstetika,
            binding.btnDebatniKlub,
            binding.btnStopa,
            binding.btnSkolniPsycholog,
            binding.btnEkonomie,
            binding.btnEvropskeSouvislosti,
            binding.btnPravoPolitologie,
            binding.btnPraktickaEkonomie,
            binding.btnPsychologie,
            binding.btnRetorika
        )
        predmety.forEach { button ->
            if (prefs.getBoolean(button.tag.toString(), false)) {
                button.isChecked = true
            }
        }

/*
        predmety.forEach { button ->
            button.setOnClickListener { layout ->
            binding.btnSkupinaC.let { it.isChecked = !it.isChecked }
            binding.tvTitle.text = binding.btnSkupinaC.isChecked.toString()
            layout.findViewWithTag<ImageView>("drawable_toggle_icon").isEnabled = binding.btnSkupinaC.isChecked
        }
        }
*/

    }

    override fun onStop() {
        val prefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        if (true) {
            val editor = prefs.edit()
            var count = 0

            predmety.forEach { button ->
                if (button.isChecked) {
                    editor.putBoolean(button.tag.toString(), true)
                    count++
                }
                else
                    editor.putBoolean(button.tag.toString(), false)
                //button.isChecked = false
            }

            editor.putInt("SubjectCount", count)
            editor.apply()
        }
        super.onStop()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);

    }

}