package com.demo.dataDemo.data

import androidx.lifecycle.MutableLiveData
import com.demo.dataDemo.api.DataService
import com.demo.dataDemo.data.model.InputNumbers
import javax.inject.Inject
import javax.inject.Singleton

// This class is open to allow for mocking when unit-testing
@Singleton
open class NumbersRepository @Inject constructor(val dataService: DataService) {
    var numbers: MutableLiveData<Resource<InputNumbers<Int>>> =
        MutableLiveData(Resource(Status.LOADING, null, null))
    var cache: InputNumbers<Int> = InputNumbers(numbers = mutableListOf())

    init {
        dataService.getNumbers(success = {
            cache = it
            numbers.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            numbers.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

    fun reset() {
        cache = InputNumbers(numbers = mutableListOf())
        numbers.value = null
    }

    fun addNumber(number : Int) {
        cache.add(number)
        numbers.postValue(Resource(Status.LOADING, cache, null))
        dataService.updateNumbers(cache, success = {
            cache = it
            numbers.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            numbers.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

    fun clear() {
        cache.clear()
        numbers.postValue(Resource(Status.LOADING, cache, null))
        dataService.updateNumbers(cache, success = {
            cache = it
            numbers.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            numbers.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

}