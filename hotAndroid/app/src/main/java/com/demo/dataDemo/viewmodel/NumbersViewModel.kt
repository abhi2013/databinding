package com.demo.dataDemo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.dataDemo.data.NumbersRepository
import com.demo.dataDemo.data.Resource
import com.demo.dataDemo.data.Status
import com.demo.dataDemo.data.model.InputNumber
import com.demo.dataDemo.data.model.InputNumbers
import java.lang.Exception
import javax.inject.Inject

class NumbersViewModel @Inject constructor(val numbersRepository: NumbersRepository) : ViewModel() {
    class Entry<T>(val number: InputNumber<T>) {
        val displayValue: String = number.asString() ?: ""
    }

    private lateinit var numbers: MutableLiveData<Resource<InputNumbers<Int>>>
    lateinit var entries: MediatorLiveData<Resource<List<Entry<Int>>>>

    val kDefaultMean = "Add numbers to get their mean."
    val kDefaultMedian = "Add numbers to get their median."
    var mean = MutableLiveData<String>(kDefaultMean)
    var median = MutableLiveData<String>(kDefaultMedian)
    var newNumber = MutableLiveData<String>()
    var validationError = MediatorLiveData<String>().apply {
        addSource(newNumber) { validate() }
    }

    fun fetch() {
        numbers = numbersRepository.numbers
        entries = MediatorLiveData<Resource<List<Entry<Int>>>>().apply {
            addSource(numbers) { update() }
        }
    }


    private fun update() {
        if (numbers.value!!.status == Status.LOADING) {
            entries.value = Resource.loading(null)
            return
        }

        val numbers = numbers.value?.data?.numbers ?: emptyList<InputNumber<Int>>()
        val newNumbers = numbers.map { number -> Entry(number = number) }
        if (newNumbers.isNotEmpty()) {
            val intNumbers = numbers.map { it.value }.toIntArray()
            val newMean = calcMean(intNumbers)
            mean.postValue("Mean : ${String.format("%.2f", newMean)}")
            val newMedian = calcMedian(intNumbers)
            median.postValue("Median value : $newMedian")
        } else {
            mean.postValue(kDefaultMean)
            median.postValue(kDefaultMedian)
        }
        entries.postValue(Resource.success(newNumbers))
    }

    // Converts the user input text to an Integer and consequently sets the displayable validation error
    private fun validate() {
        try {
            newNumber.value!!.toInt()
            validationError.value = null
        } catch(e : NumberFormatException) {
            validationError.postValue("Please enter a valid integer.")
        }
    }

    fun addNumber() {
        val newNumber = newNumber.value!!.toInt()
        numbersRepository.addNumber(newNumber)
    }

    fun clearNumbers() {
        numbersRepository.clear()
    }

    private fun calcMean(numbers: IntArray): Double {
        return numbers.average()
    }

    private fun calcMedian(numbers: IntArray): Int {
        val count = numbers.size
        val sortedNumbers = numbers.sorted()
        return when (count % 2 == 0) {
            true -> (sortedNumbers[count / 2] + sortedNumbers[(count / 2) - 1]) / 2
            false -> sortedNumbers[(count - 1) / 2]
        }
    }
}