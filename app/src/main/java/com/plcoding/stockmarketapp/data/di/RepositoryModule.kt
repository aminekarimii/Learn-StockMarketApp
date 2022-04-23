package com.plcoding.stockmarketapp.data.di

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.CompanyCSVParser
import com.plcoding.stockmarketapp.domain.model.Company
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.domain.repository.StockRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCompanyCSVParser(
        companyCSVParser: CompanyCSVParser
    ): CSVParser<Company>

    @Binds
    @Singleton
    abstract fun provideStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}