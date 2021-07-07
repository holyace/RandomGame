package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRender: GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "GLRender"
        const val CORDS_PER_VERTEX = 3
        const val CORDS_PER_VERTEX_COLOR = 2 * CORDS_PER_VERTEX
        const val FLOAT_SIZE = 4
    }

    private var mWidth = 0
    private var mHeight = 0

    private var mAppCtx: Context? = null
    private var mRenderable: Renderable? = null

    fun bindGLSurface(glSurfaceView: GLSurfaceView) {
        glSurfaceView.let {
            it.setEGLContextClientVersion(3)
            it.debugFlags = GLSurfaceView.DEBUG_CHECK_GL_ERROR or GLSurfaceView.DEBUG_LOG_GL_CALLS
            it.setRenderer(this)
            it.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        }

        mAppCtx = glSurfaceView.context.applicationContext

        mRenderable = SquareVertexRender(mAppCtx!!,
                "shader_vertex.vs.glsl",
                "shader_vertex.fs.glsl")
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
//        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
    }

    private fun clearCanvas() {
        GLES30.glClearColor(1f, 0f, 0f, 0.5f)
//        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
//        GLES30.glClear(GLES30.GL_DEPTH_BITS)
    }

    private fun drawBase() {

    }

}