package com.example.kotlin_audio_example

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.doublesymmetry.kotlinaudio.models.PlayerConfig
import com.doublesymmetry.kotlinaudio.players.QueuedAudioPlayer


class MusicService : MediaSessionService() {
    lateinit var player: QueuedAudioPlayer

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = player.mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onCreate() {
        player = QueuedAudioPlayer(
            this, playerConfig = PlayerConfig(
                interceptPlayerActionsTriggeredExternally = true,
                handleAudioBecomingNoisy = true,
                handleAudioFocus = true
            )
        )
        super.onCreate()
    }
    override fun onDestroy() {
        super.onDestroy()
    }


    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private val mBinder: IBinder = MusicBinder()

    inner class MusicBinder : Binder() {
        val service = this@MusicService
    }

}