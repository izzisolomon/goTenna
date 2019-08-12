package com.gotenna.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleTypeCallback<CallbackType>(private var receiver: Receiver<CallbackType>) : Callback<CallbackType> {

    private fun onSuccess(callbackType: CallbackType): CallbackType {
        return callbackType
    }

    override fun onResponse(call: Call<CallbackType>, response: Response<CallbackType>) {
        if (response.isSuccessful && response.body() != null) {
            receiver.onRequestSucceeded(onSuccess(response.body()!!))
        } else {
            receiver.onRequestFailed(response.code())
        }
    }

    override fun onFailure(call: Call<CallbackType>, t: Throwable) {
        receiver.onRequestFailed(0)
    }
}