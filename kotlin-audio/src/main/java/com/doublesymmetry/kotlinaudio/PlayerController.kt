package com.doublesymmetry.kotlinaudio

import android.content.Context
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class PlayerController(context: Context, sessionToken: SessionToken) {
    private var controllerFuture: ListenableFuture<MediaBrowser>
    init {
        controllerFuture =
            MediaBrowser.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener({
            // MediaController is available here with controllerFuture.get()
        }, MoreExecutors.directExecutor())
    }

    fun controller(): MediaBrowser = controllerFuture.get()

}