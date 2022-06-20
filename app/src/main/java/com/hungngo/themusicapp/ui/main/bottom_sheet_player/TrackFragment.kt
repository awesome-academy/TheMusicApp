package com.hungngo.themusicapp.ui.main.bottom_sheet_player

import android.os.Bundle
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.databinding.FragmentTrackBinding

const val BUNDLE_TRACK_NAME = "track_name"
const val BUNDLE_TRACK_ARTIS = "track_artist"

class TrackFragment : BaseFragmentViewBinding<FragmentTrackBinding>(FragmentTrackBinding::inflate) {

    private var trackName: String = ""
    private var trackArtist: String = ""

    override fun initView() {

    }

    override fun initData() {
        trackName = arguments?.getString(BUNDLE_TRACK_NAME).toString()
        trackArtist = arguments?.getString(BUNDLE_TRACK_ARTIS).toString()
    }

    override fun bindData() {
        binding?.textTrackName?.text = trackName
        binding?.textArtist?.text = trackArtist
    }

    companion object {
        @JvmStatic
        fun newInstance(trackName: String?, trackArtist: String?) =
            TrackFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_TRACK_NAME, trackName)
                    putString(BUNDLE_TRACK_ARTIS, trackArtist)
                }
            }
    }
}
