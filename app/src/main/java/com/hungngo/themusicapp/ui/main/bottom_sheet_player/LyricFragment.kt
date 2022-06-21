package com.hungngo.themusicapp.ui.main.bottom_sheet_player

import androidx.core.os.bundleOf
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.data.model.Lyric
import com.hungngo.themusicapp.databinding.FragmentLyricBinding
import com.hungngo.themusicapp.ui.main.bottom_sheet_player.BottomSheetPlayer

const val BUNDLE_LYRIC = "bundle_lyric"

class LyricFragment : BaseFragmentViewBinding<FragmentLyricBinding>(FragmentLyricBinding::inflate) {

    private var lyric: Lyric? = null

    override fun initView() {
    }

    override fun initData() {
        lyric = arguments?.getParcelable(BUNDLE_LYRIC) as Lyric?
        lyric?.let { updateLyric(it) }
    }

    override fun bindData() {
    }

    private fun updateLyric(lyric: Lyric) {
        val trackLyric = StringBuilder()
        lyric.lines?.forEachIndexed { index, line ->
            if (index < lyric.lines?.size?.minus(BottomSheetPlayer.INDEX_SIZE_DIFF) ?: 0) {
                trackLyric.append(line.words + "\n")
            } else {
                trackLyric.append(line.words)
            }
        }
        binding?.textLyric?.text = trackLyric
    }


    companion object {
        fun newInstance(lyric: Lyric?) =
            LyricFragment().apply {
                arguments = bundleOf(BUNDLE_LYRIC to lyric)
            }
    }
}
