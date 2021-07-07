package com.chad.demo.random.gl

class FloatArrayEx {

    companion object {

        fun from(vararg array: FloatArray): FloatArray {

            var size = 0
            array.forEach {
                size += it.size
            }

            val ret = FloatArray(size)
            var index = 0
            array.forEach {
                System.arraycopy(it, 0, ret, index, it.size)
                index += it.size
            }
            return ret
        }
    }
}