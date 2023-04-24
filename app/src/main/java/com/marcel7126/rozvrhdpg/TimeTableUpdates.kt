package com.marcel7126.rozvrhdpg

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.Executors

object TimeTableUpdates {

    // region REFRESH FUNCTIONS
    fun refresh(context: Context, activity: Activity) {
        //Toast.makeText(requireContext(), "Started regular fresh...", Toast.LENGTH_SHORT).show()
        downloadFromSpreadSheets("suplo", context, activity)
        //not done yet
    // Utility01.updateTableButtons(context, activity, binding)
    }
    fun forceRefresh(context: Context, activity: Activity) {
        //Toast.makeText(requireContext(), "Started full fresh...", Toast.LENGTH_SHORT).show()
        downloadFromSpreadSheets("suplo", context, activity)
        downloadFromSpreadSheets("celyTyden", context, activity)
        //not done yet
    // Utility01.updateTableButtons(context, activity, binding)
    }
    fun load(context: Context, activity: Activity) {
        UtilityFun.rewriteTimetableButtons(context, activity)
    }
    // endregion

    // region DOWNLOADS
    public fun downloadFromSpreadSheets(actionName: String, context: Context, activity: Activity) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        activity.findViewById<View>(R.id.pbTableLoad).visibility = View.VISIBLE

        executor.execute {
            UtilityFun.getData(UtilityOther.spreadsheetsURLs[actionName]!!, actionName, context)
            handler.post {
                if (UtilityFun.printError) {
                    Log.d("marcel", UtilityFun.errorToPrint.toString())
                    errorSnackBar(UtilityFun.errorToPrint.toString(), context, activity)
                }
                else
                    when (actionName) {
                        "celyTyden" -> processDataFromCelyTyden(context, activity)
                        "suplo" -> processDataFromSuplo(context)
                        else -> {  }
                    }
                UtilityFun.printError = false
                // tohle patri jinam Utility01.crossFade(context, binding.pbTableLoad, binding.includeTable.root)
                activity.findViewById<View>(R.id.pbTableLoad).visibility = View.GONE
            }
        }
    }
    public fun errorSnackBar(error: String, context: Context, activity: Activity) {
        Snackbar.make(activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0).rootView, error, Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(R.id.bottom_navigation)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(context.getColor(R.color.snack_bar_background))
            .setTextColor(context.getColor(R.color.snack_bar_text))
            .setActionTextColor(context.getColor(R.color.snack_bar_action_text))
            .setAction(R.string.retry) {
                //Utility01.refresh(context, activity, binding)
            }
            .show()
    }
    // endregion

    // region PROCESS DATA
    public fun processDataFromSuplo(context: Context) {
        val loaded = UtilityFun.parseCSVToArrayOfArrays(UtilityFun.readFromFile(context, "suplo" + ".csv"))

        val formatted: Array<Array<String>> = Array((12 * 11 + 1) * 7) { i ->
            if (loaded.getOrNull(i) != null)
                Array(15) { ii ->
                    if (loaded[i].getOrNull(ii) != null)
                        loaded[i][ii].replace("\r", "")
                    else
                        ""
                }
            else
                arrayOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        }



        for (i in 0..6) {
            if (formatted[i * 133][0] != "") {

                val l: List<String> = formatted[i * 133][0].split("/", "-", ".", " ", ". ")

                val ls = Array(2) { it ->
                    if (l[it].length == 1)
                        "0".plus(l[it])
                    else
                        l[it]
                }

                val s: String =
                    ls[0] + "-" + ls[1] + "-" + Calendar.getInstance().get(Calendar.YEAR)

                val processed: Array<Array<String>> = Array(12 * 11 + 1) { th ->
                    if (th + 1 < 12 * 11 + 1) {
                        Array(15) { d ->
                            formatted[1 + th + i * 133][d]
                        }
                    } else {
                        arrayOf(
                            formatted[0][1],
                            formatted[0][2],
                            formatted[0][3],
                            formatted[0][4],
                            formatted[0][5],
                            formatted[0][6],
                            formatted[0][7]
                        )
                    }
                }
                UtilityFun.writeToFile(context, s, UtilityFun.buildCSVString(processed))
            }
        }
    }
    public fun processDataFromCelyTyden(context: Context, activity: Activity) {
        val loaded = UtilityFun.parseCSVToArrayOfArrays(UtilityFun.readFromFile(context, "celyTyden.csv"))

        UtilityOther.celyTyden = Array(12 * 5) { y ->
            Array(11) { i ->
                if (loaded.getOrNull(i + y * 11) != null)
                    Array(15) { ii ->
                        if (loaded[i + y * 11].getOrNull(ii) != null)
                            loaded[i + y * 11][ii].replace("\r", "")
                        else
                            ""
                    }
                else
                    arrayOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
            }
        }

        UtilityOther.celyTydenNacteno = true
        //Utility01.updateTableButtons(context, activity, binding)
        // todo remove^^^
    }
    // endregion



}