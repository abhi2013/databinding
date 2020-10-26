package com.demo.dataDemo.data.model

data class InputNumbers<T>(val numbers : MutableList<InputNumber<T>>) {

    fun add(number : T){
        numbers.add(InputNumber(number))
    }

    fun clear(){
        numbers.clear()
    }

}

data class InputNumber<out T>(val value : T){

    fun asString(): String? {
        return value.toString()
    }
}
