package se.lukaswass.i18n

import io.quarkus.qute.i18n.MessageBundle

@MessageBundle("UserMessages")
interface UserMessages {
    fun hello(name: String): String
    fun errorNeedName(): String
    fun error(): String
}