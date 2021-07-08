package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * 索引法
 */
class SquareIndexRender(context: Context,
                        vertexShader: String,
                        fragmentShader: String)
    : Renderable(context, vertexShader, fragmentShader) {

    private lateinit var mVertexBuffer: FloatBuffer
    private lateinit var mIndexBuffer: ShortBuffer

    private val x = 0.25f
    private val y = 0.125f
    private val z = 0.5f

    private val mV0 = floatArrayOf(x, y, 0f)
    private val mV1 = floatArrayOf(-x, y, 0f)
    private val mV2 = floatArrayOf(-x, -y, 0f)
    private val mV3 = floatArrayOf(x, -y, 0f)

    private val mV4 = floatArrayOf(2 * x, -4 * y, -x)
    private val mV5 = floatArrayOf(2 * x, 4 * y, -z)
    private val mV6 = floatArrayOf(-2 * x, 2 * y, -z)
    private val mV7 = floatArrayOf(-2 * x, -2 * y, -z)

    private var mVertexPoints: FloatArray = FloatArrayEx.from(
            mV0, mV1, mV2, mV3,
            mV4, mV5, mV6, mV7
    )

    private var mVertexIndex: ShortArray = shortArrayOf(
            //front
            0, 1, 2, 0, 2, 3,
            //left
            1, 6, 7, 1, 7, 2,
            //bottom
            7, 4, 3, 7, 3, 2,
            //top
            0, 5, 6, 0, 6, 1,
            //right
            0, 3, 4, 0, 4, 5,
            //back
            5, 4, 7, 5, 7, 6
    )

    override fun onGLESReady() {
        super.onGLESReady()

        //of coordinate values * 4 bytes per float
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexPoints.size * GLRender.FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mVertexBuffer.put(mVertexPoints)
        mVertexBuffer.position(0)

        //of coordinate values * 2 bytes per short
        mIndexBuffer = ByteBuffer.allocateDirect(mVertexIndex.size * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
        mIndexBuffer.put(mVertexIndex)
        mIndexBuffer.position(0)

        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                GLRender.CORDS_PER_VERTEX * GLRender.FLOAT_SIZE, mVertexBuffer)
    }

    override fun render() {
        super.render()
        GLES30.glEnableVertexAttribArray(0)
        GLES30.glLineWidth(10f)
        GLES30.glDrawElements(GLES30.GL_LINE_LOOP, mVertexIndex.size, GLES30.GL_UNSIGNED_SHORT, mIndexBuffer)
    }
}