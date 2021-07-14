package com.chad.demo.random.gl

import android.content.Context
import android.opengl.GLES30
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class SquareColorRender(context: Context):
        Renderable(context,
                "shader_color.vs.glsl",
                "shader_color.fs.glsl") {

    private val mVertexPoints = floatArrayOf(
            //vertex(xyz) color(rgb) texture coords
            1f, 1f, 0f, 1f, 0f, 0f, 1f, 0f,
            -1f, 1f, 0f, 0f, 1f, 0f, 0f, 0f,
            -1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f, 0f, 0f, 1f, 0f,
            -1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f,
            1f, -1f, 0f, 1f, 1f, 0f, 1f, 1f
    )

    private lateinit var mVertexBuffer: FloatBuffer

    private val mVAOSize = 1
    private val mVAOIds = IntArray(mVAOSize)

    private val mVBOSize = 1
    private val mVBOIds = IntArray(mVBOSize)

    private var mTextureId = -1

    override fun onGLESReady() {
        super.onGLESReady()

        mVertexBuffer = ByteBuffer.allocateDirect(mVertexPoints.size * GLRender.FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        mVertexBuffer.put(mVertexPoints)
        mVertexBuffer.position(0)

        GLES30.glGenVertexArrays(mVAOSize, mVAOIds, 0)
        GLES30.glBindVertexArray(mVAOIds[0])

        GLES30.glGenBuffers(mVBOSize, mVBOIds, 0)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVertexPoints.size * GLRender.FLOAT_SIZE,
                mVertexBuffer, GLES30.GL_STATIC_DRAW)

        GLES30.glVertexAttribPointer(0, GLRender.VERTEX_POINT_SIZE,
                GLES30.GL_FLOAT, false,
                (GLRender.VERTEX_POINT_SIZE + GLRender.COLOR_POINT_SIZE + GLRender.TEXTURE_CORDS_POINT_SIZE) * GLRender.FLOAT_SIZE,
                0)

        GLES30.glEnableVertexAttribArray(0)

        GLES30.glVertexAttribPointer(1, GLRender.COLOR_POINT_SIZE,
                GLES30.GL_FLOAT, false,
                (GLRender.VERTEX_POINT_SIZE + GLRender.COLOR_POINT_SIZE + GLRender.TEXTURE_CORDS_POINT_SIZE) * GLRender.FLOAT_SIZE,
                GLRender.VERTEX_POINT_SIZE * GLRender.FLOAT_SIZE)

        GLES30.glEnableVertexAttribArray(1)

        GLES30.glVertexAttribPointer(2, GLRender.TEXTURE_CORDS_POINT_SIZE,
                GLES30.GL_FLOAT, false,
                (GLRender.VERTEX_POINT_SIZE + GLRender.COLOR_POINT_SIZE + GLRender.TEXTURE_CORDS_POINT_SIZE) * GLRender.FLOAT_SIZE,
                (GLRender.VERTEX_POINT_SIZE + GLRender.COLOR_POINT_SIZE) * GLRender.FLOAT_SIZE)

        GLES30.glEnableVertexAttribArray(2)

        mTextureId = loadTexture(context, "image_2.jpg")
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureId)
    }

    override fun render() {
        super.render()

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6)

    }

    override fun onSurfaceChange(width: Int, height: Int) {
        super.onSurfaceChange(width, height)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mTextureId > 0) GLES30.glDeleteTextures(1, intArrayOf(mTextureId), 0)
    }
}