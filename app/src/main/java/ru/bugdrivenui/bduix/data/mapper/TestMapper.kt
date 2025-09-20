package ru.bugdrivenui.bduix.data.mapper

import ru.bugdrivenui.bduix.data.model.TestResponse
import ru.bugdrivenui.bduix.domain.model.TestModel
import javax.inject.Inject

class TestMapper @Inject constructor() {

    fun map(response: TestResponse): TestModel {
        return TestModel(
            testData = response.testData,
        )
    }
}