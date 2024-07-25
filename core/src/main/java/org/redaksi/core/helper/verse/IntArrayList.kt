package org.redaksi.core.helper.verse

class IntArrayList @JvmOverloads constructor(cap: Int = 16) {
    var buf: IntArray
    var len: Int = 0

    init {
        buf = IntArray(cap)
    }

    fun size(): Int {
        return this.len
    }

    private fun expand() {
        val newArray = IntArray(buf.size shl 1)
        System.arraycopy(this.buf, 0, newArray, 0, this.len)
        this.buf = newArray
    }

    fun add(a: Int) {
        if (this.len >= buf.size) {
            expand()
        }

        buf[len++] = a
    }

    fun pop(): Int {
        return buf[--this.len]
    }

    operator fun get(i: Int): Int {
        return buf[i]
    }

    fun set(i: Int, a: Int) {
        buf[i] = a
    }

    /**
     * DANGEROUS. Do not mess with this buffer carelessly.
     * Use this for faster access to the underlying buffer only.
     * The length of the returned array will be the same or larger than [.size].
     */
    fun buffer(): IntArray {
        return buf
    }

    override fun toString(): String {
        val sb = StringBuilder(this.len * 8)
        sb.append('[')
        for (i in 0 until len) {
            sb.append(buf[i])
            if (i != this.len - 1) {
                sb.append(", ") // $NON-NLS-1$
            }
        }
        sb.append(']')
        return sb.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as IntArrayList

        if (len != that.len) return false
        for (i in 0 until len) {
            if (buf[i] != that.buf[i]) return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = buf.contentHashCode()
        result = 31 * result + len
        return result
    }
}
