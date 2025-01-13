package com.andresp.ontoptest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andresp.ontoptest.presentation.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    companion object {
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    fun getAllCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(initialKey = 1, config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                CharacterPagingSource(apiService)
            }).flow
    }
}