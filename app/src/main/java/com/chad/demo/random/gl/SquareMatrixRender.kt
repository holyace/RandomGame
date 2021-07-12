package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.Matrix
import com.chad.demo.random.gl.GLRender.Companion.MATRIX_SIZE
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class SquareMatrixRender(context: Context)
    : Renderable(context,
        "shader_matrix.vs.glsl",
        "shader_vertex.fs.glsl") {

    private var mMVPMatrixHandle = -1
    private val mMatrix = FloatArray(MATRIX_SIZE)
    private val mViewMatrix = FloatArray(MATRIX_SIZE)
    private val mProjectionMatrix = FloatArray(MATRIX_SIZE)

    private lateinit var mVertexBuffer: FloatBuffer

    private val x = 0.5f
    private val y = 0.5f
    private val z = 0.5f

    private val mV0 = floatArrayOf(x, y, 0f)
    private val mV1 = floatArrayOf(-x, y, 0f)
    private val mV2 = floatArrayOf(-x, -y, 0f)
    private val mV3 = floatArrayOf(x, -y, 0f)

    private val mV4 = floatArrayOf(x, -y, -x)
    private val mV5 = floatArrayOf(x, y, -z)
    private val mV6 = floatArrayOf(-x, y, -z)
    private val mV7 = floatArrayOf(-x, -y, -z)

    private var mVertexPoints: FloatArray = FloatArrayEx.from(
            //front
            mV0, mV3, mV2, mV0, mV2, mV1,
            //right
            mV0, mV5, mV4, mV0, mV4, mV3,
            //top
            mV0, mV6, mV5, mV0, mV1, mV6,
            //left
            mV1, mV2, mV7, mV1, mV7, mV6,
            //back
            mV5, mV6, mV7, mV5, mV7, mV4,
            //bottom
            mV3, mV4, mV7, mV3, mV7, mV2
    )

    private val mVAOSize = 1
    private val mVAOIds = IntArray(mVAOSize)

    private val mVBOSize = 1
    private val mVBOIds = IntArray(mVBOSize)

    override fun onGLESReady() {
        super.onGLESReady()

        //of coordinate values * 4 bytes per float
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexPoints.size * GLRender.FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mVertexBuffer.put(mVertexPoints)
        mVertexBuffer.position(0)

        GLES30.glGenVertexArrays(mVAOSize, mVAOIds, 0)
        GLES30.glBindVertexArray(mVAOIds[0])

        GLES30.glGenBuffers(mVBOSize, mVBOIds, 0)

        //向顶点坐标数据缓冲送入数据把顶点数组复制到缓冲中
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVertexPoints.size * GLRender.FLOAT_SIZE,
                mVertexBuffer, GLES30.GL_STATIC_DRAW)

        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                GLRender.CORDS_PER_VERTEX * GLRender.FLOAT_SIZE, 0)

        GLES30.glEnableVertexAttribArray(0)

        Matrix.setLookAtM(mViewMatrix, 0,
                0f, 0f, 3f,
                0f, 0f, 0f,
                0f, 1f, 0f)

        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramId, "uMVPMatrix")
    }

    override fun onSurfaceChange(width: Int, height: Int) {
        super.onSurfaceChange(width, height)

        val ratio = width.toFloat() / height.toFloat()
        Matrix.perspectiveM(mProjectionMatrix, 0, 75f, ratio, 3f, 7f)
    }

    override fun render() {
        super.render()

        Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMatrix, 0)

        GLES30.glBindVertexArray(mVAOIds[0])

        GLES30.glLineWidth(10f)
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, 12 * 3)

        GLES30.glBindVertexArray(0)
    }


}