package com.example.animation_samples.navigation_animation

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.animation_samples.databinding.ActivityNavigationAnimationBinding

class NavigationAnimationActivity : AppCompatActivity() {
    private val viewModel: AnimationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavigationAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(INTENT_KEY_ANIMATION_TYPE) as AnimationType
        }

        type?.let { viewModel.animationType.value = it }

        title = type?.name
    }

    enum class AnimationType {
        NOTHING,
        SLIDE,
        UNIQUE,
        TRANSITION,
        SHARED_ELEMENT,
    }

    companion object {
        const val INTENT_KEY_ANIMATION_TYPE = "INTENT_KEY_ANIMATION_TYPE"
    }
}