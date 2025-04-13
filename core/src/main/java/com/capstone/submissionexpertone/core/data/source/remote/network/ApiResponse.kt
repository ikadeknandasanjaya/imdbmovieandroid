package com.capstone.submissionexpertone.core.data.source.remote.network

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String) : ApiResponse<T>()
    object Empty : ApiResponse<Nothing>() // Ini adalah object, bukan class
}