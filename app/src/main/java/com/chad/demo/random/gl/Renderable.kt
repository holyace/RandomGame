package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLUtils
import com.chad.demo.random.constant.Constant
import com.chad.demo.random.util.FileUtil
import com.chad.demo.random.util.Logger

open class Renderable(protected val context: Context,
                      private val vertexShader: String,
                      private val fragmentShader: String) {

    companion object {
        private const val TAG = "Renderable"
    }

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

    open fun setRotation(angle: Float, angleX: Float, angleY: Float, angleZ: Float) {}

    open fun onDestroy() {
        GLES30.glDeleteShader(mVertexShaderId)
        GLES30.glDeleteShader(mFragmentShaderId)
        GLES30.glDeleteProgram(mProgramId)
    }

    protected fun loadTexture(context: Context, name: String): Int {
        val textureId = IntArray(1)
        GLES30.glGenTextures(1, textureId, 0)

        if (textureId[0] <= 0) {
            Logger.i(Constant.MODULE, TAG, "glGenTextures res: %s, id: %d, error: %d",
                    name, textureId[0], GLES30.glGetError())
            return -1
        }

        val bitmap = FileUtil.readAssetsBitmap(context, name)
        if (bitmap == null) {
            Logger.i(Constant.MODULE, TAG, "glGenTextures load bitmap error: %s", name)
            GLES30.glDeleteTextures(1, textureId, 0)
            return -1
        }

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0])

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST)

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT)

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0)

        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D)

        bitmap.recycle()

        return textureId[0]
    }
}