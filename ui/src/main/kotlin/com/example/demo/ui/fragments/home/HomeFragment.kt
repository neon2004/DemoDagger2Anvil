package com.example.demo.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.R
import com.example.demo.ui.compose.ComposeFragment
import com.example.demo.ui.di.DemoBindings
import com.example.demo.ui.di.singletonBindings
import com.example.demo.ui.di.viewmodels.ViewModelFactory
import javax.inject.Inject


class HomeFragment : ComposeFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        singletonBindings<DemoBindings>().inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity?.applicationContext as MyApp)
//            .appComponents
//            .inject(this)
    }

    @Composable
    override fun UI() {
        val data = viewModel.data.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            with(data.value) {
                when (this) {
                    HomeViewModel.DataState.Loading -> {
                        Text(text = "cargando....")
                    }

                    HomeViewModel.DataState.HideLoading -> {

                    }

                    HomeViewModel.DataState.DataError -> {
                        Text(text = "ERROR")
                    }

                    HomeViewModel.DataState.NetworkError -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_network_connection),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is HomeViewModel.DataState.DataSuccessful -> {
                        Text(text = this@with.result)
                        Button(onClick = { HomeFragmentDirections.actionNavHomeToNavDetail("Destino") }) {
                            Text(text = "Click para ir a detalle")
                        }
                    }

                }
            }
        }
    }
}

