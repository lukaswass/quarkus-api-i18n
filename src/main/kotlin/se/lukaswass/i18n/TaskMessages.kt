package se.lukaswass.i18n

import io.quarkus.qute.i18n.MessageBundle

@MessageBundle("TaskMessages")
interface TaskMessages {
    fun sayTask(taskName: String): String
    fun error(): String
}