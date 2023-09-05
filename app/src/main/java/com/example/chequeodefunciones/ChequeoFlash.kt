package com.example.chequeodefunciones

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class ChequeoFlash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chequeo_flash)

        val flash : Switch = findViewById(R.id.switchFlash)
        val camaraManager : CameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val bVolver : Button = findViewById(R.id.bVolverFlash)
        val texto : TextView = findViewById(R.id.textoFlash)

        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            Toast.makeText(this,"El dispositivo no tiene cámara",Toast.LENGTH_SHORT).show()
        }
        else if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this,"El dispositivo no tiene flash",Toast.LENGTH_SHORT).show()
        }

        flash.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, activado ->
            if(activado){
                try {
                    camaraManager.setTorchMode("0",true);
                    texto.text = "El flash funciona correctamente"
                    texto.setTextColor(Color.GREEN)
                }
                catch (e: CameraAccessException){
                    e.printStackTrace()
                    texto.text = "El flash no funciona"
                    texto.setTextColor(Color.RED)
                }
            }
            else{
                try {
                    camaraManager.setTorchMode("0",false);
                }
                catch (e: CameraAccessException){
                    e.printStackTrace()
                }
            }
        })

        bVolver.setOnClickListener {
            val volver : Intent = Intent(this, MainActivity::class.java)
            startActivity(volver)
            try {
                camaraManager.setTorchMode("0",false);
                finishAffinity()
            }
            catch (e: CameraAccessException){
                e.printStackTrace()
            }
        }
    }
}
