package org.bedu.crashlytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Crashlytics.setUserIdentifier("Bedu-LmtvK4ge-Fqox-blRy")
        Crashlytics.setUserEmail("manuel@bedu.org")
        Crashlytics.setUserName("Manuel Bedu")

        Crashlytics.setInt("Edad", 23)
        Crashlytics.setString("Trabajo", "Developer")
        Crashlytics.setBool("Bloqueado",false)
        Crashlytics.setFloat("Crédito",1350.23f)


        btnError.setOnClickListener{
            try {
                throw NullPointerException()
            } catch (ex: NullPointerException) {
                Crashlytics.log(Log.ERROR, "CrashError", "NullPointer Provocado para pruebas!")
                Crashlytics.logException(ex)
            }
        }
    }
}
