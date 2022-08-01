package com.pemeluksenja.storyappdicoding.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pemeluksenja.storyappdicoding.viewmodel.MapsViewModel

class MapsViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: MapsViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): MapsViewModelFactory {
            if (INSTANCE == null) {
                synchronized(MapsViewModelFactory::class.java) {
                    INSTANCE = MapsViewModelFactory(application)
                }
            }
            return INSTANCE as MapsViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}