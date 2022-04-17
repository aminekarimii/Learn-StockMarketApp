package com.plcoding.stockmarketapp.domain.repository

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.local.AppDatabase
import com.plcoding.stockmarketapp.data.mapper.toCompany
import com.plcoding.stockmarketapp.data.mapper.toCompanyEntity
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.Company
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.commons.lang3.StringUtils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val database: AppDatabase,
    val companyCSVParser: CSVParser<Company>
) : StockRepository {

    private val dao = database.stockDao

    override suspend fun getCompanies(
        fetchFromServer: Boolean,
        query: String
    ): Flow<Resource<List<Company>>> {
        return flow {
            emit(Resource.Loading(true))

            val companies = dao.searchCompanies(query)
            emit(Resource.Success(
                data = companies.map {
                    it.toCompany()
                }
            ))

            val isDatabaseEmpty = query.isBlank() && companies.isEmpty()
            val shouldLoadFromLocal = !isDatabaseEmpty && !fetchFromServer
            if (shouldLoadFromLocal) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteCompanies = try {
                val response = api.getListing()
                companyCSVParser.parse(response.byteStream())

            } catch (e: IOException) {
                emit(Resource.Error("IO exception"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error("HttpException"))
                null
            }

            remoteCompanies?.let { remoteListCompanies ->
                dao.clearCompanies()
                dao.insertCompaniesList(
                    remoteListCompanies.map {
                        it.toCompanyEntity()
                    }
                )

                emit(
                    Resource.Success(
                        data = dao.searchCompanies(StringUtils.EMPTY)
                            .map {
                                it.toCompany()
                            }
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }
}