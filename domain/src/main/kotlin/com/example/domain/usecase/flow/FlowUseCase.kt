package com.example.domain.usecase.flow

import androidx.annotation.CheckResult
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<T, R>() {

    @CheckResult
    fun prepare(input: T) = prepareFlow(input).flowOn(Dispatchers.IO)

    /**
     * GenericFlowUseCase Return a [Flow] that will be executed in the specified [CoroutineContext] ([Dispatchers.IO] by default).
     *
     * There's no need to call to [flowOn] in subclasses.
     */
    protected abstract fun prepareFlow(input: T): Flow<R>
}
