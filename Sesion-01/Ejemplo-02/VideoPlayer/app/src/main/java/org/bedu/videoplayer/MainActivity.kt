package org.bedu.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri
import android.widget.MediaController
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var mc: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mc =  MediaController(this)
        mc.setAnchorView(videoView)
       videoView.setMediaController(mc)

        val path = Uri.parse("android.resource://${packageName}/${R.raw.sarias_song}")

        videoView.setOnCompletionListener {
            Toast.makeText(applicationContext,"Video finalizado!", Toast.LENGTH_SHORT).show()
        }

        videoView.setVideoURI(path)
        videoView.start()
        
    }
}
