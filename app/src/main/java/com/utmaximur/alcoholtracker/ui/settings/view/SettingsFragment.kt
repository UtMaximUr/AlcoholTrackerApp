package com.utmaximur.alcoholtracker.ui.settings.view


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R

const val PREFS_NAME = "theme_prefs"
const val KEY_THEME = "prefs.theme"
const val THEME_UNDEFINED = -1
const val THEME_LIGHT = 0
const val THEME_DARK = 1

class SettingsFragment : Fragment() {

    private lateinit var privacyPolicyLayout: Button
    private lateinit var termsOfUseLayout: Button
    private lateinit var rateUsButton: Button
    private lateinit var versionApp: TextView
    private lateinit var themeSwitch: SwitchMaterial

    private val sharedPrefs by lazy {  activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        initUi(view)
        return view
    }

    private fun findViewById(view: View) {
        privacyPolicyLayout = view.findViewById(R.id.privacy_policy_button)
        termsOfUseLayout = view.findViewById(R.id.terms_of_use_button)
        rateUsButton = view.findViewById(R.id.rate_app_button)
        versionApp = view.findViewById(R.id.version_app)
        themeSwitch = view.findViewById(R.id.theme_switch)
    }


    private fun initUi(view: View) {
        findViewById(view)
        initTheme()

        privacyPolicyLayout.setOnClickListener {

        }

        termsOfUseLayout.setOnClickListener {

        }

        rateUsButton.setOnClickListener {
            rateUs()
        }

        themeSwitch.setOnCheckedChangeListener { _, b ->
            if(b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(THEME_DARK)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(THEME_LIGHT)
            }
        }

        versionApp.text = BuildConfig.VERSION_NAME
    }

    private fun initTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            themeSwitch.visibility = View.VISIBLE
        } else {
            themeSwitch.visibility = View.GONE
        }
        when (getSavedTheme()) {
            THEME_DARK -> themeSwitch.isChecked = true
            THEME_UNDEFINED -> {
                when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> themeSwitch.isChecked = true
                }
            }
        }
    }

    private fun saveTheme(theme: Int) = sharedPrefs?.edit()?.putInt(KEY_THEME, theme)?.apply()

    private fun getSavedTheme() = sharedPrefs?.getInt(KEY_THEME, THEME_UNDEFINED)

    private fun rateUs() {
        val appPackageName: String = requireContext().packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}