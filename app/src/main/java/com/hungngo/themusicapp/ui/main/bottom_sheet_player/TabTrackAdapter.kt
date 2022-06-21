package com.hungngo.themusicapp.ui.main.bottom_sheet_player

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hungngo.themusicapp.data.model.Lyric

const val FRAGMENT_TRACK_POS = 0
const val FRAGMENT_LYRIC_POS = 1

class TabTrackAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var trackName: String? = ""
    private var trackArtist: String? = ""
    private var lyric: Lyric? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            FRAGMENT_TRACK_POS -> {
                return TrackFragment.newInstance(trackName, trackArtist)
            }
            FRAGMENT_LYRIC_POS -> {
                return LyricFragment.newInstance(lyric)
            }
        }
        return TrackFragment.newInstance(trackName, trackArtist)
    }

    fun setTrackInfor(trackName: String, trackArtist: String) {
        this.trackName = trackName
        this.trackArtist = trackArtist
        notifyItemChanged(FRAGMENT_TRACK_POS)
    }

    fun setLyric(lyric: Lyric) {
        this.lyric = lyric
        notifyItemChanged(FRAGMENT_LYRIC_POS)
    }
}
