package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * 顶点法
 */
class SquareVertexRender(context: Context)
    : Renderable(context,
        "shader_vertex.vs.glsl",
        "shader_vertex.fs.glsl") {

    private lateinit var mVertexBuffer: FloatBuffer

    private val x = 0.25f
    private val y = 0.125f
    private val z = 0.5f

    private val mV0 = floatArrayOf(x, y, 0f)
    private val mV1 = floatArrayOf(-x, y, 0f)
    private val mV2 = floatArrayOf(-x, -y, 0f)
    private val mV3 = floatArrayOf(x, -y, 0f)

    private val mV4 = floatArrayOf(2 * x, -3 * y, -x)
    private val mV5 = floatArrayOf(2 * x, 3 * y, -z)
    private val mV6 = floatArrayOf(-2 * x, 2 * y, -z)
    private val mV7 = floatArrayOf(-2 * x, -2 * y, -z)

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

    override fun onGLESReady() {
        super.onGLESReady()
        //of coordinate values * 4 bytes per float
        mVertexBuffer = ByteBuffer.allocateDirect(mVertexPoints.size * GLRender.FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mVertexBuffer.put(mVertexPoints)
        mVertexBuffer.position(0)

        //启用背面剔除
//        GLES30.glEnable(GLES30.GL_CULL_FACE)
//        GLES30.glPolygonOffset()

        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                GLRender.CORDS_PER_VERTEX * GLRender.FLOAT_SIZE, mVertexBuffer)

//        GLES30.glFrontFace(GLES30.GL_CW)
    }

    override fun render() {
        super.render()
        GLES30.glEnableVertexAttribArray(0)
        GLES30.glLineWidth(10f)
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, 12 * 3)
    }

    override fun setRotation(angle: Float) {
        super.setRotation(angle)
    }
}