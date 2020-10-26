package com.demo.dataDemo.data

import androidx.lifecycle.MutableLiveData
import com.demo.dataDemo.api.DataService
import com.demo.dataDemo.data.model.InputNumbers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NumbersRepository @Inject constructor(val dataService: DataService) {
    var numbers: MutableLiveData<Resource<InputNumbers<Int>>> =
        MutableLiveData(Resource(Status.LOADING, null, null))
    var cache: InputNumbers<Int> = InputNumbers(numbers = emptyArray())

    init {
        dataService.getNumbers(success = {
            cache = it
            numbers.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            numbers.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

    fun reset() {
        cache = InputNumbers(numbers = emptyArray())
        numbers.value = null
    }


}