package com.hungngo.themusicapp.ui.main.bottom_sheet_player

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.data.model.Lyric
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.BottomSheetPlayerBinding
import com.hungngo.themusicapp.service.MusicService
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetPlayer : BottomSheetDialogFragment(), View.OnClickListener {

    private var binding: BottomSheetPlayerBinding? = null
    private var musicService: MusicService? = null
    private var track: Track? = null
    private var bottomSheetPlayer: BottomSheetDialog? = null
    private var lyric: Lyric? = null
    private var tabTrackAdapter: TabTrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        track?.let { setTrack(it) }
        setOnClick()
        val bottomSheetBehavior = BottomSheetBehavior.from(binding?.root?.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        setUpTabLayoutAndViewPager()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetPlayer = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        binding?.root?.let { bottomSheetPlayer?.setContentView(it) }
        isCancelable = false
        bottomSheetPlayer?.setCancelable(false)
        return bottomSheetPlayer as BottomSheetDialog
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.image_next_song -> {
                musicService?.nextTrack()
                updateImageResumeOrPlay(true)
            }
            R.id.image_previous_song -> {
                musicService?.previousTrack()
                updateImageResumeOrPlay(true)
            }
            R.id.image_play_song -> {
                resumeOrPauseTrack()
            }
            R.id.button_back -> bottomSheetPlayer?.dismiss()
            R.id.button_favorite -> downloadFileFromUrl()
        }
    }

    private fun setUpTabLayoutAndViewPager() {
        val tabLayout = binding?.tabLayoutTrack
        val viewPager = binding?.viewpager2Track
        if (isAdded) {
            tabTrackAdapter = TabTrackAdapter(this)
            viewPager?.adapter = tabTrackAdapter
            tabTrackAdapter?.apply {
                lyric?.let { setLyric(it) }

                track?.name?.let { trackName ->
                    track?.artists?.first()?.name?.let { trackArtist ->
                        setTrackInfor(trackName, trackArtist)
                    }
                }
            }
            if (tabLayout != null && viewPager != null) {
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                }.attach()
            }
        }
    }

    private fun bindData(track: Track) {
        binding?.apply {
            seekBarTrack.max = musicService?.getDuration()?.div(MILIS_TO_SECOND) ?: TIME_DEFAULT
            textDuration.text =
                SimpleDateFormat(
                    TIME_PATTERN,
                    Locale.getDefault()
                ).format(musicService?.getDuration())
            updateImageResumeOrPlay(true)
            seekBarTrack.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(p0: SeekBar?) {
                    musicService?.seekToPosition(p0?.progress)
                }

                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
            })
        }
    }

    fun setService(musicService: MusicService) {
        this.musicService = musicService
    }

    fun setTrack(track: Track) {
        this.track = track
        bindData(track)
    }

    fun setLyric(lyric: Lyric) {
        this.lyric = lyric
        setUpTabLayoutAndViewPager()
    }

    fun setProgress(progress: Int) {
        binding?.apply {
            seekBarTrack.progress = progress.div(MILIS_TO_SECOND)
            textElapsed.text = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
                .format(musicService?.getCurrentProgress())
        }
    }

    private fun downloadFileFromUrl() {

    }

    private fun resumeOrPauseTrack() {
        musicService?.apply {
            if (isPlaying() == true) {
                updateImageResumeOrPlay(false)
                pauseTrack()
            } else {
                updateImageResumeOrPlay(true)
                resumeTrack()
            }
        }
    }

    private fun updateImageResumeOrPlay(isPlaying: Boolean) {
        if (isPlaying) binding?.imagePlaySong?.setImageResource(R.drawable.ic_pause_40)
        else binding?.imagePlaySong?.setImageResource(R.drawable.ic_resume)
    }

    private fun setOnClick() {
        binding?.apply {
            imageNextSong.setOnClickListener(this@BottomSheetPlayer)
            imagePlaySong.setOnClickListener(this@BottomSheetPlayer)
            imagePreviousSong.setOnClickListener(this@BottomSheetPlayer)
            buttonBack.setOnClickListener(this@BottomSheetPlayer)
            buttonFavorite.setOnClickListener(this@BottomSheetPlayer)
        }
    }

    companion object {
        const val INDEX_SIZE_DIFF = 2
        const val TIME_DEFAULT = 0
        const val TIME_PATTERN = "mm:ss"
        const val MILIS_TO_SECOND = 1000
        private var instance: BottomSheetPlayer? = null
        fun getInstance() = instance ?: BottomSheetPlayer().also { instance = it }
    }
}
