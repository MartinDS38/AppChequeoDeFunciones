package com.example.chequeodefunciones

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ChequearParlantes : AppCompatActivity() {
    lateinit var soundPool: SoundPool
    lateinit var botonProbar: Button
    lateinit var audioAttributes: AudioAttributes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chequear_parlantes)

        var sonido: Int

        audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        //Constructor deprecado, pero con el constructor nuevo no anda
        soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)

        sonido = soundPool.load(this, R.raw.sonido, 1)

        botonProbar = findViewById(R.id.btnSonido)

        botonProbar.setOnClickListener {
            soundPool.play(sonido,1F,1F,0,0,1F)
        }

        val bVolver : Button = findViewById(R.id.bVolverParl)

        bVolver.setOnClickListener {
            val volver = Intent(this, MainActivity::class.java)
            soundPool.release()
            startActivity(volver)
            finishAffinity()
        }
    }
}