package com.utmaximur.alcoholtracker.presentation.view_track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentViewTrackBinding
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import javax.inject.Inject

class ViewTrackFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<ViewTrackViewModel>

    private val viewModel: ViewTrackViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentViewTrackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTrackBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUI()
        initViewArguments()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onClickClose()
        }

        toolbar.setOnMenuItemClickListener {
            onClickClose()
            onClickEdit()
            true
        }
    }

    private fun initViewArguments() = with(binding) {
        val track: Track = requireArguments().getParcelable(VIEW_DRINK)!!
        toolbar.title = track.drink.getResString(requireContext())
        imageTrack.setImagePath(track.image)
        volume.setTextOrEmpty(volumeText, track.volume)
        degree.setTextOrEmpty(degreeText, track.degree)
        quantity.setTextOrEmpty(quantityText, track.quantity)
        price.setTextOrEmpty(priceText, track.price)
        event.setTextOrEmpty(eventText, track.event)
        dateText.text = track.date.formatDate(requireContext())
    }

    private fun onClickEdit() {
        val bundle = Bundle()
        bundle.putParcelable(DRINK, requireArguments().getParcelable(VIEW_DRINK)!!)
        findNavController().navigate(R.id.addFragment, bundle)
    }

    private fun onClickClose() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}