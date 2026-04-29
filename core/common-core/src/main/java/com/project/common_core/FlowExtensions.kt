package com.project.common_core

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(FlowPreview::class)
fun <T, C> Flow<List<T>>.filterWith(
    categoryFlow: Flow<C?>,
    queryFlow: Flow<String>,
    getCategory: (T) -> C,
    getName: (T) -> String
): Flow<List<T>> {
    return flatMapLatest { list ->
        categoryFlow.combine(
            queryFlow.debounce(300L)
        ) { category, query ->
            list.filter { item ->
                (category == null || getCategory(item) == category) &&
                        getName(item).contains(query)
            }
        }
    }
}
