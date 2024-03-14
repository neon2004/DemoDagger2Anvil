package com.example.domain

import com.example.domain.either.Either
import com.example.domain.error.ServiceException
import kotlin.jvm.Throws

interface ServicesExecutor {

    @Throws( ServiceException::class)
    fun <T> execute(request: ServiceRequest<T>): Either<ServiceResponse<T>, ServiceError>
}

interface ServiceRequest<T>

open class ServiceResponse<T>(
    private val optData: T?,
    val statusCode: Int,
    val headers: Map<String, List<String>>,
) {
    val data: T
        get() = optData!!
}

open class ServiceError(
    val description: String? = null,
    val statusCode: Int,
    val headers: Map<String, List<String>>,
)