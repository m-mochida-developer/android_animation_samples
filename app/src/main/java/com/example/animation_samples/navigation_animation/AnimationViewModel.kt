package com.example.animation_samples.navigation_animation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity.AnimationType

class AnimationViewModel : ViewModel() {
    val animationType : MutableLiveData<AnimationType> = MutableLiveData()
}