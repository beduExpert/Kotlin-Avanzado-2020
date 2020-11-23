package org.bedu.animations

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_object_anim.*



class ObjectAnimActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_anim)

        frameObjAnim.setOnClickListener{
            moveArwing()
        }

        btnBarrell.setOnClickListener{
            barrelRoll()
        }
    }


    //mover en el eje y a nuestra nabe
    private fun moveArwing(){

        //obtenemos la posición del centro del layout
        val destination = - frameObjAnim.height / 2f

        //creando nuestro ValueAnimator, el rango del interpolador va de 0 a la mitad de la pantalla
        val valueAnimator = ValueAnimator.ofFloat(0f, destination)

        //cada update que del interpolador nos da el valor en el eje que necesitamos para setearlo en nuestra View
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float //obtenemos el valor interpolado
            arwing.translationY = value //asignamos el valor interpolado en nuestra posición y
        }

        //declaramos al interpolador como uno lineal
        valueAnimator.interpolator = AccelerateInterpolator()

        //declaramos la duración de la animación en 1000 ms = 1 segundo
        valueAnimator.duration = 1500

        //corremos la animación
        valueAnimator.start()
    }

    private fun barrelRoll(){
        //el valor de nuestro animator va del 0 a 720, dos veces 360º (dos rotaciones de 360º)
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)

        //en cada update del animador asignamos la rotación requerida
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float //obteniendo el valor actual
            // 2
            arwing.rotation = value //asignando la posición de rotación
        }

        valueAnimator.interpolator = AccelerateDecelerateInterpolator() //el interpolador es lineal
        valueAnimator.duration = 1000 //la duración es de 1 segundo
        valueAnimator.start() //correr la animaciónn
    }

}
