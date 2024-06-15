package com.kyungeun.offlinecachingsample.util

import kotlinx.coroutines.flow.*
import timber.log.Timber

inline fun <ResultType, RequestType> networkBoundResource(
    // responsible for getting data from database
    crossinline query: () -> Flow<ResultType>,
    // responsible for fetching new data from rest api
    crossinline fetch: suspend () -> RequestType,
    // responsible for taking data from fetch and saving it to database
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    // responsible for deciding whether to fetch new data from rest api
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    // responsible for handling error when fetching data from rest api
    crossinline onFetchFailed: (Throwable) -> Unit = { Timber.e(it) }
) = flow {

    // Get the current data from the database
    val data = query().first()

    // Check if we should fetch new data from the network
    val flow = if (shouldFetch(data)) {
        // Emit loading state with the current data
        emit(Resource.Loading(data))

        try {
            // Fetch new data from the network
            val fetchedResult = fetch()
            // Save the fetched data to the database
            saveFetchResult(fetchedResult)
            // Emit the newly saved data from the database
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            // Handle any errors during the network fetch
            onFetchFailed(throwable)
            // Emit an error state with the current data from the database
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        // Emit the current data from the database as a success state
        query().map { Resource.Success(it) }
    }

    // Emit all the values from the flow
    emitAll(flow)
}