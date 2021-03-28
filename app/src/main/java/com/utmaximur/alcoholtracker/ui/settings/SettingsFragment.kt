package com.utmaximur.alcoholtracker.ui.settings


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.ui.settings.adapter.ThemeListAdapter
import com.utmaximur.alcoholtracker.util.dpToPx
import com.utmaximur.alcoholtracker.util.toGone
import com.utmaximur.alcoholtracker.util.toVisible


const val PREFS_NAME = "theme_prefs"
const val KEY_THEME = "prefs.theme"
const val THEME_UNDEFINED = -1
const val THEME_LIGHT = 0
const val THEME_DARK = 1
const val THEME_HEIGHT = 100

const val MARKET_APP = "market://details?id="
const val HTTPS_APP = "https://play.google.com/store/apps/details?id="

const val PRIVACY_POLICY = "https://alcohol-tracker.flycricket.io/privacy.html"
const val TERMS_OF_USE = "https://alcohol-tracker.flycricket.io/terms.html"

class SettingsFragment : Fragment(), ThemeListAdapter.ThemeListener {

    private lateinit var privacyPolicyLayout: Button
    private lateinit var termsOfUseLayout: Button
    private lateinit var rateUsButton: Button
    private lateinit var versionApp: TextView
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var themeLayout: LinearLayout
    private lateinit var themeList: RecyclerView

    private val sharedPrefs by lazy {
        activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

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
        themeLayout = view.findViewById(R.id.theme_layout)
        themeList = view.findViewById(R.id.theme_list)
    }


    private fun initUi(view: View) {
        findViewById(view)
        initTheme()

        privacyPolicyLayout.setOnClickListener {
            goToUrl(PRIVACY_POLICY)
        }

        termsOfUseLayout.setOnClickListener {
            goToUrl(TERMS_OF_USE)
        }

        rateUsButton.setOnClickListener {
            rateUs()
        }

        themeList.adapter = ThemeListAdapter(
            requireContext().resources.getStringArray(R.array.theme_array).toList(),
            getTheme()
        )
        (themeList.adapter as ThemeListAdapter).setListener(this)

        themeSwitch.setOnCheckedChangeListener { _, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
                saveTheme(THEME_UNDEFINED)
                animateViewHeight(themeList, 0)
            } else {
                animateViewHeight(themeList, THEME_HEIGHT.dpToPx())
            }
        }

        versionApp.text = BuildConfig.VERSION_NAME
    }

    private fun animateViewHeight(view: RecyclerView, targetHeight: Int) {
        val animator: ValueAnimator = ObjectAnimator.ofInt(view.height, targetHeight)
        animator.addUpdateListener { animation ->
            val params = view.layoutParams
            params.height = animation.animatedValue as Int
            view.layoutParams = params
        }
        animator.start()
    }

    private fun initTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            themeLayout.toVisible()
        } else {
            themeLayout.toGone()
        }
        when (getSavedTheme()) {
            THEME_DARK -> {
                themeSwitch.isChecked = false
                val params = themeList.layoutParams
                params.height = THEME_HEIGHT.dpToPx()
                themeList.layoutParams = params
            }
            THEME_LIGHT -> {
                themeSwitch.isChecked = false
                val params = themeList.layoutParams
                params.height = THEME_HEIGHT.dpToPx()
                themeList.layoutParams = params
            }
            THEME_UNDEFINED -> {
                themeSwitch.isChecked = true
            }
        }
    }

    override fun saveTheme(theme: Int) {
        sharedPrefs?.edit()?.putInt(KEY_THEME, theme)?.apply()
        when (theme) {
            THEME_DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            THEME_LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            THEME_UNDEFINED -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
            }
        }
    }

    private fun getSavedTheme() = sharedPrefs?.getInt(KEY_THEME, THEME_UNDEFINED)

    private fun getTheme(): Int {
        return if (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        ) {
            THEME_DARK
        } else {
            THEME_LIGHT
        }
    }

    private fun goToUrl(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    private fun rateUs() {
        val appPackageName: String = requireContext().packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        MARKET_APP + appPackageName
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        HTTPS_APP + appPackageName
                    )
                )
            )
        }
    }
}