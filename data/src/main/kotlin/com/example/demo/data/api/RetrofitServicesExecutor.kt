package com.example.demo.data.api

import com.example.domain.ServiceError
import com.example.domain.ServiceRequest
import com.example.domain.ServiceResponse
import com.example.domain.ServicesExecutor
import com.example.domain.either.Either
import com.example.domain.either.eitherFailure
import com.example.domain.either.eitherSuccess
import com.example.domain.error.ServiceException
import com.google.gson.JsonParseException
import com.squareup.anvil.annotations.ContributesBinding
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(Singleton::class)
class RetrofitServicesExecutor
@Inject
constructor(
//    private val networkManager: NetworkManager,
) : ServicesExecutor {

    private inline val logTag
        get() = javaClass.simpleName

    override fun <T> execute(request: ServiceRequest<T>): Either<ServiceResponse<T>, ServiceError> {
        if (request !is RetrofitServiceRequest<T>)
            throw IllegalArgumentException("RetrofitServicesExecutor only accepts RetrofitServiceRequest")
        val url = request.retrofitCall.request().url().toString()
        return try {

//            networkManager.checkConnectivity()

            val response = request.retrofitCall.execute()
            if (response.isSuccessful) {
                eitherSuccess(ServiceResponse(response.body(), response.code(), response.headers().toMultimap()))
            } else {
                eitherFailure(ServiceError(response.errorBody()?.string(), response.code(), response.headers().toMultimap()))
            }
        } catch (ex: JsonParseException) {

            throw ServiceException(ex.message ?: "", url)
        } catch (ex: IOException) {

            throw ServiceException(ex.message ?: "", url)
        }
    }
}

class RetrofitServiceRequest<T>(val retrofitCall: retrofit2.Call<T>) : ServiceRequest<T>
