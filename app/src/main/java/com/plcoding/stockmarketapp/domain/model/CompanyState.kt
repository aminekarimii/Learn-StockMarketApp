package com.plcoding.stockmarketapp.domain.model

import org.apache.commons.lang3.StringUtils

data class CompanyState(
    val companies: List<Company> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = StringUtils.EMPTY
)