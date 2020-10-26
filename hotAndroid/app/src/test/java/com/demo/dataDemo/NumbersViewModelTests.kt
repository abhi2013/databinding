package com.demo.dataDemo

import androidx.lifecycle.MutableLiveData
import com.demo.dataDemo.data.NumbersRepository
import com.demo.dataDemo.data.Resource
import com.demo.dataDemo.data.Status
import com.demo.dataDemo.data.model.InputNumber
import com.demo.dataDemo.data.model.InputNumbers
import com.demo.dataDemo.viewmodel.NumbersViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class NumbersViewModelTests {
    private var repository = Mockito.mock(NumbersRepository::class.java)
    private var viewModel: NumbersViewModel

    private val exampleNumbers = InputNumbers(
        numbers = mutableListOf(
            InputNumber(23),
            InputNumber(37),
            InputNumber(42),
            InputNumber(55),
            InputNumber(60),
            InputNumber(72),
            InputNumber(83),
            InputNumber(91)
        )
    )

    init {
        var numbers: MutableLiveData<Resource<InputNumbers<Int>>> =
            MutableLiveData(Resource(Status.SUCCESS, exampleNumbers, null))
        // Initialise the repository with some example numbers
        // These are not injected using data-service during unit tests since that would require mocking data-service
        repository.numbers = numbers
        viewModel = NumbersViewModel(repository)
    }

    @Test
    fun correctMeanAndMedian() {
        // Checks Mean and Median calculations to the second decimal place
        val numbers = exampleNumbers.numbers.map { it.value }.toIntArray()
        assert(viewModel.calcMean(numbers) - 57.88 < 0.01)
        assert(viewModel.calcMedian(numbers) - 57.5 == 0.0)
    }

    @Test
    fun correctEntries() {
        // Tests that the view model correctly creates entries from the repository data
        viewModel.fetch()
        val entries = viewModel.entries.getOrAwaitValue()
        assertEquals(entries.data!!.size, exampleNumbers.numbers.size)
    }
}