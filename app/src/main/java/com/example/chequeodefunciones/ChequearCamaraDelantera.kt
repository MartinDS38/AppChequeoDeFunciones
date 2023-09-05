package com.example.chequeodefunciones

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import androidx.core.app.ActivityCompat

class ChequearCamaraDelantera : AppCompatActivity() {
    lateinit var camaraManager : CameraManager
    lateinit var cameraDevice : CameraDevice
    lateinit var handlerThread: HandlerThread
    lateinit var handler : Handler
    lateinit var camDel : TextureView
    lateinit var capReq : CaptureRequest.Builder
    lateinit var cameraCaptureSession: CameraCaptureSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chequear_camara_delantera)
        obtenerPermisos()

        camaraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        camDel = findViewById(R.id.camDel)
        handlerThread = HandlerThread("video")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        camDel.surfaceTextureListener = object : TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                abrirCamara()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {

            }

        }

        val volver : Button = findViewById(R.id.bVolverCamDel)

        volver.setOnClickListener {
            cameraDevice.close()
            finishAffinity()
            val volver : Intent = Intent(this, MainActivity::class.java)
            startActivity(volver)

        }

    }

    fun abrirCamara(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            obtenerPermisos()
        }

        camaraManager.openCamera("1", object: CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                cameraDevice = p0
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                var surface = Surface(camDel.surfaceTexture)
                capReq.addTarget(surface)

                cameraDevice.createCaptureSession(listOf(surface), object: CameraCaptureSession.StateCallback(){
                    override fun onConfigured(p0: CameraCaptureSession) {
                        cameraCaptureSession = p0
                        cameraCaptureSession.setRepeatingRequest(capReq.build(), null, null)
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {

                    }
                },handler)
            }

            override fun onDisconnected(p0: CameraDevice) {

            }

            override fun onError(p0: CameraDevice, p1: Int) {

            }
        },handler)
    }

    fun obtenerPermisos(){
        var listaPermisos = mutableListOf<String>()

        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            listaPermisos.add(android.Manifest.permission.CAMERA)

        if(listaPermisos.size > 0){
            requestPermissions(listaPermisos.toTypedArray(), 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if(it != PackageManager.PERMISSION_GRANTED){
                obtenerPermisos()
            }
        }
    }
}