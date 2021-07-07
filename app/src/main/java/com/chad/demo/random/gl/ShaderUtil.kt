package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import com.chad.demo.random.constant.Constant
import com.chad.demo.random.util.FileUtil
import com.chad.demo.random.util.Logger

class ShaderUtil {

    companion object {

        private const val TAG = "ShaderUtil"

        fun loadShaderCode(context: Context, fileName: String): String? {
            return FileUtil.readAssetsString(context, fileName)
        }

        fun compileShader(type: Int, shaderCode: String?): Int {
            if (shaderCode.isNullOrBlank()) {
                Logger.i(Constant.MODULE, TAG, "compileShader shader code is empty")
                return -1
            }

            val shaderId = GLES30.glCreateShader(type)
            if (shaderId <= 0) {
                Logger.i(Constant.MODULE, TAG, "compileShader create shader id type: %d, error: %d",
                        type, GLES30.glGetError())
                return -1
            }

            GLES30.glShaderSource(shaderId, shaderCode)
            GLES30.glCompileShader(shaderId)

            val compileStatus = intArrayOf(-1)
            GLES30.glGetShaderiv(shaderId, GLES30.GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] <= 0) {
                Logger.i(Constant.MODULE, TAG, "compile shader error type: %d, id: %d, status: %d, error: %s",
                        type, shaderId, compileStatus[0], GLES30.glGetShaderInfoLog(shaderId))
                GLES30.glDeleteShader(shaderId)
                return -1
            }

            return shaderId
        }

        fun linkProgram(ids: IntArray): Int {
            val programId = GLES30.glCreateProgram()
            if (programId <= 0) {
                Logger.i(Constant.MODULE, TAG, "create program id error : %d",
                        GLES30.glGetError())
                return -1
            }
            ids.forEach {
                GLES30.glAttachShader(programId, it)
            }
            GLES30.glLinkProgram(programId)

            val linkStatus = intArrayOf(-1)
            GLES30.glGetProgramiv(programId, GLES30.GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] <= -1) {
                Logger.i(Constant.MODULE, TAG, "link program error, id: %d, msg: %s",
                        programId, GLES30.glGetProgramInfoLog(programId))
                GLES30.glDeleteProgram(programId)
                return -1
            }

            return programId
        }
    }
}