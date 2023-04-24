package com.marcel7126.rozvrhdpg

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marcel7126.rozvrhdpg.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        setOnClickListenersAndShit()

    }

    override fun onResume() {
        super.onResume()
        val prefs = this.requireActivity().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE)
        binding.tvClassEnd.text = UtilityOther.classrooms[prefs.getString("Class", "")]?.get(0).toString()

        //binding.btnProfileDescription.text = prefs.getString("Profile", "")//.toString().replaceFirstChar { it.uppercase() }
        binding.tvClassEnd.text = if (UtilityOther.classrooms[prefs.getString("Class", "")]?.get(0) != null)
            UtilityOther.classrooms[prefs.getString("Class", "")]?.get(0).toString()
        else
            ""
        binding.tvLocaleEnd.text = prefs.getString("Locale", "")
        binding.tvSubjectSelectionEnd.text = prefs.getInt("SubjectCount", 0).toString() + " Předmětů" // TODO: FIX

        val string = binding.tvClassEnd.text
    }

    private fun setOnClickListenersAndShit() {

        binding.btnChangelog.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_MaterialComponents_Dialog)
                .setTitle("Changelog v0.6 (+v0.5)")
                .setMessage(" + prepsany kod tabulky, nyni je tabulka pevne vytvorena a dynamicky se meni jen hodnoty - to zjednodusilo upravovani hodnot tabulky a nyni se muze aktualizovat kdykoliv" +
                        "\n + tabulka se aktualizuje pri vraceni do aktivity" +
                        "\n + pridan nacitaci radek pri stahovani dat z tabulek" +
                        "\n + dny jsou zarovnane" +
                        "\n + pridany casy hodin" +
                        "\n + cas hodin se zvyrazni podle probihajici hodiny" +
                        "\n + pridan sloupec pro 10 vyucovaci hodinu" +
                        "\n + predmet a vyucujici jsou oddeleni pomlckou" +
                        "\n + tabulka vykresluje jen ucebny mimo kmenovou/vychozi ucebnu" +
                        "\n + moznost skryt ucebny" +
                        "\n + moznost vzdy vykreslovat ubeny" +
                        "\n + moznost skryt pomlcku" +
                        "\n" +
                        "\nChangelog 0.4" +
                        "\n + pridany vsechny rocniky k vyberu rozvrhu" +
                        "\n + pridana moznost vytvoreni udalosti v kalendari z podrobnosti hodiny" +
                        "\n + pridany vsechny predmety do nastaveni a jsou ukladany pro zobrazovani urcitych hodin v rozvrhu" +
                        "\n + rozvrh vsech rocniku se nacita z google spreadsheets" +
                        "\n + aktualizovan splash art pri zapinani aplikace (dal jsem si zalezet(: ")
                .setPositiveButton("Dismiss") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnFeedback.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLSfSyVE-y8AICL5LIx2RozXrXC3D-mJv5chgcVuXsNpP8Y6quw/viewform"
            val uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        // SPANNABLE STRING
        /*
        val string = "I want THIS to be colored."
        val ss: SpannableString = SpannableString(string)
        val fcsRed: ForegroundColorSpan = ForegroundColorSpan(Color.RED)
        val bcsRed: BackgroundColorSpan = BackgroundColorSpan(Color.GREEN)

        val clickSpan0: ClickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = requireContext().getColor(R.color.colorPrimary)
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(fcsRed, 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(bcsRed, 15, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickSpan0, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitle.text = ss
        binding.tvTitle.highlightColor = Color.TRANSPARENT
        binding.tvTitle.movementMethod = LinkMovementMethod.getInstance()
         */



        val prefs = this.requireActivity().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE)
        val editor = prefs.edit()



        // NAVIGATION TO SUBJECT SELECTION
        binding.btnSubjectSelection.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_subjectSelectionActivity)
        }
        // NAVIGATION TO TIMETABLE CUSTOMIZATION
        binding.btnVisibilityPrefs.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_customizationActivity)
        }

        // LIST POPUP PROFILE
        //val listPopupWindow0 = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)
        //listPopupWindow0.anchorView = binding.btnProfile
        //val profiles = listOf(getString(R.string.student), getString(R.string.teacher))
        //// TODO: DON'T USE TRANSLATED STRING TO SAVE PREFERENCES
        //val adapter0 = ArrayAdapter(requireContext(), R.layout.item_dropdown_day, profiles)
        //listPopupWindow0.setAdapter(adapter0)
        //listPopupWindow0.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long -> // TODO: cleanup
        //    view as TextView
        //    binding.btnProfileDescription.text = profiles[position]
        //    listPopupWindow0.dismiss()
        //    editor.putString("Profile", view.text.toString())
        //    editor.apply()
        //}
        /*
        binding.btnProfile.setOnClickListener { view ->
            //listPopupWindow0.show()
            view.findNavController().navigate(R.id.action_settingsFragment_to_profileSelectionActivity)
        }
         */
        // LIST POPUP CLASS
        //val listPopupWindow1 = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)
        //listPopupWindow1.anchorView = binding.btnClass
        //val years = resources.getStringArray(R.array.tridy)
        //val adapter1 = ArrayAdapter(requireContext(), R.layout.item_dropdown_day, years)
        //listPopupWindow1.setAdapter(adapter1)
        //listPopupWindow1.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
        //    view as TextView
        //    binding.btnClassDescription.text = years[position]
        //    listPopupWindow1.dismiss()
        //    editor.putString("Class", Utility.classrooms[view.text.toString()]?.get(0).toString())
        //    editor.apply()
        //}
        binding.btnClass.setOnClickListener { view ->
            //listPopupWindow1.show()
            view.findNavController().navigate(R.id.action_settingsFragment_to_classSelectionActivity)
        }

        // LIST POPUP LOCALE
        val listPopupWindow2 = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)
        listPopupWindow2.anchorView = binding.btnLocale
        val locales = arrayOf("en", "cs", "device")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.item_dropdown_day, locales)
        listPopupWindow2.setAdapter(adapter2)
        listPopupWindow2.setOnItemClickListener { _, v: View, position, _ ->
            v as TextView
            val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
            val e = preferences.edit()
            binding.tvLocaleEnd.text = locales[position]
            listPopupWindow2.dismiss()
            e.putString("Locale", v.text.toString())
            e.apply()
        }
        binding.btnLocale.setOnClickListener {
            //listPopupWindow2.show()
            val localesShort = arrayOf("en", "cs", "device")
            val localesLong = arrayOf(resources.getString(R.string.locale_en), resources.getString(R.string.locale_cs), resources.getString(R.string.locale_device))
            context?.let { it1 ->
                val dialog = MaterialAlertDialogBuilder(it1)
                dialog
                    .setTitle(resources.getString(R.string.locale))
                    .setItems(localesLong) { dialog, which ->
                        // Respond to item chosen
                        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
                        val e = preferences.edit()
                        binding.tvLocaleEnd.text = localesLong[which]
                        listPopupWindow2.dismiss()
                        e.putString("Locale", localesShort[which])
                        e.apply()
                    }
                dialog.background = ResourcesCompat.getDrawable(it1.resources, R.drawable.settings_button_background, it1.theme)
                dialog.show()
            }
        }

    }

}