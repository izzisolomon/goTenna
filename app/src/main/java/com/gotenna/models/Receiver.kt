package com.gotenna.models

interface Receiver<T> {
    fun onRequestSucceeded(t: T)
    fun onRequestFailed(statusCode: Int)
}
