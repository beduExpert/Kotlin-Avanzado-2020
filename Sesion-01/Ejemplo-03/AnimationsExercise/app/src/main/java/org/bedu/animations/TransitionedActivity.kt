package org.bedu.animations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.transition.Fade


class TransitionedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transitioned)

        //definiendo el tipo de transición
        val fade = Fade()

        //asignando el tipo de transición a las transiciones de la ventana
        window.enterTransition = fade
        window.exitTransition = fade
    }
}