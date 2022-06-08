package com.hungngo.themusicapp.base

import com.hungngo.themusicapp.utils.DataResult
import com.hungngo.themusicapp.utils.dispatchers.BaseDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository : KoinComponent {

    private val dispatchersProvider = get<BaseDispatcherProvider>()

    protected suspend fun <R> withResultContext(
        context: CoroutineContext = dispatchersProvider.io(),
        requestBlock: suspend CoroutineScope.() -> R
    ): DataResult<R> = withContext(context) {
        return@withContext try {
            val response = requestBlock()
            DataResult.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.Error(e.message.toString())
        }
    }
}
