package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * 顶点法
 */
class SquareVertexRender(context: Context,
                         vertexShader: String,
                         fragmentShader: String)
    : Renderable(context, vertexShader, fragmentShader) {

    private lateinit var mVertexBuffer: FloatBuffer

    private val x = 0.25f
    private val y = 0.125f
    private val z = 0.5f

    private val mV0 = floatArrayOf(x, y, 0f)
    private val mV1 = floatArrayOf(-x, y, 0f)
    private val mV2 = floatArrayOf(-x, -y, 0f)
    private val mV3 = floatArrayOf(x, -y, 0f)

    private val mV4 = floatArrayOf(2 * x, -2 * y, -x)
    private val mV5 = floatArrayOf(2 * x, 2 * y, -z)
    private val mV6 = floatArrayOf(-2 * x, 2 * y, -z)
    private val mV7 = floatArrayOf(-2 * x, -2 * y, -z)

    private var mVertexPoints: FloatArray = FloatArrayEx.from(
            //front
            mV0, mV1, mV2, mV2, mV3, mV0,
            //right
            mV0, mV3, mV4, mV4, mV5, mV0,
            //top
            mV0, mV5, mV6, mV6, mV1, mV0,
            //left
            mV1, mV6, mV7, mV7, mV2, mV1,
            //back
            mV5, mV6, mV7, mV7, mV4, mV5,
            //bottom
            mV3, mV2, mV7, mV7, mV4, mV3
    )

    override fun onGLESReady() {
        super.onGLESReady()
        //of coordinate values * 4 bytes per float
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexPoints.size * GLRender.FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mVertexBuffer.put(mVertexPoints)
        mVertexBuffer.position(0)

        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                GLRender.CORDS_PER_VERTEX * GLRender.FLOAT_SIZE, mVertexBuffer)

    }

    override fun render() {
        super.render()
        GLES30.glEnableVertexAttribArray(0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 12)
    }

    override fun setRotation(angle: Float) {
        super.setRotation(angle)
    }
}