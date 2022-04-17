package com.plcoding.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface StockDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertCompaniesList(
        companies: List<CompanyEntity>
    )

    @Query("DELETE FROM company_entity")
    suspend fun clearCompanies()

    @Query(
        """
            SELECT *
                FROM company_entity 
                WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
                """
    )
    suspend fun searchCompanies(query: String): List<CompanyEntity>

}