package com.utmaximur.alcoholtracker.ui.settings.view.impl


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
import com.utmaximur.alcoholtracker.ui.settings.presenter.SettingsPresenter
import com.utmaximur.alcoholtracker.ui.settings.presenter.factory.SettingsPresenterFactory
import com.utmaximur.alcoholtracker.ui.settings.view.SettingsView



class SettingsFragment : Fragment(), SettingsView {

    private lateinit var privacyPolicyLayout: Button
    private lateinit var termsOfUseLayout: Button
    private lateinit var rateUsButton: Button
    private lateinit var versionApp: TextView

    private lateinit var presenter: SettingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        presenter = SettingsPresenterFactory.createPresenter(requireContext())
        presenter.onAttachView(this)
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
            //presenter.onPrivacyPolicyClicked()
        }

        termsOfUseLayout.setOnClickListener {
            //presenter.onTermsOfUseClicked()
        }

        rateUsButton.setOnClickListener {
            presenter.onRateUsClicked()
        }

        versionApp.text = BuildConfig.VERSION_NAME
    }


    override fun showPrivacyPolicy() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("")
            )
        )
    }

    override fun showTermsOfUse() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("")
            )
        )
    }

    override fun rateUs() {
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
        presenter.onDetachView()
    }
}