package com.plcoding.stockmarketapp.presentation.companylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.stockmarketapp.domain.model.CompanyState
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyState())

    fun onEvent(event: CompanyEvents) {
        when (event) {
            is CompanyEvents.Refresh -> {
                getCompanies(forceRefresh = true)
            }
            is CompanyEvents.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                getCompanies()
            }
        }
    }

    private fun getCompanies(
        forceRefresh: Boolean = false,
        searchQuery: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            stockRepository
                .getCompanies(forceRefresh, searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(companies = it)
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }
}