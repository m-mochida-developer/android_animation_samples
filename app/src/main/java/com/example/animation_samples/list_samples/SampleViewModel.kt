package com.example.animation_samples.list_samples

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animation_samples.list_samples.SampleListFragment.Sample

class SampleViewModel : ViewModel() {
    val sample: MutableLiveData<Sample> = MutableLiveData(Sample.ANIMATION_NOTHING)
}