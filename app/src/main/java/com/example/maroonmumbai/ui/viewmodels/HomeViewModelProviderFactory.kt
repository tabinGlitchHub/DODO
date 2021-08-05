package com.example.maroonmumbai.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maroonmumbai.repository.HomeRepository

class HomeViewModelProviderFactory(
    private val homeRepository: HomeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}