package com.pramodk.timepass

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.widget.TextView

import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var calendar: Calendar
    private var hour24hrs: Int = 0
    private var hour12hrs: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0
    val handler = Handler()
    private lateinit var tts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                tts.language = Locale.UK
            }
        })

        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000*60*1)//1 sec delay
                //updateTime()
                speakOut()
            }
        }, 0)


    }

    fun updateTime() {
        // Set Current Date
        calendar = Calendar.getInstance()
        hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        hour12hrs = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);

        textView1.text = "$hour12hrs:$minutes:$seconds"

    }
    private fun speakOut() {

        calendar = Calendar.getInstance()
        hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        hour12hrs = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);
        seconds = calendar.get(Calendar.SECOND);

        //textView1.text = "$hour12hrs:$minutes:$seconds"

        val message = "CURRENT TIME IS $hour24hrs hours:$minutes minutes"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            @Suppress("DEPRECATION")
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun onDestroy() {
        // Shut down TTS
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

}
