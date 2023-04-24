package com.marcel7126.rozvrhdpg

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.provider.CalendarContract
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.marcel7126.rozvrhdpg.databinding.DialogDescriptionBinding
import com.marcel7126.rozvrhdpg.databinding.FragmentTimetableBinding
import com.marcel7126.rozvrhdpg.databinding.LayoutTimetableBinding
import com.marcel7126.rozvrhdpg.databinding.PrefabSubjectTileBinding
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


object UtilityFun {


    // region SORT


    private var num = 1
    private fun prevTileCheck(index: Int, tableArray: Array<PrefabSubjectTileBinding>) {
        if (
            index != 10 &&
            index != 21 &&
            index != 32 &&
            index != 43 &&
            index != 54 &&
            tableArray[index].tvTileSubject.text != "" &&
            tableArray[index].tvTileSubject.text == tableArray[index + 1].tvTileSubject.text &&
            tableArray[index].tvTileTeacher.text == tableArray[index + 1].tvTileTeacher.text &&
            tableArray[index].tvTileClassroom.text == tableArray[index + 1].tvTileClassroom.text &&
            tableArray[index].tvTileNote.text == tableArray[index + 1].tvTileNote.text
        ) {
            num++
            prevTileCheck(index + 1, tableArray)
        }
    }



    public fun rewriteTimetableButtons(context: Context, activity: Activity) {
        val binding = FragmentTimetableBinding.bind(activity.findViewById<ConstraintLayout>(R.id.clTimetableFragment))

        val table: LayoutTimetableBinding = binding.includeTable
        val prefs = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)

        val hoursToday = SimpleDateFormat("HH", Locale.GERMAN).format(Date())
        val minutesToday = SimpleDateFormat("mm", Locale.GERMAN).format(Date())
        var highlight: Int? = null
        for (index in 0..10) {
            if (hoursToday.toInt() * 60 + minutesToday.toInt() > (UtilityOther.zvoneni[index]!![2].toInt() * 60 + UtilityOther.zvoneni[index]!![3].toInt()))
                highlight = index
        }

        arrayOf(table.incTime0, table.incTime1, table.incTime2, table.incTime3, table.incTime4,
            table.incTime5, table.incTime6, table.incTime7, table.incTime8, table.incTime9, table.incTime10
        ).forEachIndexed { index, timeTileBinding ->

            if (highlight == index) {
                timeTileBinding.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_highlight, context.theme)
                timeTileBinding.tvDash.setTextColor(context.resources.getColor(R.color.white, context.theme))
                timeTileBinding.tvFrom.setTextColor(context.resources.getColor(R.color.white, context.theme))
                timeTileBinding.tvNumber.setTextColor(context.resources.getColor(R.color.white, context.theme))
                timeTileBinding.tvTo.setTextColor(context.resources.getColor(R.color.white, context.theme))
            }
            timeTileBinding.tvFrom.text = UtilityOther.zvoneni[index]?.get(0)
            timeTileBinding.tvTo.text = UtilityOther.zvoneni[index]?.get(1)
            timeTileBinding.tvNumber.text = index.toString()
            if (prefs.getBoolean("ShowClassNumber", false))
                timeTileBinding.tvNumber.visibility = View.VISIBLE
            else
                timeTileBinding.tvNumber.visibility = View.GONE
            if (prefs.getBoolean("HideTime", false))
                timeTileBinding.gTime.visibility = View.GONE
            else
                timeTileBinding.gTime.visibility = View.VISIBLE


        }

        year = prefs.getString("Class", "ok").toString()
        binding.tvTrida.text = UtilityOther.rocniky_short[year]

        val tableArray = arrayOf(table.incTile0, table.incTile1, table.incTile2, table.incTile3, table.incTile4, table.incTile5, table.incTile6,
            table.incTile7, table.incTile8, table.incTile9, table.incTile10, table.incTile11, table.incTile12, table.incTile13, table.incTile14,
            table.incTile15, table.incTile16, table.incTile17, table.incTile18, table.incTile19, table.incTile20, table.incTile21, table.incTile22,
            table.incTile23, table.incTile24, table.incTile25, table.incTile26, table.incTile27, table.incTile28, table.incTile29, table.incTile30,
            table.incTile31, table.incTile32, table.incTile33, table.incTile34, table.incTile35, table.incTile36, table.incTile37, table.incTile38,
            table.incTile39, table.incTile40, table.incTile41, table.incTile42, table.incTile43, table.incTile44, table.incTile45, table.incTile46,
            table.incTile47, table.incTile48, table.incTile49, table.incTile50, table.incTile51, table.incTile52, table.incTile53, table.incTile54
        )

        //// klasik rozvrh

        val posunTrida = when (year) {
            "sk" -> 5
            "te" -> 10
            "kr" -> 15
            "kn" -> 20
            "sx" -> 25
            "sp" -> 30
            "ok" -> 35
            "i" -> 40
            "ii" -> 45
            "iii" -> 50
            "iv" -> 55
            else -> 0
        }

        val rocnik = when (year) {
            "pr" -> 0
            "sk" -> 1
            "te" -> 2
            "kr" -> 3
            "kn" -> 4
            "sx" -> 5
            "sp" -> 6
            "ok" -> 7
            "i" -> 8
            "ii" -> 9
            "iii" -> 10
            "iv" -> 11
            else -> 0
        }

        val datanew = Array(12) { trida ->
            Array(5) { day ->
                Array(11) { hodina ->
                    Predmet(UtilityOther.celyTyden[day + trida*5][hodina][0], UtilityOther.celyTyden[day + trida*5][hodina][1], UtilityOther.celyTyden[day + trida*5][hodina][2], UtilityOther.celyTyden[day + trida*5][hodina][3])
                }
            }
        }


        tableArray.forEachIndexed { index, it ->

            // HELPER INT
            val day: Int = when (index) { // TODO: USE < 11 / < 22...       ....no use index % 11
                in 0..10 -> 0
                in 11..21 -> 1
                in 22..32 -> 2
                in 33..43 -> 3
                in 44..54 -> 4
                else -> 0
            }


            //if (UtilityOther.celyTyden[day+posunTrida][index-day*11][0] == "")
            if (false)
            {}
            else {

            // SET TILE ON CLICK LISTENER
            it.root.setOnClickListener { _ ->
                UtilityFun.tableButtonOnClick(context, activity, Hodina(index - day * 11,
                    it.tvTileSubject.text.toString(),
                    it.tvTileTeacher.text.toString(),
                    it.tvTileClassroom.text.toString(),
                    it.tvTileNote.text.toString()
                    )
                )
            }



                if (datanew[rocnik][day][index-day*11].subject == "<" && index - day * 11 != 0) {
                    datanew[rocnik][day][index-day*11].subject = datanew[rocnik][day][index-day*11 - 1].subject

                    if (datanew[rocnik][day][index-day*11].teacher != "")
                        datanew[rocnik][day][index-day*11].teacher = datanew[rocnik][day][index-day*11 - 1].teacher
                    if (datanew[rocnik][day][index-day*11].classroom != "")
                        datanew[rocnik][day][index-day*11].classroom = datanew[rocnik][day][index-day*11 - 1].classroom
                    if (datanew[rocnik][day][index-day*11].note != "")
                        datanew[rocnik][day][index-day*11].note = datanew[rocnik][day][index-day*11 - 1].note
                }



            // WRITE DATA TO TILE
                if (prefs.getBoolean(datanew[rocnik][day][index-day*11].subject, false)) {
                    prepis(it, datanew[rocnik][day][index-day*11])
                }


            // todo investigate
            if (it.tvTileSubject.text == "oběd") {
                it.tvTileSubject.text = context.getString(R.string.lunch)
                it.tvTileSubject.visibility = View.VISIBLE
            }

            // FORMAT TILE WHEN TRIDNICKA HODINA
            if (it.tvTileSubject.text.toString().lowercase() == "th") {
                it.tvTileSubject.visibility = View.VISIBLE
                it.tvTileSubject.text = context.getString(R.string.lunch)
                it.tvTileNote.visibility = View.VISIBLE
                it.tvTileNote.text = "Th"
            }

                if (it.tvTileSubject.text == "" || it.tvTileSubject.text == "oběd") {
                    it.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawableUnoccupied, context.theme)
                }
                else {
                    it.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawable, context.theme)
                }

        } // END OF FOR EACH TILE




    }



/*
    public fun updateTableButtons(context: Context, activity: Activity) {

/*
        tableArray.forEachIndexed { index, tile ->




            tile.root.visibility = View.VISIBLE
            tile.root.layoutParams.width = context.resources.getDimension(R.dimen.table_tile_size).toInt()

            val clTile = tableArray[index].clTile
            val setTile = ConstraintSet()
            setTile.clone(clTile)
            setTile.connect(tile.tvTileSubject.id, ConstraintSet.BOTTOM, tile.tvTileClassroom.id, ConstraintSet.TOP, 0)
            setTile.connect(tile.tvTileClassroom.id, ConstraintSet.TOP, tile.tvTileSubject.id, ConstraintSet.BOTTOM, 0)
            setTile.connect(tile.tvTileClassroom.id, ConstraintSet.END, tile.helpEnd.id, ConstraintSet.END, context.dpToPx(8F))
            setTile.applyTo(clTile)
            tile.tvTileClassroom.margin(bottom = 0F)

            val row: Int = when {
                index < 11 -> 0
                index < 22 -> 1
                index < 33 -> 2
                index < 44 -> 3
                index < 55 -> 4
                else -> 0
            }

            val i: Int = when {
                index < 11 -> index
                index < 22 -> index - 11
                index < 33 -> index - 22
                index < 44 -> index - 33
                else -> index - 44
            }

            var posun = 0
            if (!sharedPref.getBoolean(Utility.tableData[row][i][0], false)) {
                posun = 5
                if (!sharedPref.getBoolean(Utility.tableData[row][i][5], false)) {
                    posun = 10
                    if (!sharedPref.getBoolean(Utility.tableData[row][i][5], false)) {
                        // co mam dopc napsat sem aby student ktery nema ani jednu ze tri predmetu v danou vyucovaci hodinu mel ukazano volno ? odkladam na pozdeji tohle neni ted dulezite
                        // pri zahlidnuti tohohle me napadlo jednoduche reseni ale nemam cas, jednoduse pridam v inicializaci tableData promene prazdne pole za predchozi
                    }
                }
            }

            var test = ""
            if (Utility.tableData[row][i][0 + posun] == "Li") {
                test = Utility.tableData[row][i][0 + posun]
            }



            if (Utility.tableData[row][i][0 + posun] == "") {
                tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawableUnoccupied, context.theme)
            } else {
                tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawable, context.theme)
            }

            tile.tvTileSubject.text = Utility.tableData[row][i][0 + posun]
            tile.tvTileSubject.visibility = View.VISIBLE
            tile.tvTileTeacher.text = Utility.tableData[row][i][1 + posun]
            tile.tvTileTeacher.visibility = View.VISIBLE
            tile.tvTileClassroom.text = Utility.tableData[row][i][2 + posun]
            tile.tvTileClassroom.visibility = View.VISIBLE
            tile.tvTileNote.text = Utility.tableData[row][i][3 + posun]
            tile.tvTileNote.visibility = View.VISIBLE

            // NEW CODE - - - TT CHECK
            if (Utility.tableData[row][i][3 + posun] == "TT") {
                tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_tile_tt, context.theme)
            }



            if (Utility.tmpSuplovani[row][i][0 + posun] != "") {
                tile.tvTileSubject.text = Utility.tmpSuplovani[row][i][0 + posun]
                tile.tvTileTeacher.text = Utility.tmpSuplovani[row][i][1 + posun]
                tile.tvTileClassroom.text = Utility.tmpSuplovani[row][i][2 + posun]
                tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawableSuplovano, context.theme)
            }






            // podminka 1 - pokud je zapnuto skryt-ucebny
            if (sharedPref.getBoolean("HideClassrooms", false)) {
                tile.tvTileClassroom.visibility = View.GONE

                // podminka 2 - pokud je zapnuto vzdy-ukazovat-ucebny
            } else if (sharedPref.getBoolean("AlwaysShowClassrooms", false)) {
                tile.tvTileClassroom.visibility = View.VISIBLE

                //podminka 2-1 - podkud je ucebna neurcena
                if (tile.tvTileClassroom.text == "") {

                    // podminka 2-1-1 - pokud je v pravidlech urcena vychozi ucebna pro predmet
                    if (Utility.pravidla[tile.tvTileSubject.text] != null)
                        tile.tvTileClassroom.text = Utility.pravidla[tile.tvTileSubject.text]

                    // podminka 2-1-2 - pokud neni v pravidlech urcena vychozi ucebna pro predmet
                    else if (tile.tvTileSubject.text != "")
                        tile.tvTileClassroom.text = sharedPref.getString("Class", "").toString()
                            .replaceFirstChar { it.uppercase() }
                }

                // podminka 3 - poked je vypnuto skryt-ucebny i vzdy-zobrazovat-ucebny
            } else {
                if (tile.tvTileClassroom.text == "") {
                    tile.tvTileClassroom.visibility = View.GONE
                    if (Utility.pravidla[tile.tvTileSubject.text] != null)
                        tile.tvTileClassroom.text = Utility.pravidla[tile.tvTileSubject.text]
                    else if (tile.tvTileSubject.text != "")
                        tile.tvTileClassroom.text = sharedPref.getString("Class", "").toString()
                            .replaceFirstChar { it.uppercase() }
                } else {
                    tile.tvTileClassroom.visibility = View.VISIBLE
                }
            }


            if (sharedPref.getBoolean("HideDash", false))
                tile.tvTileDash.visibility = View.GONE
            else
                tile.tvTileDash.visibility = View.VISIBLE


            if (tile.tvTileTeacher.text == "") {
                tile.tvTileTeacher.visibility = View.GONE
                tile.tvTileDash.visibility = View.GONE
            }


            when (Utility.tmpSuplovani[row][i][3 + posun]) {
                "TT" -> {
                    tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_tile_tt, context.theme)
                    tile.tvTileNote.text = "TT"
                    tile.tvTileNote.visibility = View.VISIBLE
                }
                "OTT" -> {
                    tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_tile_ott, context.theme)
                    tile.tvTileNote.text = "opr."
                    tile.tvTileNote.visibility = View.VISIBLE
                }
                "odp" -> {
                    tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_tile_odp, context.theme)
                    tile.tvTileNote.text = "odpadá"
                    tile.tvTileNote.visibility = View.VISIBLE
                    tile.tvTileClassroom.visibility = View.GONE
                }
                else -> {

                }
            }


            tile.root.setOnClickListener {
                Utility.tableButtonOnClick(context, activity, Hodina(i, tile.tvTileSubject.text.toString(), tile.tvTileTeacher.text.toString(), tile.tvTileClassroom.text.toString(), tile.tvTileNote.text.toString()) )
            }
            tile.root.setOnLongClickListener {
                //easterEgg()
                true
            }
        }


        val tileSize = context.resources.getDimension(R.dimen.table_tile_size).toInt()
        val tileGap = context.resources.getDimension(R.dimen.table_tile_gap).toInt()

        // spojování dvouavíce-hodinovek
        for (index in 0..54) {
            val tile = tableArray[index]
            // IF FOLLOWING IS SAME -> INCREASE WIDTH
            prevTileCheck(index, tableArray)
            if (num > 1) {
                tile.root.layoutParams.width = tileSize * num + tileGap * (num - 1)
                val clTile = tableArray[index].clTile
                val setTile = ConstraintSet()
                setTile.clone(clTile)
                setTile.connect(tile.tvTileSubject.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
                setTile.connect(tile.tvTileClassroom.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, context.dpToPx(8F))
                setTile.connect(tile.tvTileNote.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, context.dpToPx(8F))
                setTile.clear(tile.tvTileClassroom.id, ConstraintSet.TOP)
                setTile.clear(tile.tvTileNote.id, ConstraintSet.TOP)
                setTile.applyTo(clTile)
                tile.tvTileClassroom.margin(bottom = 8F)
                tile.tvTileNote.margin(bottom = 8F)
            }
            // IF PREVIOUS IS SAME -> VISIBILITY GONE
            if (index != 0 && index != 11 && index != 22 && index != 33 && index != 44 && tile.tvTileSubject.text != "" && tile.tvTileSubject.text == tableArray[index - 1].tvTileSubject.text) {
                tile.root.visibility = View.GONE
            }
            num = 1 // RESET
        }
*/
*/



        suplo(context, binding)



        val tileSize = context.resources.getDimension(R.dimen.table_tile_size)
        val tileGap = context.resources.getDimension(R.dimen.table_tile_gap)

        for (index in 0..54) {
            val tile = tableArray[index]
            // IF FOLLOWING IS SAME -> INCREASE WIDTH
            prevTileCheck(index, tableArray)
            if (num > 1) {
                val newWidth = (tileSize * num + tileGap * (num - 1)) + context.dpToPx(1F)
                tile.root.layoutParams.width = newWidth.toInt()
                tile.root.maxWidth = newWidth.toInt()
                tile.tvTileSubject.layoutParams.width = context.dpToPx(0F)
                val clTile = tableArray[index].clTile
                val setTile = ConstraintSet()
                setTile.clone(clTile)
                setTile.connect(tile.tvTileSubject.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
                setTile.connect(tile.tvTileClassroom.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, context.dpToPx(8F))
                setTile.connect(tile.tvTileNote.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, context.dpToPx(8F))
                setTile.clear(tile.tvTileClassroom.id, ConstraintSet.TOP)
                setTile.clear(tile.tvTileNote.id, ConstraintSet.TOP)
                setTile.applyTo(clTile)
                tile.tvTileClassroom.margin(bottom = 8F)
                tile.tvTileNote.margin(bottom = 8F)
            }
            // IF PREVIOUS IS SAME -> VISIBILITY GONE
            if (
                index != 0 &&
                index != 11 &&
                index != 22 &&
                index != 33 &&
                index != 44 &&
                tile.tvTileSubject.text != "" &&
                tile.tvTileSubject.text == tableArray[index - 1].tvTileSubject.text &&
                tile.tvTileTeacher.text == tableArray[index - 1].tvTileTeacher.text &&
                tile.tvTileClassroom.text == tableArray[index - 1].tvTileClassroom.text &&
                tile.tvTileNote.text == tableArray[index - 1].tvTileNote.text
            ) {
                tile.root.visibility = View.GONE
            }
            num = 1 // RESET
        }

    }


    fun suplo(context: Context, binding: FragmentTimetableBinding) {

        arrayOf("Mon", "Tue", "Wed", "Thu", "Fri")
        val dayToNumber = mapOf<String, Int>(
            "Mon" to 0,
            "Tue" to 1,
            "Wed" to 2,
            "Thu" to 3,
            "Fri" to 4
        )

        val nameOfDay = SimpleDateFormat("E", Locale.US).format(Date())
        Log.d("test", nameOfDay)

        val todaysDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())


        if (File(context.filesDir, todaysDate).exists()) {


            val r = when (year) {
                "pr" -> 0
                "sk" -> 1
                "te" -> 2
                "kr" -> 3
                "kn" -> 4
                "sx" -> 5
                "sp" -> 6
                "ok" -> 7
                "i" -> 8
                "ii" -> 9
                "iii" -> 10
                "iv" -> 11
                else -> 0//12
            }

        val suploData = parseCSVToArrayOfArrays(readFromFile(context, todaysDate))





            val suplodatanew = Array(12) { rocnik ->
                Array(11) { hodina ->
                    Predmet(suploData[hodina + 11 * rocnik][0], suploData[hodina + 11 * rocnik][1], suploData[hodina + 11 * rocnik][2], suploData[hodina + 11 * rocnik][3])
                }
            }

            for (rr in 0..11) {
                for (h in 0..10) {
                    if (suplodatanew[rr][h].subject == "/" && rr != 0) {
                        suplodatanew[rr][h].subject = suplodatanew[rr - 1][h].subject

                        if (suplodatanew[rr][h].teacher != "")
                            suplodatanew[rr][h].teacher = suplodatanew[rr - 1][h].teacher
                        if (suplodatanew[rr][h].classroom != "")
                            suplodatanew[rr][h].classroom = suplodatanew[rr - 1][h].classroom
                        if (suplodatanew[rr][h].note != "")
                            suplodatanew[rr][h].note = suplodatanew[rr - 1][h].note
                    }

                    if (suplodatanew[rr][h].subject == "<" && h != 0) {
                        suplodatanew[rr][h].subject = suplodatanew[rr][h - 1].subject

                        if (suplodatanew[rr][h].teacher != "")
                            suplodatanew[rr][h].teacher = suplodatanew[rr][h - 1].teacher
                        if (suplodatanew[rr][h].classroom != "")
                            suplodatanew[rr][h].classroom = suplodatanew[rr][h - 1].classroom
                        if (suplodatanew[rr][h].note != "")
                            suplodatanew[rr][h].note = suplodatanew[rr][h - 1].note
                    }
                }
            }




            //







            //

        val x = when (nameOfDay) {
            "Mon" -> 0
            "Tue" -> 1
            "Wed" -> 2
            "Thu" -> 3
            "Fri" -> 4
            else -> 5
        }

        //val minusCisloZaKazdyRadek = when (index) {
        //    in 0..10 -> 0
        //    in 11..21 -> 11
        //    in 22..32 -> 22
        //    in 33..43 -> 33
        //    in 44..54 -> 44
        //    else -> 0
        //}



        val table: LayoutTimetableBinding = binding.includeTable
        val tableArray = arrayOf(
            table.incTile0,
            table.incTile1,
            table.incTile2,
            table.incTile3,
            table.incTile4,
            table.incTile5,
            table.incTile6,
            table.incTile7,
            table.incTile8,
            table.incTile9,
            table.incTile10,
            table.incTile11,
            table.incTile12,
            table.incTile13,
            table.incTile14,
            table.incTile15,
            table.incTile16,
            table.incTile17,
            table.incTile18,
            table.incTile19,
            table.incTile20,
            table.incTile21,
            table.incTile22,
            table.incTile23,
            table.incTile24,
            table.incTile25,
            table.incTile26,
            table.incTile27,
            table.incTile28,
            table.incTile29,
            table.incTile30,
            table.incTile31,
            table.incTile32,
            table.incTile33,
            table.incTile34,
            table.incTile35,
            table.incTile36,
            table.incTile37,
            table.incTile38,
            table.incTile39,
            table.incTile40,
            table.incTile41,
            table.incTile42,
            table.incTile43,
            table.incTile44,
            table.incTile45,
            table.incTile46,
            table.incTile47,
            table.incTile48,
            table.incTile49,
            table.incTile50,
            table.incTile51,
            table.incTile52,
            table.incTile53,
            table.incTile54
        )

        for (t in 0..10) {
            if (suploData[t + 11 * r][0] != "") {
                when (suploData[t + 11 * r][3]) {
                    "odp", "odp.", "odpadá", "přesun", "přesun." -> {
                        tableArray[t + 11 * x].root.background = ResourcesCompat.getDrawable(context.resources, R.drawable._tile_light04odpada, context.theme)
                        tableArray[t + 11 * x].tvTileSubject.setTextColor(Color.parseColor("#410002"))
                        tableArray[t + 11 * x].tvTileTeacher.setTextColor(Color.parseColor("#410002"))
                        tableArray[t + 11 * x].tvTileClassroom.setTextColor(Color.parseColor("#410002"))
                        tableArray[t + 11 * x].tvTileNote.setTextColor(Color.parseColor("#410002"))
                        setTextViewDrawableColor(tableArray[t + 11 * x].tvNoteIcon, Color.parseColor("#410002"))
                    }
                    else -> {
                        tableArray[t + 11 * x].root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawableSuplovano, context.theme)
                        tableArray[t + 11 * x].tvTileSubject.setTextColor(Color.parseColor("#003544"))
                        tableArray[t + 11 * x].tvTileTeacher.setTextColor(Color.parseColor("#003544"))
                        tableArray[t + 11 * x].tvTileClassroom.setTextColor(Color.parseColor("#003544"))
                        tableArray[t + 11 * x].tvTileNote.setTextColor(Color.parseColor("#003544"))
                        setTextViewDrawableColor(tableArray[t + 11 * x].tvNoteIcon, Color.parseColor("#003544"))
                    }
                }






                prepis(tableArray[t + 11 * x], suplodatanew[r][t])



                }
            }
        }
    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawablesRelative) {
            if (drawable != null) {
                drawable.colorFilter = PorterDuffColorFilter(/*ContextCompat.getColor(textView.context, color)*/color, PorterDuff.Mode.SRC_IN)
            }
        }
    }


    // TODO: fix text in tile w/ subject can expand tiles width when text is too long

    lateinit var year: String

    @SuppressLint("SetTextI18n")
    private fun writeToTile(context: Context, tile: PrefabSubjectTileBinding, posun1: Int, posun2: Int, posun3: Int) {

        tile.root.visibility = View.VISIBLE

        tile.tvTileSubject.visibility = View.VISIBLE
        tile.tvTileTeacher.visibility = View.VISIBLE
        tile.tvTileClassroom.visibility = View.VISIBLE
        tile.tvTileNote.visibility = View.VISIBLE

        //tile.tvTileDash.text = "-"
        tile.tvTileDash.visibility = View.VISIBLE
        tile.tvNoteIcon.visibility = View.GONE

        tile.root.background = ResourcesCompat.getDrawable(context.resources, R.drawable.tileBackgroundDrawable, context.theme)

        if (false) { // todo implement or delete (teachers timetable)
            tile.tvTileSubject.text = UtilityOther.celyTyden[posun1][posun2][0 + posun3]
            tile.tvTileTeacher.text = "(" + UtilityOther.arrayStudentsShort[posun2] + ")"
            tile.tvTileClassroom.text = UtilityOther.celyTyden[posun1][posun2][2 + posun3]
            tile.tvTileNote.text = UtilityOther.celyTyden[posun1][posun2][3 + posun3]
            //tile.tvTileDash.text = "-"
            tile.tvTileDash.visibility = View.GONE

            if (tile.tvTileClassroom.text == "") {
                if (UtilityOther.pravidla[tile.tvTileSubject.text] != null)
                    tile.tvTileClassroom.text = UtilityOther.pravidla[tile.tvTileSubject.text]
                else if (tile.tvTileSubject.text != "")
                    tile.tvTileClassroom.text = UtilityOther.arrayStudentsShort[posun2]
            }

        } else {
            tile.tvTileSubject.text = UtilityOther.celyTyden[posun1][posun2][0 + posun3]
            tile.tvTileTeacher.text = UtilityOther.celyTyden[posun1][posun2][1 + posun3]
            tile.tvTileClassroom.text = UtilityOther.celyTyden[posun1][posun2][2 + posun3]
            tile.tvTileNote.text = UtilityOther.celyTyden[posun1][posun2][3 + posun3]

            var b = true
            if (tile.tvTileClassroom.text == "") {
                if (UtilityOther.pravidla[tile.tvTileSubject.text] != null) {
                    tile.tvTileClassroom.text = UtilityOther.pravidla[tile.tvTileSubject.text]
                    tile.tvTileClassroom.visibility = View.GONE
                    b = false
                } else if (tile.tvTileSubject.text != "") {
                    tile.tvTileClassroom.text = year.replaceFirstChar { it.uppercase() }
                    tile.tvTileClassroom.visibility = View.GONE
                    b = false
                }
            }

            // ADDING DOTS FOR SMALL TILES WHEN CANT SHOW ADDITIONAL INFO
            //if (b && tile.tvTileNote.text != "")
            //    tile.tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dotdot, 0)
            //else if (b || tile.tvTileNote.text != "")
            //    tile.tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot, 0)

        }

    }

    // endregion











    fun prepis(tile: PrefabSubjectTileBinding, from: Predmet) {

        // PREDMET
        tile.tvTileSubject.text = from.subject
        if (from.subject == "") {
            tile.tvTileSubject.visibility = View.GONE
        } else {
            tile.tvTileSubject.visibility = View.VISIBLE
        }

        // VYUCUJICI
        tile.tvTileTeacher.text = from.teacher
        if (from.teacher == "") {
            tile.tvTileTeacher.visibility = View.GONE
        } else {
            tile.tvTileTeacher.visibility = View.VISIBLE
        }

        // UCEBNA
        if (from.classroom == "") {
            if (UtilityOther.pravidla[tile.tvTileSubject.text] != null) {
                tile.tvTileClassroom.text = UtilityOther.pravidla[tile.tvTileSubject.text]
            } else /*if (tile.tvTileSubject.text != "")*/ {
                tile.tvTileClassroom.text = UtilityOther.rocniky_short[year]
            }
            tile.tvTileClassroom.visibility = View.GONE
        } else {
            tile.tvTileClassroom.text = from.classroom
            tile.tvTileClassroom.visibility = View.VISIBLE
        }

        // POZNAMKA
        tile.tvTileNote.text = from.note
        if (from.note == "") {
            tile.tvTileNote.visibility = View.GONE
            tile.tvNoteIcon.visibility = View.GONE
        } else if (tile.tvTileNote.text.length > 6) /* switch to icon if text too long */ {
            tile.tvTileNote.visibility = View.GONE
            tile.tvNoteIcon.visibility = View.VISIBLE
        } else {
            tile.tvTileNote.visibility = View.VISIBLE
            tile.tvNoteIcon.visibility = View.GONE
        }

        // POMLCKA
        if (from.subject == "" || from.teacher == "") {
            tile.tvTileDash.visibility = View.GONE
        } else {
            tile.tvTileDash.visibility = View.VISIBLE
        }
    }









    // region TRANSITIONS
    public fun crossFade(context: Context, viewToFadeOut: View, viewToFadeIn: View) {
        val shortAnimationDuration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime)
        viewToFadeIn.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
        viewToFadeOut.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewToFadeOut.visibility = View.GONE
                    viewToFadeOut.alpha = 1f
                }
            })
    }
    public fun fadeIn(context: Context, view: View) {
        val shortAnimationDuration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime)
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }
    public fun fadeOut(context: Context, view: View) {
        val shortAnimationDuration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime)
        view.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                    view.alpha = 1f
                }
            })
    }
    // endregion

    // region FILE UTILS
    public fun buildCSVString(arrayToSave: Array<Array<String>>): String {
        val sb = StringBuilder()
        for (element in arrayToSave) {
            for (subElement in element) {
                sb.append(subElement)
                sb.append(",")
            }
            if (sb.isNotEmpty())
                sb.setLength(sb.length - 1)
            sb.append("\n")
        }
        return sb.toString()
    }
    public fun writeToFile(context: Context, fileName: String, content: String) {
        val path: File = context.filesDir
        try {
            val writer = FileOutputStream(File(path, fileName))
            writer.write(content.toByteArray())
            Toast.makeText(context, "Wrote to file: $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    public fun readFromFile(context: Context, fileName: String): String {
        val path: File = context.filesDir
        val readFrom = File(path, fileName)
        val content = ByteArray(readFrom.length().toInt())
        return try {
            val stream = FileInputStream(readFrom)
            stream.read(content)
            String(content)
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
        }
    }
    public fun parseCSVToArrayOfArrays(csv: String): Array<Array<String>> {
        val lines: Array<String> = csv.split("\n").toTypedArray()
        val words = arrayOfNulls<Array<String>>(lines.size)
        for ((index, line) in lines.withIndex()) {
            words[index] = line.split(",").toTypedArray()
        }
        return words.filterNotNull().toTypedArray()
    }
    var printError: Boolean = false
    lateinit var errorToPrint: java.lang.Exception
    public fun getData(url: URL, fileName: String, context: Context) {
        try {
            BufferedInputStream(url.openStream()).use { `in` ->
                FileOutputStream(File(context.filesDir, "$fileName.csv")).use { fileOutputStream ->
                    val dataBuffer = ByteArray(1024)
                    var bytesRead: Int
                    while (`in`.read(dataBuffer, 0, 1024)
                            .also { bytesRead = it } != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead)
                    }
                }
            }
        } catch (e: IOException) {
            // handle exception
            Log.d("marcel", e.toString())
            printError = true
            errorToPrint = e
        }
    }
    // endregion

    // region
    public fun tableButtonOnClick(context: Context, activity: Activity, hodina: Hodina) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        val bd = DialogDescriptionBinding.inflate(activity.layoutInflater) // bd for binding a dialog, vim ze to bude zahada tak to sem pisu
        dialog.setContentView(bd.root)

        if (UtilityOther.subjectsStringIds[hodina.subject] != null)
            bd.tvSubject.text = context.getString(UtilityOther.subjectsStringIds[hodina.subject]!!)
        else
            bd.tvSubject.text = hodina.subject

        bd.tvTeacher.text = UtilityOther.teachers[hodina.teacher]
        bd.tvClassroom.text = UtilityOther.classrooms[hodina.classroom]?.get(0).toString()
        bd.tvFloor.text = when (UtilityOther.classrooms[hodina.classroom]?.get(1)) {
            -1 -> context.getString(R.string.doesnt_exist)
            0 -> context.getString(R.string.ground_floor)
            1 -> context.getString(R.string.floor, context.getString(R.string.first))
            2 -> context.getString(R.string.floor, context.getString(R.string.second))
            else -> ""
        }

        if (UtilityOther.classrooms[hodina.classroom]?.get(1) == null)
            bd.tvFloor.text = ""
        if (hodina.subject == "") {
            bd.tvSubject.text = context.resources.getString(R.string.lunch_break)
            bd.tvSubject.textSize = 20F
            bd.tvSubject.typeface = Typeface.DEFAULT
        }
        if (hodina.teacher == "") {
            bd.tvTeacher.visibility = View.GONE
        }
        /*if (bd.tvSubject.text == "")
            bd.tvTeacher.text = "volná hodina + oběd + ratio + you fell off + didnt ask"*/
        bd.tvTimeStart.text = UtilityOther.zvoneni[hodina.index]?.get(0)
        bd.tvTimeEnd.text = UtilityOther.zvoneni[hodina.index]?.get(1)

        bd.btnAddToCalendar.setOnClickListener {
            addToCalendar(activity, bd.tvSubject.text.toString(), hodina.index)
        }

        if (hodina.note != "") {
            if (UtilityOther.commonNotesStringIdsMap[hodina.note] != null)
                bd.tvNote.text = context.getString(UtilityOther.commonNotesStringIdsMap[hodina.note]!!)
            else
                bd.tvNote.text = hodina.note
            bd.tvNote.visibility = View.VISIBLE
        }

        dialog.window?.setBackgroundDrawable(context.getDrawable(R.color.transparent))

        dialog.show()
    }
    public fun addToCalendar(activity: Activity, subjectShortcut: String, index: Int) { // TODO: remove 30 min before reminder in calendar app when creating event

        //val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        //val dateString: String = formatter.format(Date(System.currentTimeMillis()))

        //val hours = SimpleDateFormat("HH").format(Date(System.currentTimeMillis())).toLong()
        //val minutes = SimpleDateFormat("mm").format(Date(System.currentTimeMillis())).toLong()

        val millis: Long = Date(System.currentTimeMillis()).time
        val dayOnlyMillis = ((millis / 86400000) * 86400000) - 1 * 60 * 60000 // + 7 * 60 * 60000// - (13 * 60 * 60000)

        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI  // add/remove .EVENTS ??
        intent.putExtra(CalendarContract.Events.TITLE, subjectShortcut)
        //intent.putExtra(CalendarContract.Events.DESCRIPTION, "description_here")
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Liberec")
        intent.putExtra(CalendarContract.Events.ALL_DAY, "false")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,(dayOnlyMillis + UtilityOther.zvoneni[index]!![2].toLong() * 60 * 60000 + UtilityOther.zvoneni[index]!![3].toLong() * 60000))
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,(dayOnlyMillis + UtilityOther.zvoneni[index]!![2].toLong() * 60 * 60000 + UtilityOther.zvoneni[index]!![3].toLong() * 60000 + 45 * 60000))
        activity.startActivity(intent)
    }
    // endregion

    // region OTHER UTILS
    public fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) { // sets view margin
        layoutParams<ViewGroup.MarginLayoutParams> {
            left?.run { leftMargin = dpToPx(this) }
            top?.run { topMargin = dpToPx(this) }
            right?.run { rightMargin = dpToPx(this) }
            bottom?.run { bottomMargin = dpToPx(this) }
        }
    }
    public inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
        if (layoutParams is T) block(layoutParams as T)
    }
    public fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    public fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    // endregion

}