package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.local.CompanyEntity
import com.plcoding.stockmarketapp.domain.model.Company

fun CompanyEntity.toCompany(): Company {
    return Company(
        name = this.name,
        symbol = this.symbol,
        exchange = this.exchange
    )
}

fun Company.toCompanyEntity(): CompanyEntity {
    return CompanyEntity(
        name = this.name,
        symbol = this.symbol,
        exchange = this.exchange
    )
}