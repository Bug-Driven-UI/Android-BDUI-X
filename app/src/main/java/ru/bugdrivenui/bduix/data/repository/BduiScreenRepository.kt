package ru.bugdrivenui.bduix.data.repository

import ru.bugdrivenui.bduix.data.model.BduiScreenResponse
import ru.bugdrivenui.bduix.domain.repository.IBduiScreenRepository
import ru.bugdrivenui.bduix.domain.state.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BduiScreenRepository @Inject constructor(

) : IBduiScreenRepository {

    override suspend fun getScreen(): Result<BduiScreenResponse> {
        TODO("Not yet implemented")
    }
}