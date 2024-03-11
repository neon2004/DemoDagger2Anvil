package com.example.demo.data.datasource

import com.example.domain.ServiceError
import com.example.domain.either.Either

interface RemoteDataSource {
    suspend fun getRemoteData(): Either<String, ServiceError> //TODO cambiar con los datos que se tenga que obten
}