package org.bedu.animations

import android.os.Bundle
import android.transition.Explode
import androidx.appcompat.app.AppCompatActivity
import android.transition.Slide
import android.view.View




class TransitionedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transitioned)

        title = "Receptor"


        //definiendo el tipo de transición
        val transition = Explode()
        val decor = window.decorView

        transition.excludeTarget(decor.findViewById<View>(R.id.action_bar_container), true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        transition.excludeTarget(android.R.id.navigationBarBackground, true)

        //asignando el tipo de transición a las transiciones de la ventana
        window.enterTransition = transition
        window.exitTransition = transition
    }

}