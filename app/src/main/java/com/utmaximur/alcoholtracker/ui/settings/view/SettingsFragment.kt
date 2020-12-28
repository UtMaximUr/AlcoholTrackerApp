package com.utmaximur.alcoholtracker.ui.settings.view


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R


class SettingsFragment : Fragment() {

    private lateinit var privacyPolicyLayout: Button
    private lateinit var termsOfUseLayout: Button
    private lateinit var rateUsButton: Button
    private lateinit var versionApp: TextView

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
    }


    private fun initUi(view: View) {
        findViewById(view)

        privacyPolicyLayout.setOnClickListener {

        }

        termsOfUseLayout.setOnClickListener {

        }

        rateUsButton.setOnClickListener {
            rateUs()
        }

        versionApp.text = BuildConfig.VERSION_NAME
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}