package ru.bugdrivenui.bduix.data.repository

import kotlinx.coroutines.Dispatchers
import ru.bugdrivenui.bduix.data.api.TestApi
import ru.bugdrivenui.bduix.data.utils.apiCall
import ru.bugdrivenui.bduix.data.mapper.TestMapper
import ru.bugdrivenui.bduix.data.utils.toResult
import ru.bugdrivenui.bduix.domain.model.TestModel
import ru.bugdrivenui.bduix.domain.repository.ITestRepository
import ru.bugdrivenui.bduix.domain.state.Result
import ru.bugdrivenui.bduix.domain.state.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val testApi: TestApi,
    private val mapper: TestMapper,
) : ITestRepository {

    override suspend fun getTestData(): Result<TestModel> {
        return apiCall(Dispatchers.IO) {
            testApi.getTestData()
                .toResult()
                .map(mapper::map)
        }
    }
}