package com.example.animation_samples.navigation_animation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.Explode
import androidx.transition.TransitionSet
import com.example.animation_samples.R
import com.example.animation_samples.databinding.FragmentABinding
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity.AnimationType

class AFragment : Fragment(R.layout.fragment_a) {
    private val viewModel: AnimationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.animationType.observe(this) {
            when (it) {
                AnimationType.TRANSITION,
                AnimationType.SHARED_ELEMENT -> {
                    val transition = TransitionSet().apply {
                        addTransition(Explode())
                    }
                    // 退場Transitionを設定
                    exitTransition = transition.clone()
                    // 戻る操作の再入場Transitionを設定
                    reenterTransition = transition.clone().apply {
                        // アニメーションが被らないように遅らせる
                        startDelay = 400L
                    }
                }
                else -> Unit
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentABinding.bind(view)
        binding.nextButton.setOnClickListener {
            val action: Int
            val extras: Navigator.Extras?
            when (viewModel.animationType.value) {
                AnimationType.NOTHING,
                AnimationType.TRANSITION -> {
                    action = R.id.action_AFragment_to_BFragment_no_animation
                    extras = null
                }
                AnimationType.SLIDE -> {
                    action = R.id.action_AFragment_to_BFragment_slide_animation
                    extras = null
                }
                AnimationType.UNIQUE -> {
                    action = R.id.action_AFragment_to_BFragment_unique_animation
                    extras = null
                }
                AnimationType.SHARED_ELEMENT -> {
                    action = R.id.action_AFragment_to_BFragment_no_animation
                    extras = FragmentNavigatorExtras(
                        binding.mainText.let { it to it.transitionName },
                        binding.nextText.let { it to it.transitionName },
                        binding.nextNextText.let { it to it.transitionName },
                    )
                }
                else -> {
                    action = R.id.action_AFragment_to_BFragment_no_animation
                    extras = null
                }
            }
            findNavController().navigate(action, null, null, extras)
        }
    }
}