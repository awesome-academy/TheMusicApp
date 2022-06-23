package com.hungngo.themusicapp.ui.favorite

import android.app.AlertDialog
import android.content.Context
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.FragmentFavoriteBinding
import com.hungngo.themusicapp.ui.main.MainViewModel
import com.hungngo.themusicapp.utils.Constant
import com.hungngo.themusicapp.utils.extension.showToast
import com.hungngo.themusicapp.utils.onClick.OnItemRecyclerViewClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment :
    BaseFragmentViewBinding<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    OnItemRecyclerViewClickListener<Int> {

    private val mainViewModel: MainViewModel by viewModel()
    private val adapter: FavoriteTrackAdapter by lazy { FavoriteTrackAdapter() }
    private var listTracks = listOf<Track>()
    private var listener: OnPlayLocal? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPlayLocal
    }

    override fun initView() {
        adapter.setListener(this)
        binding?.apply {
            recycle.adapter = adapter
        }
    }

    override fun initData() {
        mainViewModel.getAllTrack()
    }

    override fun bindData() {
        mainViewModel.apply {
            tracks.observe(viewLifecycleOwner) {
                listTracks = it
                adapter.submitList(it)
            }

            toastMessage.observe(viewLifecycleOwner) {
                context?.showToast(it)
            }
        }
    }

    override fun onItemClick(item: Int?) {
        item?.let { listener?.playLocalTrack(it, listTracks) }
    }

    override fun onItemLongClick(item: Int?) {
        item?.let {
            AlertDialog.Builder(context).apply {
                setTitle(getString(R.string.title_alert_delete))
                setMessage(getString(R.string.msg_alert_delete))
                setPositiveButton(Constant.TEXT_YES) { _, _ ->
                    listener?.onDeleteTrack(listTracks[it])
                    mainViewModel.deleteTrack(adapter.currentList[it])
                }
                setNegativeButton(Constant.TEXT_NO) { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    interface OnPlayLocal {
        fun playLocalTrack(trackPosition: Int, listTracks: List<Track>)
        fun onDeleteTrack(track: Track)
    }
}
