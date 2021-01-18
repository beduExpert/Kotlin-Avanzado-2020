package org.bedu.rxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import io.reactivex.disposables.Disposable



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress.visibility = View.VISIBLE

        var names = arrayListOf<String>()

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)

        lista.adapter = adapter


        val nameList = listOf(
            "Manuel",
            "Agnès",
            "Frida",
            "Pedro",
            "Anaïs",
            "Pedro",
            "Marlon",
            "Sofía"
        )

        val observable = nameList
            .toObservable()
            .observeOn(Schedulers.computation()) //correr en el main thread
            .filter{it==nameList.get(1)}
            .subscribeBy (
                    onError =  { it.printStackTrace() },
                    onNext = {
                        names.add(it)
                        adapter.notifyDataSetChanged()
                    },
                    onComplete = {
                        progress.visibility = View.GONE
                    }
                )


        val list = IntRange(0, 9).toList()


    }



}
