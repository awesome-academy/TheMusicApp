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
    const val DELAY_SEARCH_TIME = 500L
    const val EXTRA_MUSIC = "com.hungngo.themusicapp.ACTION_MUSIC"

    const val ACTION_PLAY = "com.hungngo.musicapp.play_music"
    const val ACTION_PAUSE = "com.hungngo.musicapp.stop_music"
    const val ACTION_RESUME = "com.hungngo.musicapp.resume_music"
    const val ACTION_NEXT = "com.hungngo.musicapp.next_song"
    const val ACTION_PREVIOUS = "com.hungngo.musicapp.previous_song"
}
