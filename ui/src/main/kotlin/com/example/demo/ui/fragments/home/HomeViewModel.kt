package com.example.demo.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.ui.di.viewmodels.ViewModelKey
import com.example.domain.either.onFailure
import com.example.domain.either.onSuccess
import com.example.domain.usecase.DataUseCase
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@ContributesMultibinding(Singleton::class, boundType = ViewModel::class)
@ViewModelKey(HomeViewModel::class)
class HomeViewModel @Inject
constructor(private val dataUseCase: DataUseCase,
//    private val internetUtil: InternetUtil
): ViewModel( ) {

    private val _data: MutableStateFlow<DataState> = MutableStateFlow(DataState.Loading)
    val data: StateFlow<DataState> = _data.asStateFlow()

//    private val isInternetOn = InternetUtil.isInternetOn()

    init {
//        if(isInternetOn){
            dataUseCase.prepare(Unit)
                .onEach { result -> /*_data.emit(DataState.DataSuccessful(result))*/
                    viewModelScope.launch {
                        delay(5000)
                        result.onSuccess { _data.emit(DataState.DataSuccessful(it)) }.onFailure { _data.emit(
                            DataState.DataError
                        ) }
                    }}
                .catch {
                    _data.emit(DataState.DataError)
                }
                .onCompletion { _data.emit(DataState.HideLoading) }
                .launchIn(viewModelScope)
//        }else{
//            viewModelScope.launch { _data.value/*emit(*/ = DataState.NetworkError }
//        }
    }

    sealed interface DataState {
        data object Loading : DataState
        data object HideLoading : DataState
        data object NetworkError : DataState
        data object DataError : DataState
        data class DataSuccessful(val result: String) : DataState
    }
}