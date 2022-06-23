package com.hungngo.themusicapp.ui.home

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.databinding.FragmentHomeBinding
import com.hungngo.themusicapp.ui.home.adapter.HomeAlbumAdapter
import com.hungngo.themusicapp.ui.home.adapter.HomeTrackAdapter
import com.hungngo.themusicapp.utils.Constant
import com.hungngo.themusicapp.utils.onClick.OnClickTrackListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragmentViewBinding<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnClickTrackListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeTrackAdapter by lazy { HomeTrackAdapter() }
    private val homeAlbumAdapter by lazy { HomeAlbumAdapter() }
    private var idAlbum = ""

    private fun observerItem() {
        homeViewModel.albums.observe(viewLifecycleOwner) {
            homeAlbumAdapter.submitList(it.albums)
            homeTrackAdapter.submitList(it.albums?.first()?.tracks?.items)
            idAlbum = it.albums?.first()?.id.toString()
        }
    }

    override fun initView() {
        binding?.apply {
            recyclerPlaylist.adapter = homeAlbumAdapter
            recyclerTracks.adapter = homeTrackAdapter
        }
    }

    override fun initData() {
        homeTrackAdapter.setOnClickTrackListener(this)
        homeAlbumAdapter.setOnClickTrackListener(this)
    }

    override fun bindData() {
        observerItem()
    }

    override fun onItemClick(idTrack: String, idAlbum: String) {
        val bundle = bundleOf(
            Constant.BUNDLE_ID_TRACK to idTrack,
            Constant.BUNDLE_ID_ALBUM to this.idAlbum
        )
        findNavController().navigate(
            R.id.action_homeFragment_to_playlistFragment,
            bundle
        )
    }
}
