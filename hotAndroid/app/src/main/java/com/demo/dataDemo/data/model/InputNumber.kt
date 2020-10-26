package com.demo.dataDemo.data.model

data class InputNumbers<T>(val numbers : Array<InputNumber<T>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InputNumbers<*>

        if (!numbers.contentEquals(other.numbers)) return false

        return true
    }

    override fun hashCode(): Int {
        return numbers.contentHashCode()
    }
}

data class InputNumber<out T>(val value : T){

    fun asString(): String? {
        return value.toString()
    }
}
