package org.bedu.reproductordevideo

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mc: MediaController

    //paso 8: declarar banderas
    val PLAY_TIME = "PLAY_TIME"
    val IS_PLAYING = "IS_PLAYING"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //paso 7: Cambiamos el modo de la pantalla dependiendo de la orientación del dispositivo
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        }
        else {
            showSystemUI()
        }


        //paso 3: Creamos el media controller prefabricado y lo asignamos al videoView
        mc = MediaController(this)
        mc.setAnchorView(videoView)
        videoView.setMediaController(mc)

        //paso 4: determinamos el contenido local con una Uri, lo seteamos al videoView y reproducimos
        val path = Uri.parse("android.resource://${packageName}/${R.raw.simpsons}")
        videoView.setVideoURI(path)
        videoView.start()
    }

    //Paso 9: Guardar el estado del video antes de que la Activity se destruya
    override fun onSaveInstanceState(out: Bundle) {
        super.onSaveInstanceState(out)
        //guardando la posición actual del video y si se está reproduciendo
        out.putInt(PLAY_TIME, videoView.currentPosition)
        out.putBoolean(IS_PLAYING, videoView.isPlaying)
    }

    //paso 10: regresar al estado anterior
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        // Recuperando el tiempo transcurrido de reproducción
        var seekValue = 0
        val playTime = savedInstanceState?.getInt(PLAY_TIME)
        if(playTime != null){
            seekValue = playTime
        }

        videoView.seekTo(seekValue)

        // Vamos a regresar al estado anterior reproduciendo/pausado
        var isPlaying = savedInstanceState?.getBoolean(IS_PLAYING)

        if(isPlaying == null){
            videoView.start()
        } else if(!isPlaying){
            videoView.pause()
        } else{
            videoView.start()
        }
    }


    //Habilitando el modo inmersivo
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // El contenido aparecerá bajo las barras de sistema
                // para que no cambie de tamaño cuando estas aparezcan/desaparezcan
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Esconde el nav Bar y el Status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Removemos todas las banderas para que las barras de sistema vuelvan a mostrarse
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }



}