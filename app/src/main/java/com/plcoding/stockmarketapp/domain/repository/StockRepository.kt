package com.plcoding.stockmarketapp.domain.repository

import com.plcoding.stockmarketapp.domain.model.Company
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanies(
        fetchFromServer: Boolean,
        query: String
    ): Flow<Resource<List<Company>>>
}