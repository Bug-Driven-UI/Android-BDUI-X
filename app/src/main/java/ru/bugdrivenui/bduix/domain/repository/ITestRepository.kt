package ru.bugdrivenui.bduix.domain.repository

import ru.bugdrivenui.bduix.domain.model.TestModel
import ru.bugdrivenui.bduix.domain.state.Result

interface ITestRepository {

    suspend fun getTestData(): Result<TestModel>
}