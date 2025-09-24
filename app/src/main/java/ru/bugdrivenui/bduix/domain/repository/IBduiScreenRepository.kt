package ru.bugdrivenui.bduix.domain.repository

import ru.bugdrivenui.bduix.data.model.BduiScreenResponse
import ru.bugdrivenui.bduix.domain.state.Result

interface IBduiScreenRepository {

    suspend fun getScreen(/* params */): Result<BduiScreenResponse>
}