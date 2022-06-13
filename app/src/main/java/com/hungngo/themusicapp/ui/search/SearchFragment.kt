package com.hungngo.themusicapp.ui.search

import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragment
import com.hungngo.themusicapp.databinding.FragmentSearchBinding
import com.hungngo.themusicapp.ui.search.adapter.TrackAdapter
import com.hungngo.themusicapp.utils.extension.hide
import com.hungngo.themusicapp.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModel()
    private val trackAdapter by lazy {
        TrackAdapter()
    }

    override fun init() {
        binding?.apply {
            recyclerTracks.adapter = trackAdapter
        }

        searchViewModel.apply {
            tracks.observe(viewLifecycleOwner) { searchTrackResponse ->
                trackAdapter.submitList(searchTrackResponse.tracks?.itemSearches)

                isLoading.observe(viewLifecycleOwner) { isShow ->
                    if (isShow) showProgressBar() else hideProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding?.progressSearch?.show()
    }

    private fun hideProgressBar() {
        binding?.progressSearch?.hide()
    }
}
