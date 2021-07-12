package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30

open class Renderable(protected val context: Context,
                      private val vertexShader: String,
                      private val fragmentShader: String) {

    private var mVertexShaderId: Int = -1
    private var mFragmentShaderId: Int = -1
    protected var mProgramId: Int = -1

    open fun onGLESReady() {
        mVertexShaderId = ShaderUtil.compileShader(GLES30.GL_VERTEX_SHADER,
                ShaderUtil.loadShaderCode(context, vertexShader))
        mFragmentShaderId = ShaderUtil.compileShader(GLES30.GL_FRAGMENT_SHADER,
                ShaderUtil.loadShaderCode(context, fragmentShader))
        mProgramId = ShaderUtil.linkProgram(intArrayOf(mVertexShaderId, mFragmentShaderId))

        GLES30.glUseProgram(mProgramId)
    }

    open fun onSurfaceChange(width: Int, height: Int) {}

    open fun render() {}

    open fun setRotation(angle: Float) {}

    open fun onDestroy() {
        GLES30.glDeleteShader(mVertexShaderId)
        GLES30.glDeleteShader(mFragmentShaderId)
        GLES30.glDeleteProgram(mProgramId)
    }
}