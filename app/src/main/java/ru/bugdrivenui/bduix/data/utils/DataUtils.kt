package ru.bugdrivenui.bduix.data.utils

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.bugdrivenui.bduix.domain.state.Completable
import ru.bugdrivenui.bduix.domain.state.Result

suspend fun <T : Any> apiCall(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> Result<T>,
): Result<T> = withContext(dispatcher) {
    try {
        block.invoke()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Log.d("apiCall", e.toString())
        Result.Error(e)
    }
}

fun <T> Response<T>.toResult(): Result<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        Result.Success(body)
    } else {
        Log.d("apiCall", "isSuccessful=$isSuccessful, body=$body")
        Result.Error()
    }
}

fun <T, R : Any> Response<T>.toResult(onSuccessMapper: (T) -> R): Result<R> {
    val body = body()
    return if (isSuccessful && body != null) {
        Result.Success(onSuccessMapper(body))
    } else {
        Result.Error()
    }
}

fun <T> Response<T>.toCompletableResult(): Result<Completable> {
    val body = body()
    return if (isSuccessful && (code() == 204 || body != null)) {
        Result.Success(Completable)
    } else {
        Result.Error()
    }
}