package com.example.domain.usecase

import com.example.domain.either.Either
import com.example.domain.either.eitherFailure
import com.example.domain.either.eitherSuccess
import com.example.domain.either.onFailure
import com.example.domain.either.onSuccess
import com.example.domain.usecase.flow.FlowUseCase
import com.example.domain.repository.AppRepository
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUseCase @Inject
constructor(private val appRepository: AppRepository) :
    FlowUseCase<Unit, Either<String, String>>() { // TODO cambiar dato cuando sepamos que devolvemos

        override fun prepareFlow(input: Unit): Flow<Either<String, String>> = flow {
            appRepository.getData()
                .onSuccess {
                    emit(eitherSuccess(it) )
                }
                .onFailure { eitherFailure(it) }
    }
}