package com.chad.demo.random.gl

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chad.demo.random.constant.Constant
import com.chad.demo.random.util.Logger

class GLPhotoActivity: AppCompatActivity() {

    companion object {
        private const val TAG = "GLPhotoActivity"
    }

    private var mGlSurfaceView: GLSurfaceView? = null
    private var mGLRender: GLRender? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkHardware()) {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        mGlSurfaceView = GLSurfaceView(this)
        setContentView(mGlSurfaceView)

        init()
    }

    private fun checkHardware(): Boolean {
        val am: ActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val supportEs3 = am.deviceConfigurationInfo.reqGlEsVersion >= 0x30000
        val isEmulator = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
        return supportEs3 || isEmulator
    }

    private fun init() {
        mGLRender = GLRender()
        mGLRender!!.bindGLSurface(mGlSurfaceView!!)
    }

    override fun onResume() {
        super.onResume()
        mGlSurfaceView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGlSurfaceView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mGLRender?.onDestroy()
    }
}