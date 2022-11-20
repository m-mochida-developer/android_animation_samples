package com.example.animation_samples.navigation_animation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.Explode
import androidx.transition.TransitionSet
import com.example.animation_samples.R
import com.example.animation_samples.databinding.FragmentCBinding
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity.AnimationType

class CFragment : Fragment(R.layout.fragment_c) {
    private val viewModel: AnimationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.animationType.observe(this) {
            when (it) {
                AnimationType.TRANSITION,
                AnimationType.SHARED_ELEMENT,
                -> {
                    val transition = TransitionSet().apply {
                        addTransition(Explode())
                    }
                    enterTransition = transition.clone().apply {
                        startDelay = 400L
                    }
                    returnTransition = transition.clone()
                    if (it == AnimationType.SHARED_ELEMENT) {
                        sharedElementEnterTransition = ChangeBounds()
                    }
                }
                else -> Unit
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCBinding.bind(view)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}