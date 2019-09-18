package com.example.stripedemokotlin.utils

open class Event<out T>(private var content: T?) {

    var hasBeenHandled = false
        private set

    fun setHandled(){
        hasBeenHandled = true
        content = null
    }

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() = content
}
