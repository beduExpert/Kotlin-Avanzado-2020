package org.bedu.audioconseeker

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler
import android.widget.SeekBar



class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.sarias_song)

        //Paso 2: setear la duración del archivo en el rango del seekBar
        val songLength = mediaPlayer.duration
        seekBar.max = songLength/1000 //dividido entre mil para volverlo segundos


        //paso 3: modificamos el tiempo en la reproducción en función de la posición del SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //Si el mediaPlayer ya fue inicializado, poner la reproduccción en el segundo que sugiere el SeekBar
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000) //multiplicado por mil porque el resultado va en ms
                }
            }
        })

        //paso 4: agregando la rutina para el avance del tiempo en el SeekBar
        updateSeekBar()


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

    private fun updateSeekBar(){
        val mHandler = Handler()

        //Actualizamos la información cada segundo en el hilo de la IU, que se ejecuta en el MainThread
        this@MainActivity.runOnUiThread(object : Runnable {

            override fun run() {

                // modificammos el progreso (la posición) del seekBar de acuerdo al tiempo transcurrido en el MediaPlayer
                if (mediaPlayer != null) {
                    val mCurrentPosition = mediaPlayer.currentPosition / 1000
                    seekBar.progress = mCurrentPosition
                }

                mHandler.postDelayed(this, 1000) //correr cada segundo
            }
        })
    }

}
