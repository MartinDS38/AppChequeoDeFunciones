package com.example.chequeodefunciones

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.security.Permission
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bFlash : Button = findViewById(R.id.bFlash)
        val bSalir : Button = findViewById(R.id.bSalir)
        val bCamDel : Button = findViewById(R.id.bCamDel)
        val bCamTras : Button = findViewById(R.id.bCamTras)
        val bGPS : Button = findViewById(R.id.bGPS)
        val bParlantes : Button = findViewById(R.id.bParlantes)

        bFlash.setOnClickListener {
            val irFlash : Intent = Intent(this, ChequeoFlash::class.java)
            startActivity(irFlash)
        }

        bGPS.setOnClickListener {
            val irGPS : Intent = Intent(this, ChequearGPS::class.java)
            startActivity(irGPS)
        }

        bCamDel.setOnClickListener {
            val irCamDel : Intent = Intent(this, ChequearCamaraDelantera::class.java)
            startActivity(irCamDel)
        }

        bCamTras.setOnClickListener {
            val irCamTras : Intent = Intent(this, ChequearCamaraTrasera::class.java)
            startActivity(irCamTras)
        }

        bParlantes.setOnClickListener {
            val irParlantes = Intent(this, ChequearParlantes::class.java)
            startActivity(irParlantes)
        }

        bSalir.setOnClickListener {
            exitProcess(0)
        }
    }
}