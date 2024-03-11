package com.example.demo.data.repository

import com.example.demo.data.datasource.RemoteDataSource
import com.example.domain.ServiceError
import com.example.domain.either.Either
import com.example.domain.either.eitherSuccess
import com.example.domain.either.map
import com.example.domain.repository.AppRepository
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(Singleton::class)
class AppRepositoryImpl
@Inject
constructor(private val remoteDataSource: RemoteDataSource) : AppRepository {
    override suspend fun getData(): Either<String, ServiceError> {
        return remoteDataSource.getRemoteData().map(
            onSuccess = {it},
            onFailure = {it}
        )
    }
}