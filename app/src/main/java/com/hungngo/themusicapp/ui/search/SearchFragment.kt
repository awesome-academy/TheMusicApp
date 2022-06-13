package com.hungngo.themusicapp.ui.search

import android.app.AlertDialog
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragment
import com.hungngo.themusicapp.data.model.Data
import com.hungngo.themusicapp.databinding.FragmentSearchBinding
import com.hungngo.themusicapp.ui.search.adapter.ArtistListAdapter
import com.hungngo.themusicapp.ui.search.adapter.PlayListListAdapter
import com.hungngo.themusicapp.ui.search.adapter.TrackAdapter
import com.hungngo.themusicapp.utils.extension.hide
import com.hungngo.themusicapp.utils.extension.show
import com.hungngo.themusicapp.utils.Constant
import com.hungngo.themusicapp.utils.extension.showToast
import com.hungngo.themusicapp.utils.onClick.OnClickTrackListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search), OnClickTrackListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private val trackAdapter by lazy { TrackAdapter() }
    private val artistAdapter by lazy { ArtistListAdapter() }
    private val playListAdapter by lazy { PlayListListAdapter() }
    private var searchType = "tracks"

    override fun init() {
        trackAdapter.setOnClickTrackListener(this)
        binding?.apply {
            recyclerTracks.adapter = trackAdapter
            recyclerArtists.adapter = artistAdapter
            recyclerPlaylists.adapter = playListAdapter
        }

        setOnClickListener()

        observerItem()
    }

    private fun setOnClickListener() {
        binding?.apply {
            var job: Job? = null
            editTextSearch.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    editable?.let {
                        delay(Constant.DELAY_SEARCH_TIME)
                        if (editable.toString().isNotEmpty()) {
                            searchViewModel.searchItems(editable.toString(), searchType)
                        }
                    }
                }
            }

            buttonFilter.setOnClickListener { showDialog() }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.title_search_dialog))
            setItems(Constant.LIST_FILTER) { _, which ->
                searchType = Constant.LIST_FILTER[which].trim().lowercase()
                context.showToast(searchType)
            }
            show()
        }
    }

    private fun observerItem() {
        searchViewModel.searchResponse.observe(viewLifecycleOwner) {
            when (searchType) {
                Constant.SEARCH_TRACK_PARAM -> {
                    checkRecyclerView(searchType)
                    trackAdapter.submitList(it.itemsInSearch?.itemSearches)
                }
                Constant.SEARCH_ARTIST_PARAM -> {
                    checkRecyclerView(searchType)
                    artistAdapter.submitList(it.itemsInSearch?.itemSearches)
                }
                Constant.SEARCH_PLAYLIST_PARAM -> {
                    checkRecyclerView(searchType)
                    playListAdapter.submitList(it.itemsInSearch?.itemSearches)
                }
            }
        }

        searchViewModel.apply {
            searchResponse.observe(viewLifecycleOwner) { searchTrackResponse ->
                trackAdapter.submitList(searchTrackResponse.itemsInSearch?.itemSearches)

                isLoading.observe(viewLifecycleOwner) { isShow ->
                    if (isShow) showProgressBar() else hideProgressBar()
                }
            }
        }
    }

    private fun checkRecyclerView(type: String) {
        when (type) {
            Constant.SEARCH_TRACK_PARAM -> {
                binding?.recyclerTracks?.visibility = View.VISIBLE
                binding?.recyclerPlaylists?.visibility = View.GONE
                binding?.recyclerArtists?.visibility = View.GONE
            }
            Constant.SEARCH_ARTIST_PARAM -> {
                binding?.recyclerArtists?.visibility = View.VISIBLE
                binding?.recyclerPlaylists?.visibility = View.GONE
                binding?.recyclerTracks?.visibility = View.GONE
            }
            Constant.SEARCH_PLAYLIST_PARAM -> {
                binding?.recyclerPlaylists?.visibility = View.VISIBLE
                binding?.recyclerTracks?.visibility = View.GONE
                binding?.recyclerArtists?.visibility = View.GONE
            }
        }
    }

    private fun showProgressBar() {
        binding?.progressSearch?.show()
    }

    private fun hideProgressBar() {
        binding?.progressSearch?.hide()
    }

    override fun onItemClick(data: Data) {
        val bundle = bundleOf(Constant.BUNDLE_DATA to data)
        findNavController().navigate(
            R.id.action_searchFragment_to_playlistFragment,
            bundle
        )
    }
}
