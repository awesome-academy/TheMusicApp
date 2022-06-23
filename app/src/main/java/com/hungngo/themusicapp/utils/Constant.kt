package com.hungngo.themusicapp.utils

object Constant {
    const val BASE_URL = "https://spotify23.p.rapidapi.com"
    const val API_HOST = "spotify23.p.rapidapi.com"

    val LIST_FILTER = arrayOf("Tracks", "Artists", "PlayLists")
    const val SEARCH_TRACK_PARAM = "tracks"
    const val SEARCH_ARTIST_PARAM = "artists"
    const val SEARCH_PLAYLIST_PARAM = "playlists"
    const val BUNDLE_ID_TRACK = "idTrack"
    const val BUNDLE_ID_ALBUM = "idAlbum"
    const val BUNDLE_TRACK_POS = "trackPosition"
    const val BUNDLE_LIST_TRACKS = "listTracks"
    const val DELAY_SEARCH_TIME = 500L
    const val EXTRA_MUSIC = "com.hungngo.themusicapp.ACTION_MUSIC"

    const val ACTION_OPEN_ACTIVITY = "openActivity"
    const val ACTION_PLAY = "com.hungngo.musicapp.play_music"
    const val ACTION_PAUSE = "com.hungngo.musicapp.stop_music"
    const val ACTION_RESUME = "com.hungngo.musicapp.resume_music"
    const val ACTION_NEXT = "com.hungngo.musicapp.next_song"
    const val ACTION_PREVIOUS = "com.hungngo.musicapp.previous_song"

    const val ALBUM_ID_RANDOM_1 = "3nzuGtN3nXARvvecier4K0"

    const val INDEX_SIZE_DIFFERENT = 1

    const val MESSAGE_DELETE_SUCCESS = "Delete Successfully"
    const val TEXT_YES = "Yes"
    const val TEXT_NO = "No"
}
