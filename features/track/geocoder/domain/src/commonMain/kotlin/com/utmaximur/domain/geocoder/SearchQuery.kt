package com.utmaximur.domain.geocoder

data class SearchQuery(
    val query: String,
) {
    val isReadyToRequest = query.length >= MIN_QUERY_LENGTH && query.isNotEmpty()
    companion object {
        private const val MIN_QUERY_LENGTH = 2
    }
}
