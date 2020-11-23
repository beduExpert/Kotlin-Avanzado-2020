package org.bedu.audioexample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.sarias_song)

        btnPlay.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }

        btnPause.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }

        btnStop.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
            mediaPlayer.seekTo(0) //nos envía al segundo cero
        }
    }





}
