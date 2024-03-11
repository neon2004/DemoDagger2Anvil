package com.example.demo.ui.fragments.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.demo.ui.compose.ComposeFragment

class DetailFragment : ComposeFragment() {

    val args: DetailFragmentArgs by navArgs()

    @Composable
    override fun UI() {
        Text(text = args.detailArgument)
    }
}