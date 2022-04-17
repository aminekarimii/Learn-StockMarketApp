package com.plcoding.stockmarketapp.presentation.companylist

sealed class CompanyEvents {
    object Refresh : CompanyEvents()
    data class OnSearchQueryChange(val query: String) : CompanyEvents()
}
