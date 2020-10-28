package com.grades

import java.util.*

internal open class MultiKeyPair(var key: Any, var value: Any?) {
    override fun hashCode(): Int {
        return key.hashCode()
    }

    internal inner class MultiKeyList(key: Any) : MultiKeyPair(key, null) {
        var list = ArrayList<MultiKeyPair?>()
        override fun equals(obj: Any?): Boolean {
            list.add(obj as MultiKeyPair?)
            return false
        }
    }

    fun main(args: Array<String?>?) {
        val set = HashSet<MultiKeyPair>()
        set.add(MultiKeyPair("A", "a1"))
        set.add(MultiKeyPair("A", "a2"))
        set.add(MultiKeyPair("B", "b1"))
        set.add(MultiKeyPair("A", "a3"))
        val o = MultiKeyList("A")
        set.contains(o)
        for (pair in o.list) {
            println(pair!!.value)
        }
    }
}