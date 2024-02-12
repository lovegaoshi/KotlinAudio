@file: UnstableApi package com.example.kotlin_audio_example

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.SessionToken
import com.doublesymmetry.kotlinaudio.PlayerController
import com.doublesymmetry.kotlinaudio.models.DefaultAudioItem
import com.doublesymmetry.kotlinaudio.models.MediaType
import com.example.kotlin_audio_example.ui.component.PlayerControls
import com.example.kotlin_audio_example.ui.component.TrackDisplay
import com.example.kotlin_audio_example.ui.theme.KotlinAudioTheme
import timber.log.Timber


class MainActivity : ComponentActivity() {
    private lateinit var controller: PlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        val sessionToken =
            SessionToken(this, ComponentName(this, MusicService::class.java))
        controller = PlayerController(this, sessionToken)
        val mediaController = controller.controller()
        val mediaItem =
            MediaItem.Builder()
                .setMediaId("media-1")
                .setUri("https://rntp.dev/example/Longing.mp3")
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist("David Bowie")
                        .setTitle("Heroes")
                        .setArtworkUri(Uri.parse("https://rntp.dev/example/Longing.jpeg"))
                        .build()
                )
                .build()

        mediaController.setMediaItem(mediaItem)
        mediaController.prepare()
        mediaController.play()
    }


    companion object {
        val tracks = listOf(
            DefaultAudioItem(
                "https://rntp.dev/example/Longing.mp3",
                MediaType.DEFAULT,
                title = "Longing",
                artwork = "https://rntp.dev/example/Longing.jpeg",
                artist = "David Chavez",
                duration = 143 * 1000,
            ),
            DefaultAudioItem(
                "https://rntp.dev/example/Soul%20Searching.mp3",
                MediaType.DEFAULT,
                title = "Soul Searching (Demo)",
                artwork = "https://rntp.dev/example/Soul%20Searching.jpeg",
                artist = "David Chavez",
                duration = 77 * 1000,
            ),
            DefaultAudioItem(
                "https://rntp.dev/example/Lullaby%20(Demo).mp3",
                MediaType.DEFAULT,
                title = "Lullaby (Demo)",
                artwork = "https://rntp.dev/example/Lullaby%20(Demo).jpeg",
                artist = "David Chavez",
                duration = 71 * 1000,
            ),
            DefaultAudioItem(
                "https://rntp.dev/example/Rhythm%20City%20(Demo).mp3",
                MediaType.DEFAULT,
                title = "Rhythm City (Demo)",
                artwork = "https://rntp.dev/example/Rhythm%20City%20(Demo).jpeg",
                artist = "David Chavez",
                duration = 106 * 1000,
            ),
            DefaultAudioItem(
                "https://rntp.dev/example/hls/whip/playlist.m3u8",
                MediaType.HLS,
                title = "Whip",
                artwork = "https://rntp.dev/example/hls/whip/whip.jpeg",
            ),
            DefaultAudioItem(
                "https://ais-sa5.cdnstream1.com/b75154_128mp3",
                MediaType.DEFAULT,
                title = "Smooth Jazz 24/7",
                artwork = "https://rntp.dev/example/smooth-jazz-24-7.jpeg",
                artist = "New York, NY",
            ),
            DefaultAudioItem(
                "https://traffic.libsyn.com/atpfm/atp545.mp3",
                title = "Chapters",
                artwork = "https://random.imagecdn.app/800/800?dummy=1",
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    title: String,
    artist: String,
    artwork: String,
    position: Long,
    duration: Long,
    isLive: Boolean,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    isPaused: Boolean,
    onTopBarAction: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onSeek: (Long) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = "Kotlin Audio Example",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = onTopBarAction) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            TrackDisplay(
                title = title,
                artist = artist,
                artwork = artwork,
                position = position,
                duration = duration,
                isLive = isLive,
                onSeek = onSeek,
                modifier = Modifier.padding(top = 46.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            PlayerControls(
                onPrevious = onPrevious,
                onNext = onNext,
                isPaused = isPaused,
                onPlayPause = onPlayPause,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)
            )
        }
    }
}
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ContentPreview() {
    KotlinAudioTheme {
        MainScreen(
            title = "Title",
            artist = "Artist",
            artwork = "",
            position = 1000,
            duration = 6000,
            isLive = false,
            isPaused = true
        )
    }
}
