package com.marcel7126.rozvrhdpg

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.marcel7126.rozvrhdpg.databinding.ActivityMainBinding
import com.marcel7126.rozvrhdpg.databinding.FragmentTimetableBinding
import java.sql.Time

//TODO make a button to remove unused saved data

open class MainActivity : AppCompatActivity() {

    // VARIABLE NAME PRESERVED TO COMMEMORATE PAIN WHEN PICKING UP VIEW BINDING (ik, its easy, but chaos when starting):
    private lateinit var dopc: ActivityMainBinding
    // VIEW MODEL NOT USED AT THIS MOMENT
    private val viewModel: MainViewModel by viewModels()
    // DECLARING APP CONTEXT TO INITIALIZE IN OnCreate() // todo
    private lateinit var appContext: Context

    // LITTLE COMPLEX CODE NECESSARY FOR APP SPECIFIC LOCALE INDEPENDENT ON DEVICE
    override fun attachBaseContext(newBase: Context) {
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(newBase)
        var lang = prefs.getString("Locale", "cs")
        if (lang == "device")
            lang = LocaleHelper.getSystemLocaleAll(newBase.resources.configuration).toString()
        super.attachBaseContext(LocaleHelper.wrap(newBase, lang!!))
    }

    // ON CREATE METHOD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // USING THEME FOR SPLASHSCREEN (DECLARED IN ANDROID MANIFEST, HERE SETTING THE DEFAULT ONE)
        setTheme(R.style.marcelTheme)
        // CREATING INSTANCE OF MAIN ACTIVITY VIEW BINDING
        dopc = ActivityMainBinding.inflate(layoutInflater)
        // NEW SPLASH SCREEN API NOT FULLY USED AT THE MOMENT
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
            //setOnExitAnimationListener()
        }
        // PASSING ROOT VIEW
        setContentView(dopc.root)
        // SETTING APPLICATION CONTEXT (FORGOT IF I USED IT) TODO: check and possibly delete
        appContext = applicationContext

        // SETTING UP BOTTOM NAVIGATION, HOST FRAGMENT VIEW AND NAVIGATION CONTROLLER
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController // can make it lateinit var to use outside od on create
        dopc.bottomNavigation.setupWithNavController(navController)
        // STARTING IN TIMETABLE FRAGMENT
        dopc.bottomNavigation.selectedItemId = R.id.timetableFragment

        // NAVIGATION CONTROLLER HANDLES NAVIGATING, HOWEVER I WANT RESELECT AND LONG CLICK LISTENERS
        // RESELECT LISTENER
        dopc.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> {  }
                R.id.timetableFragment -> {
                    // FINDING ROOT VIEW OF THE FRAGMENT IN ORDER TO PASS VIEW BINDING INTO STATIC OBJECT
                    val v: View = findViewById(R.id.clTimetableFragment)
                    val timetableBinding = FragmentTimetableBinding.bind(v)
                    // CALLING REFRESH ON TIMETABLE FRAGMENT RESELECT
                    TimeTableUpdates.refresh(this, this)
                    // FEEDBACK RESPONSE (TODO: remove)
                    Toast.makeText(this, "Called refresh", Toast.LENGTH_SHORT).show()
                }
                R.id.settingsFragment -> {  }
                else -> {  }
            }
        }
        // ON LONG CLICK LISTENER (TODO: add check to run only on timetable fragment)
        dopc.bottomNavigation.findViewById<View>(R.id.timetableFragment).setOnLongClickListener { _ ->
            // FINDING ROOT VIEW FOR VIEW BINDING
            val v: View = findViewById(R.id.clTimetableFragment)
            val timetableBinding = FragmentTimetableBinding.bind(v)
            // CALLING FORCE REFRESH ON LONG CLICK LISTENER
            TimeTableUpdates.forceRefresh(this, this)
            // FEEDBACK RESPONSE (TODO: remove)
            Toast.makeText(this, "Called force refresh", Toast.LENGTH_SHORT).show()
            true
        }

        // FIST LAUNCH THINGIES
        /*
        val prefs = getSharedPreferences(this.packageName, AppCompatActivity.MODE_PRIVATE)
        if (prefs.getString("Profile", "") == "") {

            //val badge = dopc.bottomNavigation.getOrCreateBadge(R.id.settingsFragment)
            //badge.isVisible = true
            //badge.number = 1

            Snackbar.make(dopc.root, R.string.sb_first_launch, Snackbar.LENGTH_INDEFINITE)
                .setAnchorView(dopc.bottomNavigation)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .setBackgroundTint(getColor(R.color.snack_bar_background))
                .setTextColor(getColor(R.color.snack_bar_text))
                .setActionTextColor(getColor(R.color.snack_bar_action_text))
                .setAction(R.string.sb_settings) {
                    navController.navigate(R.id.settingsFragment)
                }
                .show()
        }
        */

    }

}
