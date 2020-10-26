package com.demo.dataDemo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.dataDemo.data.NumbersRepository
import com.demo.dataDemo.data.Resource
import com.demo.dataDemo.data.Status
import com.demo.dataDemo.data.model.InputNumber
import com.demo.dataDemo.data.model.InputNumbers
import javax.inject.Inject

class NumbersViewModel @Inject constructor(val numbersRepository: NumbersRepository) : ViewModel() {
    class Entry<T>(val number : InputNumber<T>) {
        val displayValue : String = number.asString() ?: ""
    }

    private lateinit var numbers : MutableLiveData<Resource<InputNumbers<Int>>>
    lateinit var entries : MediatorLiveData<Resource<List<Entry<Int>>>>

    fun fetch() {
        val exampleNumbers = InputNumbers(numbers = arrayOf(InputNumber(1),InputNumber(3),InputNumber(6),InputNumber(5)));
        numbersRepository.dataService.save(exampleNumbers)
        numbers = numbersRepository.numbers
        entries = MediatorLiveData<Resource<List<Entry<Int>>>>().apply {
            addSource(numbers) { update() }
        }
    }

    fun update() {
        if (numbers.value!!.status == Status.LOADING) {
            entries.value = Resource.loading(null)
            return
        }

        val numbers = numbers.value?.data?.numbers ?: emptyArray()
        val newNumbers = numbers.map { number -> Entry(number = number) }
        entries.postValue(Resource.success(newNumbers))
    }
}