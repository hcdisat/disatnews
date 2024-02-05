package com.hcdisat.common.exceptions

class MaxResultsReachedException(
    val code: Int,
    override val message: String = "Max results reached. http request code $code"
) : RuntimeException(message) {
    companion object {
        val MAX_RESULTS_REACHED_CODE = listOf(426, 429)
    }
}