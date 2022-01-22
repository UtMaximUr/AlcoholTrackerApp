package com.utmaximur.alcoholtracker.presentation.settings


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentSettingsBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseFragment
import com.utmaximur.alcoholtracker.presentation.settings.adapter.ThemeListAdapter
import com.utmaximur.alcoholtracker.util.*


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private lateinit var themeListAdapter: ThemeListAdapter
    private lateinit var themeConcatAdapter: ConcatAdapter

    private val sharedPrefs by lazy {
        activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(binding) {
        initSettings()

        privacyPolicyButton.setOnClickListener {
            goToUrl(PRIVACY_POLICY)
        }

        termsOfUseButton.setOnClickListener {
            goToUrl(TERMS_OF_USE)
        }

        rateAppButton.setOnClickListener {
            rateUs()
        }

        themeListAdapter = ThemeListAdapter(
            this@SettingsFragment::saveTheme,
            requireContext().resources.getStringArray(R.array.theme_array).toList(),
            getTheme()
        )
        themeConcatAdapter = ConcatAdapter(themeListAdapter)
        themeList.adapter = themeConcatAdapter

        themeSwitch.setOnCheckedChangeListener { _, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
                saveTheme(THEME_UNDEFINED)
                animateViewHeight(themeList, 0)
            } else {
                animateViewHeight(themeList, THEME_HEIGHT.dpToPx())
            }
        }

        updateSwitch.setOnCheckedChangeListener { _, b ->
            sharedPrefs?.edit()?.putBoolean(KEY_UPDATE, b)?.apply()
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

    private fun initSettings() = with(binding) {
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

        updateSwitch.isChecked = getSavedUpdate()!!
    }

    private fun saveTheme(theme: Int) {
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

    private fun getSavedUpdate() = sharedPrefs?.getBoolean(KEY_UPDATE, UPDATE_UNDEFINED)

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