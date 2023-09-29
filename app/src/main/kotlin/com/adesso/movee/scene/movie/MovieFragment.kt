package com.adesso.movee.scene.movie

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentMovieBinding
import com.adesso.movee.internal.extension.collectFlow
import com.adesso.movee.internal.extension.toast
import com.adesso.movee.internal.util.addAppBarStateChangeListener
import com.adesso.movee.uimodel.MovieUiModel
import com.adesso.movee.uimodel.ShowUiModel
import com.adesso.movee.widget.nowplayingshow.NowPlayingShowCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment :
    BaseFragment<MovieViewModel, FragmentMovieBinding>(),
    PopularMovieCallback,
    NowPlayingShowCallback {

    override val layoutId: Int get() = R.layout.fragment_movie

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewModel.navigateCinemasFragment()
    }

    override fun initialize() {
        super.initialize()

        binder.popularMovieAdapter = PopularMovieListAdapter(popularMovieCallback = this)
        binder.layoutShowHeader.nowPlayingShowCallback = this
        binder.layoutShowHeader.appBarShow.addAppBarStateChangeListener { _, state ->
            viewModel.appbarStateChanged(state)
        }
        binder.layoutShowHeader.headerFabButton.isVisible = true
        binder.layoutShowHeader.headerFabButton.setOnClickListener {
            requestLocationPermission()
        }
        setShouldRefreshPagingListener()
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    override fun onPopularMovieClick(movie: MovieUiModel) {
        viewModel.onPopularMovieClick(movie)
    }

    override fun onNowPlayingShowClick(show: ShowUiModel) {
        viewModel.onNowPlayingShowClick(show)
    }

    private fun setShouldRefreshPagingListener() {
        collectFlow(viewModel.shouldRefreshPaging) {
            if (it) {
                binder.popularMovieAdapter?.refresh()
                requireContext().toast(getString(R.string.common_paging_list_refreshed_message))
                binder.recyclerViewPopularMovies.scrollToPosition(0)
            }
        }
    }
}
