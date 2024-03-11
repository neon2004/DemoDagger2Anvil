package com.example.domain.repository

import com.example.domain.ServiceError
import com.example.domain.either.Either


interface AppRepository {
    suspend fun getData() : Either<String, ServiceError> //TODO cambiar con los datos que se pidan
}