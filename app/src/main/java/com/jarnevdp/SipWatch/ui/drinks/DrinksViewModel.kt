package com.jarnevdp.SipWatch.ui.drinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrinksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drinks Fragment"
    }
    val text: LiveData<String> = _text
}