package com.plcoding.stockmarketapp.data.local

import androidx.room.Database

@Database(
    entities = [CompanyEntity::class],
    version = 1
)

abstract class AppDatabase {
    abstract val stockDao: StockDao
}