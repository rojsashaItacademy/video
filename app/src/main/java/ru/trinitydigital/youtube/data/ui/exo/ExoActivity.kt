package ru.trinitydigital.youtube.data.ui.exo

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exo.*
import ru.trinitydigital.youtube.R

class ExoActivity : AppCompatActivity() {

    private lateinit var exoMediaPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo)
        initExoPlayer()

        play("https://static.bulbul.kg/mp4/9/73409.94a48ab333575954b93499ae4b9d51ba.240.mp4")
    }

    private fun initExoPlayer() {
        player.player = initialize()
    }

    private fun initialize(): ExoPlayer {
        initializePlayer()
        initializeMediaSession()
        return exoMediaPlayer
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(applicationContext)
        exoMediaPlayer =
            ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
    }

    private fun initializeMediaSession() {
        mediaSession = MediaSessionCompat(applicationContext, "sadasdasd")
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mediaSession.setMediaButtonReceiver(null)

        stateBuilder = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_FAST_FORWARD or
                        PlaybackStateCompat.ACTION_REWIND
            )

        mediaSession.setPlaybackState(stateBuilder.build())

        mediaSession.setCallback(SessionCallback())

        mediaSession.isActive = true
    }

    private inner class SessionCallback : MediaSessionCompat.Callback() {

        private val SEEK_WINDOW_MILLIS = 1_000

        override fun onPlay() {
            exoMediaPlayer.playWhenReady = true
        }

        override fun onPause() {
            exoMediaPlayer.playWhenReady = false
        }

        override fun onRewind() {
            exoMediaPlayer.seekTo(exoMediaPlayer.currentPosition - SEEK_WINDOW_MILLIS)
        }

        override fun onFastForward() {
            exoMediaPlayer.seekTo(exoMediaPlayer.currentPosition + SEEK_WINDOW_MILLIS)
        }
    }

    val listener = object: Player.EventListener{
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

        }

        override fun onTracksChanged(
            trackGroups: TrackGroupArray?,
            trackSelections: TrackSelectionArray?
        ) {
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            if (isLoading){
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
        }

        override fun onPositionDiscontinuity(reason: Int) {
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        }

        override fun onSeekProcessed() {
        }

    }

    private fun play(url: String) {

        val userAgent = Util.getUserAgent(
            applicationContext,
            applicationContext.getString(R.string.app_name)
        )

        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            userAgent,
            null /* listener */,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true /* allowCrossProtocolRedirects */
        )

        val mediaSource = ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(
                applicationContext,
                null,
                httpDataSourceFactory
            )
        )
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))

        exoMediaPlayer.prepare(mediaSource)

        exoMediaPlayer.playWhenReady = true

        exoMediaPlayer.addListener(listener)

    }
}