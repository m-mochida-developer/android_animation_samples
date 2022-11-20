package com.example.animation_samples.list_samples

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animation_samples.R
import com.example.animation_samples.databinding.FragmentSampleListBinding
import com.example.animation_samples.databinding.LayoutItemMenuListBinding

class SampleListFragment : Fragment(R.layout.fragment_sample_list) {
    private val viewModel: SampleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        val binding = FragmentSampleListBinding.bind(view)

        binding.menuList.let {
            it.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
            it.adapter = SampleListAdapter(Sample.values().toList()).apply {
                setOnItemClickListener { sample, view ->
                    transitionToPage(sample, view)
                }
            }
        }

        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun transitionToPage(sample: Sample, view: View) {
        viewModel.sample.value = sample
        val args: NavDirections
        val extras: Navigator.Extras?
        when (sample) {
            Sample.ANIMATION_NOTHING -> {
                args = SampleListFragmentDirections.actionSampleListFragmentToSampleItemFragmentNoAnimation(
                    sample = sample
                )
                extras = null
            }
            Sample.NAVIGATION_SLIDE -> {
                args = SampleListFragmentDirections.actionSampleListFragmentToSampleItemFragmentSlideAnimation(
                    sample = sample
                )
                extras = null
            }
            Sample.TRANSITION_SHARED_ELEMENT -> {
                args = SampleListFragmentDirections.actionSampleListFragmentToSampleItemFragmentNoAnimation(
                    sample = sample
                )
                extras = FragmentNavigatorExtras(
                    view to view.transitionName
                )
            }
        }
        if (extras == null) {
            findNavController().navigate(args)
        } else {
            findNavController().navigate(args, extras)
        }
    }

    enum class Sample(@StringRes val text: Int) {
        ANIMATION_NOTHING(R.string.AnimationNothing),
        NAVIGATION_SLIDE(R.string.NavigationSlide),
        TRANSITION_SHARED_ELEMENT(R.string.TransitionSharedElement),
    }

    private class SampleListAdapter(private val menuList: List<Sample>) :
        RecyclerView.Adapter<SampleListAdapter.ViewHolder>() {

        inner class ViewHolder(
            private val resources: Resources,
            private val binding: LayoutItemMenuListBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bindTo(sample: Sample) {
                binding.menu.text = resources.getString(sample.text)
                binding.root.transitionName = resources.getString(sample.text) + resources.getString(R.string.transition_name_item)
                binding.root.setOnClickListener {
                    itemClickListener?.onItemClick(sample, it)
                }
            }
        }

        fun interface ItemClickListener {
            fun onItemClick(menu: Sample, view: View)
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