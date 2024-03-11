package com.example.demo.ui.compose

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import com.example.demo.ui.compose.theme.DemoTheme

abstract class ComposeFragment : Fragment(), ComposeFragmentDelegate {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = createComposeView(inflater.context)
}
class A : Fragment(){}

interface ComposeFragmentDelegate {
    @Composable
    fun UI()

    fun createComposeView(context: Context) =
        ComposeView(context).apply {
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DemoTheme {
                    UI()
                }
            }
        }
}
