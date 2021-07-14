package com.chad.demo.random.gl

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import android.view.View
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRender: GLSurfaceView.Renderer, View.OnTouchListener {

    companion object {
        const val TAG = "GLRender"
        const val VERTEX_POINT_SIZE = 3
        const val COLOR_POINT_SIZE = 3
        const val TEXTURE_CORDS_POINT_SIZE = 2
        const val CORDS_PER_VERTEX_COLOR = 2 * VERTEX_POINT_SIZE
        const val FLOAT_SIZE = 4
        const val MATRIX_SIZE = 16
    }

    private var mWidth = 0
    private var mHeight = 0

    private var mAppCtx: Context? = null
    private var mRenderable: Renderable? = null

    private var mDownX = 0f
    private var mDownY = 0f

    private var mLastX = 0f
    private var mLastY = 0f

    private var mAngleX = 0f
    private var mAngleY = 0f

    fun bindGLSurface(glSurfaceView: GLSurfaceView) {
        glSurfaceView.let {
            it.setEGLContextClientVersion(3)
            it.debugFlags = GLSurfaceView.DEBUG_CHECK_GL_ERROR or GLSurfaceView.DEBUG_LOG_GL_CALLS
            it.setRenderer(this)
            it.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
            it.setOnTouchListener(this)
        }

        mAppCtx = glSurfaceView.context.applicationContext

//        mRenderable = SquareVertexRender(mAppCtx!!)

        mRenderable = SquareIndexRender(mAppCtx!!)

//        mRenderable = SquareVertexVAORender(mAppCtx!!)

//        mRenderable = SquareMatrixRender(mAppCtx!!)

//        mRenderable = SquareTextureRender(mAppCtx!!)

//        mRenderable = TriangleRender(mAppCtx!!)

//        mRenderable = SquareCubeRender(mAppCtx!!)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        initOpenGL()
        clearCanvas()
        mRenderable?.onGLESReady()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        mWidth = width
        mHeight = height
        GLES30.glViewport(0, 0, width, height)
        mRenderable?.onSurfaceChange(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        clearCanvas()
        drawBase()
        mRenderable?.render()
    }

    fun onDestroy() {
        mRenderable?.onDestroy()
    }

    private fun initOpenGL() {
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
    }

    private fun clearCanvas() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)
//        GLES30.glClearColor(0f, 0f, 0f, 1.0f)
//        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
//        GLES30.glClear(GLES30.GL_DEPTH_BITS)
    }

    private fun drawBase() {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event?: return false
        val action = event.actionMasked
        val x = event.x
        val y = event.y
        when (action) {

            MotionEvent.ACTION_DOWN -> {
                mDownX = x
                mDownY = y

                mLastX = x
                mLastY = y
            }

            MotionEvent.ACTION_MOVE -> {
                var dx = x - mLastX
                var dy = x - mLastY

                mLastX = x
                mLastY = y

                if (dx >= dy) {
                    mAngleX += dx / mWidth.toFloat() * 90
                    mRenderable?.setRotation(mAngleX, 1f, 0f, 0f)
                }
//                else {
//                    mAngleY += dy / mHeight.toFloat() * 90
//                    mRenderable?.setRotation(mAngleY, 0f, 1f, 0f)
//                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mLastX = 0f
                mLastY = 0f
            }
        }
        return true
    }
}