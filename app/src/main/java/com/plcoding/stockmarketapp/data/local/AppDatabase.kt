package com.plcoding.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val stockDao: StockDao
}