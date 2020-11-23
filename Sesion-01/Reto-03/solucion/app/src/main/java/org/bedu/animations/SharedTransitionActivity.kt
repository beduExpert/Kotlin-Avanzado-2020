package org.bedu.animations

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_shared_transition.*


class SharedTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_transition)

        title = "Emisor"

        val transition = Explode()
        val decor = window.decorView

        transition.excludeTarget(decor.findViewById<View>(R.id.action_bar_container), true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        transition.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = transition
        window.exitTransition = transition

        //Definimos el tipo de transición del elemento compartido en la función startActivity
        btnActivity2.setOnClickListener {

            val intent = Intent(this, TransitionedActivity::class.java)

            //se obtiene el nombre de la transción para identificar nuestros diseños, crear las escenas
            //y la animación de la transición
            val options = ViewCompat.getTransitionName(imgConcert)?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, imgConcert, it
                )
            }
            startActivity(intent, options?.toBundle())
        }

        shared_layout.setOnClickListener{
            val transition = Slide()
            TransitionManager.beginDelayedTransition(shared_layout, transition)
            shared_layout.removeView(tvDeleteMe)
        }
    }
}
