package com.example.stripedemokotlin.utils

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource<T>(val status: Status, val data: T?, val message: String?, val type: ApiType) {



    companion object {

        internal fun <T> success(@Nullable data: T?, @NonNull type: ApiType): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                type
            )
        }

        fun <T> error(msg: String, data: T?, type: ApiType): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                type
            )
        }

    }


    enum class Status {
        SUCCESS, ERROR
    }

}