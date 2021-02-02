package org.bedu.androidfcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    companion object{
        const val CHANNEL_ID = "CANAL_GENERICO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("ERROR_FCM", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                Log.d("FCM_TOKEN",token)

                //toast
                Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
            })
    }

    private fun setNotificationChannel(){
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Canal Generico",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "CANAL GENERICO"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}
