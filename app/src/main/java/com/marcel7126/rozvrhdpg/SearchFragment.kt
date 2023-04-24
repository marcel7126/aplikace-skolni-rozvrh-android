package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.marcel7126.rozvrhdpg.databinding.*
import java.io.*

class SearchFragment: Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var sbinding: LayoutSearchTableResultBinding
    private var day: Int = 0
    private var search: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        sbinding = LayoutSearchTableResultBinding.bind(view)

        val items: Array<String> = UtilityOther.arrayStudents + UtilityOther.arrayTeachers

        val itemsList = items.toList()

        val itemsArray = arrayOfNulls<SearchedItem>(UtilityOther.arrayStudents.size + UtilityOther.arrayTeachers.size)


        UtilityOther.arrayStudents.forEachIndexed { index, it ->
            itemsArray[index] = SearchedItem(it, UtilityOther.arrayStudentsShort[index], R.drawable.ic_school_20)
        }

        UtilityOther.arrayTeachers.forEachIndexed { index, it ->
            itemsArray[index + UtilityOther.arrayStudents.size] = SearchedItem(it, UtilityOther.arrayTeachersShort[index], R.drawable.ic_person_20)
        }

        binding.listView.adapter = MyAdapter(requireActivity(), R.layout.item_search, itemsArray.toList().requireNoNulls())

        //binding.listView.adapter = ArrayAdapter(requireActivity(), R.layout.search_item, R.id.tvCardText, items)
        binding.listView.isTextFilterEnabled = true
        //binding.listView.isClickable = true

        binding.listView.setOnItemClickListener { adapterView, itemView, position, id ->
            //itemView as TextView
            val textView = itemView.findViewById<TextView>(R.id.tvCardText)
            search = textView.text.toString()
            if (UtilityOther.celyTydenNacteno)
                updateResults()
            refreshAnim()
        }



        setupSearchView()

        arrayOf(
            sbinding.include100,
            sbinding.include101,
            sbinding.include102,
            sbinding.include103,
            sbinding.include104,
            sbinding.include105,
            sbinding.include106,
            sbinding.include107,
            sbinding.include108,
            sbinding.include109,
            sbinding.include110,
        ).forEachIndexed { index, it ->
            it.tvNumber.text = index.toString()
            it.tvFrom.text = UtilityOther.zvoneni[index]?.get(0)
            it.tvTo.text = UtilityOther.zvoneni[index]?.get(1)
        }

        arrayOf(
            sbinding.include0,
            sbinding.include1,
            sbinding.include2,
            sbinding.include3,
            sbinding.include4,
            sbinding.include5,
            sbinding.include6,
            sbinding.include7,
            sbinding.include8,
            sbinding.include9,
            sbinding.include10
        ).forEach { it ->
            clearTile(it)
            it.root.background = ResourcesCompat.getDrawable(this.resources, R.drawable.tileBackgroundDrawable, requireContext().theme)
        }


        // NEW LIST POPUP
        val listPopupWindow = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)
        // ANCHOR POPUP LIST TO BUTTON
        listPopupWindow.anchorView = binding.btnDay
        // USING ADAPTER FOR ITEMS
        val days = listOf(getString(R.string.day_short_mon).uppercase(), getString(R.string.day_short_tue).uppercase(), getString(R.string.day_short_wed).uppercase(), getString(R.string.day_short_thu).uppercase(), getString(R.string.day_short_fri).uppercase())
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_day, days)
        listPopupWindow.setAdapter(adapter)
        // ON ITEM CLICK LISTENER
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long -> // TODO: cleanup
            day = position
            binding.btnDay.text = resources.getString(UtilityOther.daysStringIds[position])
            if (UtilityOther.celyTydenNacteno)
                updateResults()
            refreshAnim()
            listPopupWindow.dismiss()
        }

        binding.btnDay.setOnClickListener {
            listPopupWindow.show()
        }


        if (!File(requireContext().filesDir, "celyTyden.csv").exists())
            TimeTableUpdates.downloadFromSpreadSheets("celyTyden", requireContext(), requireActivity())




    }

    private fun refreshAnim() { // TODO: OPTIMIZE BY HAVING SEPARATE LAYOUT WHICH CAN BE WHOLE FADED
        arrayOf(
            sbinding.include0,
            sbinding.include1,
            sbinding.include2,
            sbinding.include3,
            sbinding.include4,
            sbinding.include5,
            sbinding.include6,
            sbinding.include7,
            sbinding.include8,
            sbinding.include9,
            sbinding.include10
        ).forEach { fadeIn(it.root) }
    }

    private fun fadeIn(view: View) {
        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }


    private fun updateResults() {

        val co: String = when {
            UtilityOther.teachers[search] != null -> "t"
            UtilityOther.classrooms[search] != null -> "s"
            else -> ""
        }

        if (co == "s") binding.tvSearchHead.text = UtilityOther.classroomsToShort[search]
        if (co == "t") binding.tvSearchHead.text = UtilityOther.teachers[search]


        val posunTrida = when (UtilityOther.classrooms[search]?.get(0)) {
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
            else -> {
                0
            }
        }


        val resultTiles = arrayOf(
            sbinding.include0,
            sbinding.include1,
            sbinding.include2,
            sbinding.include3,
            sbinding.include4,
            sbinding.include5,
            sbinding.include6,
            sbinding.include7,
            sbinding.include8,
            sbinding.include9,
            sbinding.include10,
            sbinding.include11,
            sbinding.include12,
            sbinding.include13,
            sbinding.include14,
            sbinding.include15,
            sbinding.include16,
            sbinding.include17,
            sbinding.include18,
            sbinding.include19,
            sbinding.include20,
            sbinding.include21,
            sbinding.include22,
            sbinding.include23,
            sbinding.include24,
            sbinding.include25,
            sbinding.include26,
            sbinding.include27,
            sbinding.include28,
            sbinding.include29,
            sbinding.include30,
            sbinding.include31,
            sbinding.include32
        )
        resultTiles.forEachIndexed { index, it ->

            val cislo1: Int = when (index) {
                in 0..10 -> 0
                in 11..21 -> 5
                else -> 10
            }
            val cislo2: Int = when (index) {
                in 0..10 -> 0
                in 11..21 -> 11
                else -> 22
            }

            when (co) {
                "s" -> {
                    it.root.setOnClickListener { _ ->
                        UtilityFun.tableButtonOnClick(requireContext(), requireActivity(), Hodina(index - cislo2, it.tvTileSubject.text.toString(), it.tvTileTeacher.text.toString(), it.tvTileClassroom.text.toString(), it.tvTileNote.text.toString()) )
                    }
                }
                "t" -> {
                    it.root.setOnClickListener { _ ->
                        UtilityFun.tableButtonOnClick(requireContext(), requireActivity(), Hodina(index, it.tvTileSubject.text.toString(), UtilityOther.teachers[search]!!, it.tvTileClassroom.text.toString(), it.tvTileNote.text.toString()) )
                    }
                }
            }

            if (it.root.height != requireContext().dpToPx(64F) && index < 11)
                setTileHeight(it, 64F)

            it.tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            when {
                // STUDENT -- FIRST ROW
                index < 11 && co == "s" -> {

                    writeToTile(it, day + posunTrida, index, 0, false)

                    if (it.tvTileSubject.text == "") {
                        clearTile(it)
                    }
                    if (it.tvTileSubject.text == "oběd") {
                        it.tvTileSubject.text = requireActivity().getString(R.string.lunch)
                        clearTile(it)
                        it.tvTileSubject.visibility = View.VISIBLE
                    }
                    if (it.tvTileSubject.text.toString().lowercase() == "th") {
                        clearTile(it)
                        it.tvTileSubject.visibility = View.VISIBLE
                        it.tvTileSubject.text = requireActivity().getString(R.string.lunch)
                        it.tvTileNote.visibility = View.VISIBLE
                        it.tvTileNote.text = "Th"
                    }

                }
                // STUDENT -- SECOND ROW
                index < 22 && co == "s"-> {
                    if (UtilityOther.celyTyden[day + posunTrida][index - cislo2][0 + cislo1] != "" && UtilityOther.celyTyden[day + posunTrida][index - cislo2][0 + cislo1] != "Sb") {

                        writeToTile(it, day + posunTrida, index - cislo2, 0 + cislo1, false)
                        it.tvTileClassroom.visibility = View.GONE
                        it.tvTileNote.visibility = View.GONE

                        setTileHeight(it, 30F)
                        setTileHeight(resultTiles[index - cislo2], 30F)

                        resultTiles[index - 11].tvTileClassroom.visibility = View.GONE
                        resultTiles[index - 11].tvTileNote.visibility = View.GONE

                        if (it.tvTileSubject.text == "") {
                            clearTile(it)
                        }
                        if (it.tvTileSubject.text == "oběd") {
                            clearTile(it)
                            it.tvTileSubject.visibility = View.VISIBLE
                        }

/*
                        if (resultTiles[index - 11].tvTileSubject.text == "") {
                            clearTile(it)
                            resultTiles[index - 11].root.visibility = View.VISIBLE
                        }
*/

                    } else {
                        it.root.visibility = View.GONE
                        resultTiles[index - 11].tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                // STUDENT -- THIRD ROW
                index < 33 && co == "s"-> {
                    if (UtilityOther.celyTyden[day + posunTrida][index - cislo2][0 + cislo1] != "" && UtilityOther.celyTyden[day + posunTrida][index - cislo2][0 + cislo1] != "Sb" ) {

                        writeToTile(it, day + posunTrida, index - cislo2, 0 + cislo1, false)
                        it.tvTileClassroom.visibility = View.GONE
                        it.tvTileNote.visibility = View.GONE

                        setTileHeight(it, 18F)
                        if (resultTiles[index - 11].tvTileSubject.text == "") {
                            clearTile(it)
                            resultTiles[index - 11].root.visibility = View.VISIBLE
                        }
                        /*
                        if (resultTiles[index - 22].tvTileSubject.text == "") {
                               clearTile(it)
                               resultTiles[index - 22].root.visibility = View.VISIBLE
                           }*/
                        setTileHeight(resultTiles[index - 11], 18F)
                        setTileHeight(resultTiles[index - 22], 18F)
                        resultTiles[index - 11].tvTileClassroom.visibility = View.GONE
                        resultTiles[index - 11].tvTileNote.visibility = View.GONE
                        resultTiles[index - 22].tvTileClassroom.visibility = View.GONE
                        resultTiles[index - 22].tvTileNote.visibility = View.GONE

                        if (it.tvTileSubject.text == "") {
                            clearTile(it)
                        }
                        if (it.tvTileSubject.text == "oběd") {
                            clearTile(it)
                            it.tvTileSubject.visibility = View.VISIBLE
                        }

                    } else it.root.visibility = View.GONE
                }
                // SEARCH TEACHER (TODO: ADD ALL YEARS TAUGHT WHEN MIXED)
                index < 11 && co == "t" -> {

                    var check = false
                    for (i in 0..11) {
                        if (UtilityOther.celyTyden[day + i * 5][index][1] == UtilityOther.teachers[search]) {
                            writeToTile(it, day + i * 5, index, 0, true)
                            check = true
                            break
                        }
                        if (UtilityOther.celyTyden[day + i * 5][index][1 + 5] == UtilityOther.teachers[search]) {
                            writeToTile(it, day + i * 5, index, 5, true)
                            check = true
                            break
                        }
                        if (UtilityOther.celyTyden[day + i * 5][index][1 + 10] == UtilityOther.teachers[search]) {
                            writeToTile(it, day + i * 5, index, 10, true)
                            check = true
                            break
                        }

                    }

                    if (!check) {
                        clearTile(it)
                    }

                }
                // CLEAR
                index >= 11 && co == "t" -> {
                    if (it.root.visibility != View.GONE)
                        it.root.visibility = View.GONE
                }
                else -> {  }
            }

            if (it.tvTileClassroom.text == "") it.tvTileClassroom.visibility = View.GONE
            if (it.tvTileNote.text == "") it.tvTileNote.visibility = View.GONE
            if (it.tvTileNote.text.length > 6) {
                it.tvNoteIcon.visibility = View.VISIBLE
                it.tvTileNote.visibility = View.GONE
            }

        }

    }

    private fun writeToTile(tile: PrefabSubjectTileBinding, posun1: Int, posun2: Int, posun3: Int, filteredByTeacher: Boolean) {

        tile.root.visibility = View.VISIBLE

        setTileHeight(tile, 64F)

        tile.tvTileSubject.visibility = View.VISIBLE
        tile.tvTileTeacher.visibility = View.VISIBLE
        tile.tvTileClassroom.visibility = View.VISIBLE
        tile.tvTileNote.visibility = View.VISIBLE
        tile.tvTileDash.visibility = View.VISIBLE
        tile.tvNoteIcon.visibility = View.GONE
        //tile.tvTileDash.text = "-"
        tile.root.background = ResourcesCompat.getDrawable(this.resources, R.drawable.tileBackgroundDrawable, requireContext().theme)

        if (filteredByTeacher) {
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
                }
                else if (tile.tvTileSubject.text != "") {
                    tile.tvTileClassroom.text = UtilityOther.classroomsToShort[search]
                    tile.tvTileClassroom.visibility = View.GONE
                    b = false
                }
            }

            if (b && tile.tvTileNote.text != "")
                tile.tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dotdot, 0)
            else if (b || tile.tvTileNote.text != "")
                tile.tvTileTeacher.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot, 0)

        }

    }

    private fun clearTile(tile: PrefabSubjectTileBinding) {
        tile.tvTileSubject.visibility = View.GONE
        tile.tvTileTeacher.visibility = View.GONE
        tile.tvTileClassroom.visibility = View.GONE
        tile.tvTileNote.visibility = View.GONE
        tile.tvTileDash.visibility = View.GONE
        tile.tvNoteIcon.visibility = View.GONE
        tile.root.setOnClickListener { }
        tile.root.background = ResourcesCompat.getDrawable(this.resources, R.drawable.tileBackgroundDrawableUnoccupied, requireContext().theme)
    }

    private fun setTileHeight(tile: PrefabSubjectTileBinding, height: Float) {
        val params = tile.root.layoutParams
        params.height = requireContext().dpToPx(height)
        tile.root.layoutParams = params
    }

    private fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

    private fun setupSearchView() {
        binding.tietSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {  }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s)) {
                    binding.listView.clearTextFilter()
                } else {
                    binding.listView.setFilterText(s.toString())
                }
            }
        })
    }

}