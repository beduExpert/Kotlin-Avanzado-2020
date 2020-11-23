package org.bedu.animations

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_shared_transition.*


class SharedTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_transition)


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
    }
}
