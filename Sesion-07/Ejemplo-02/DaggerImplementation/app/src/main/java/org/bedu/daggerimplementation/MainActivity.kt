package org.bedu.daggerimplementation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    //Campo a ser inyectado cuando se ejecute inject()
    @Inject
    lateinit var secret: Secret

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Con esto se inyectan todas las dependencias
        DaggerSecretComponent.create().inject(this)


        chest.setOnClickListener{
            textView.text = secret.hint
        }
    }
}


class Secret @Inject constructor(){
    val hint = "¡Haz encontrado Rupias en el cofre!"
}


//Este es el componente de dagger que va a inyectarse en algún miembreo con @Injecct
@Component
interface SecretComponent {
    fun inject(app: MainActivity)
}

