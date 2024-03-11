package com.example.demo.data.datasource

import com.example.demo.data.api.ApiService
import com.example.demo.data.api.RetrofitServiceRequest
import com.example.domain.ServiceError
import com.example.domain.ServicesExecutor
import com.example.domain.either.Either
import com.example.domain.either.map
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import javax.inject.Singleton

@ContributesBinding(Singleton::class)
class RemoteDataSourceImpl
@Inject
constructor(
    private val apiService: ApiService,
    private val servicesExecutor: ServicesExecutor,
) : RemoteDataSource {
    override suspend fun getRemoteData(): Either<String, ServiceError> {
        return Either.Success("OK")/*servicesExecutor
            .execute(
                RetrofitServiceRequest(
                    apiService.getData("")
                )
            ).map(
                onSuccess = {response -> response.data},
                onFailure = {it}
            )*/
    }
}