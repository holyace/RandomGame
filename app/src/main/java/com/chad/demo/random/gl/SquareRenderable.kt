package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class SquareRenderable(context: Context,
                       vertexShader: String,
                       fragmentShader: String)
    : Renderable(context, vertexShader, fragmentShader) {

    private lateinit var mVertexBuffer: FloatBuffer
    private lateinit var mIndexBuffer: ShortBuffer

    private val x = 0.25f
    private val y = 0.125f
    private val z = 0f

//    private var mVertexPoints: FloatArray = floatArrayOf(
//            //正面矩形
//            x, y, 0f, 1f, 0f, 0f, //V0
//            -x, y, 0f, 0f, 1f, 0f,//V1
//            -x, -y, 0f, 0f, 0f, 1f,//V2
//            x, -y, 0f, 0f, 0f, 0f,//V3
//
//            //背面矩形
//            1.5f * x, -0.75f * y, -z, 1f, 1f, 1f, //V4
//            1.5f * x, 1.25f * y, -z, 0f, 1f, 0f,//V5
//            -0.5f * x, 1.25f * y, -z, 0f, 0f, 1f,//V6
//            -0.5f * x, -0.75f * y, -z, 1f, 0f, 0f//V7
//    )
    private var mVertexPoints: FloatArray = floatArrayOf(
            //正面矩形
            x, y, 0f, //V0
            -x, y, 0f,//V1
            -x, -y, 0f,//V2
            x, -y, 0f,//V3

            //背面矩形
            1.5f * x, -0.75f * y, -z, //V4
            1.5f * x, 1.25f * y, -z,//V5
            -0.5f * x, 1.25f * y, -z,//V6
            -0.5f * x, -0.75f * y, -z//V7
    )

    private var mVertexIndex: ShortArray = shortArrayOf(
            //背面
            5, 6, 7, 5, 7, 4,
            //左侧
            6, 1, 2, 6, 2, 7,
            //底部
            4, 7, 2, 4, 2, 3,
            //顶面
            5, 6, 7, 5, 7, 4,
            //右侧
            5, 0, 3, 5, 3, 4,
            //正面
            0, 1, 2, 0, 2, 3
    )

    private val mRotationMatrix = FloatArray(16)

    private var mMatrixHandler: Int = -1
    private var mVBOSize = 2
    private val mVBOIds = IntArray(mVBOSize)

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

        mMatrixHandler = GLES30.glGetUniformLocation(mProgramId, "uMatrix")

        GLES30.glGenBuffers(mVBOIds.size, mVBOIds, 0)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVertexPoints.size * GLRender.FLOAT_SIZE, mVertexBuffer, GLES30.GL_STATIC_DRAW)

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1])
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndexBuffer.capacity(), mIndexBuffer, GLES30.GL_STATIC_DRAW)

//        GLES30.glVertexAttribPointer(0, 2 * GLRender.CORDS_PER_VERTEX, GLES30.GL_FLOAT, false, 6, 0)
//        GLES30.glVertexAttribPointer(1, 2 * GLRender.CORDS_PER_VERTEX, GLES30.GL_FLOAT, false, 6, 3)
    }

    override fun render() {
        super.render()
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0])
        GLES30.glEnableVertexAttribArray(0)
//        GLES30.glEnableVertexAttribArray(1)

//        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX,
//                GLES30.GL_FLOAT, false,
//                2 * GLRender.CORDS_PER_VERTEX * GLRender.FLOAT_SIZE, mVertexBuffer)

//        GLES30.glUniformMatrix4fv(mMatrixHandler, 1, false, )

//        GLES30.glDrawElements(GLES30.GL_LINE_LOOP, mVertexIndex.size, GLES30.GL_UNSIGNED_SHORT, 0)

        GLES30.glVertexAttribPointer(0, GLRender.CORDS_PER_VERTEX_COLOR, GLES30.GL_FLOAT, false, 0, 0)
        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, mVertexIndex.size)
//        GLES30.glDrawElements(GLES30.GL_LINE_LOOP, mVertexIndex.size, GLES30.GL_UNSIGNED_SHORT, 0)
    }

    override fun setRotation(angle: Float) {
        super.setRotation(angle)
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, -1f)
    }
}