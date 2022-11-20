package com.example.animation_samples.list_samples

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import com.example.animation_samples.R
import com.example.animation_samples.databinding.FragmentSampleItemBinding
import com.example.animation_samples.list_samples.SampleListFragment.Sample

class SampleItemFragment : Fragment(R.layout.fragment_sample_item) {
    private val args: SampleItemFragmentArgs by navArgs()
    private val sample: Sample get() = args.sample
    private val viewModel: SampleViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sample.observe(this) {
            when (it) {
                Sample.TRANSITION_SHARED_ELEMENT -> {
                    enterTransition = Slide()
                    sharedElementEnterTransition = ChangeBounds()
                }
                else -> {
                    enterTransition = null
                    sharedElementEnterTransition = null
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSampleItemBinding.bind(view)
        binding.item.menu.textSize = 32f
        binding.item.menu.textAlignment = View.TEXT_ALIGNMENT_CENTER
        binding.item.menu.text = getString(sample.text)
        binding.item.root.transitionName = getString(sample.text) + getString(R.string.transition_name_item)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}