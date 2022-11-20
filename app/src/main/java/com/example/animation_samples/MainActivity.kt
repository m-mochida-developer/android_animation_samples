package com.example.animation_samples

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animation_samples.databinding.ActivityMainBinding
import com.example.animation_samples.databinding.LayoutItemMenuListBinding
import com.example.animation_samples.list_samples.SampleActivity
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity.AnimationType
import com.example.animation_samples.navigation_animation.NavigationAnimationActivity.Companion.INTENT_KEY_ANIMATION_TYPE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuList.let {
            it.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager(this).orientation))
            it.adapter = MenuListAdapter(Menu.values().toList()).apply {
                setOnItemClickListener { menu ->
                    transitionToPage(menu)
                }
            }
        }
    }

    private fun transitionToPage(menu: Menu) {
        val intent = when (menu) {
            Menu.SAMPLE -> {
                Intent(this, SampleActivity::class.java)
            }
            Menu.ANIMATION_NOTHING -> {
                Intent(this, NavigationAnimationActivity::class.java).apply {
                    putExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType.NOTHING)
                }
            }
            Menu.NAVIGATION_SLIDE -> {
                Intent(this, NavigationAnimationActivity::class.java).apply {
                    putExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType.SLIDE)
                }
            }
            Menu.NAVIGATION_UNIQUE -> {
                Intent(this, NavigationAnimationActivity::class.java).apply {
                    putExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType.UNIQUE)
                }
            }
            Menu.TRANSITION -> {
                Intent(this, NavigationAnimationActivity::class.java).apply {
                    putExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType.TRANSITION)
                }
            }
            Menu.TRANSITION_SHARED_ELEMENT -> {
                Intent(this, NavigationAnimationActivity::class.java).apply {
                    putExtra(INTENT_KEY_ANIMATION_TYPE, AnimationType.SHARED_ELEMENT)
                }
            }
        }
        startActivity(intent)
    }

    private enum class Menu(@StringRes val text: Int) {
        SAMPLE(R.string.Sample),
        ANIMATION_NOTHING(R.string.AnimationNothing),
        NAVIGATION_SLIDE(R.string.NavigationSlide),
        NAVIGATION_UNIQUE(R.string.NavigationUnique),
        TRANSITION(R.string.Transition),
        TRANSITION_SHARED_ELEMENT(R.string.TransitionSharedElement),
    }

    private class MenuListAdapter(private val menuList: List<Menu>) :
        RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

        inner class ViewHolder(
            private val resources: Resources,
            private val binding: LayoutItemMenuListBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bindTo(menu: Menu) {
                binding.menu.text = resources.getText(menu.text)
                binding.root.setOnClickListener {
                    itemClickListener?.onItemClick(menu)
                }
            }
        }

        fun interface ItemClickListener {
            fun onItemClick(menu: Menu)
        }

        private var itemClickListener: ItemClickListener? = null

        fun setOnItemClickListener(listener: ItemClickListener) {
            itemClickListener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                parent.resources,
                LayoutItemMenuListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindTo(menuList[position])
        }

        override fun getItemCount(): Int = menuList.size
    }
}