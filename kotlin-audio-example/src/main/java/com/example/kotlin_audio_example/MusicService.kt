package com.example.kotlin_audio_example

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import timber.log.Timber

class MusicService : MediaSessionService() {
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = null//mediaSession

    override fun onCreate() {
        super.onCreate()
    }
    override fun onDestroy() {
        //mediaSession = null
        super.onDestroy()
    }
}