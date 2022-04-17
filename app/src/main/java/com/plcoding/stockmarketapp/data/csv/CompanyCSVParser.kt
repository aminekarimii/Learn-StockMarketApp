package com.plcoding.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.domain.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyCSVParser @Inject constructor() : CSVParser<Company> {

    override suspend fun parse(stream: InputStream): List<Company> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { csvLine ->
                    val name = csvLine.getOrNull(0)
                    val symbol = csvLine.getOrNull(1)
                    val exchange = csvLine.getOrNull(2)
                    Company(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    stream.close()
                }
        }
    }
}