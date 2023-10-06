package com.adesso.movee.scene.cinemas

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentCinemasBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CinemasFragment : BaseFragment<CinemasViewModel, FragmentCinemasBinding>() {

    override val layoutId: Int get() = R.layout.fragment_cinemas

    override fun initialize() {
        super.initialize()
        if (locationPermission) {
            viewModel.getLocation()
        }

        binder.layout.setContent {
            CinemasScreen(viewModel = viewModel)
        }
    }


    private val locationPermission by lazy {
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}