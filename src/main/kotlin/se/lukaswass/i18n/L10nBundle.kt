package se.lukaswass.i18n

import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.MessageBundle
import io.quarkus.qute.i18n.MessageBundles
import jakarta.enterprise.inject.spi.CDI
import kotlin.reflect.KProperty

class L10nBundle<T : Any>(private val bundleClass: Class<T>) {
    private fun <T> checkIfBundleExists(bundleClass: Class<T>, tag: String): Boolean {
        try {
            val annotation = bundleClass.getAnnotation(MessageBundle::class.java) ?: return false

            val loader = Thread.currentThread().contextClassLoader

            val resource = loader.getResource("messages/${annotation.value}_${tag}.properties")
            if (resource != null) return true
        } catch (_: Throwable) {
        }

        return false
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val l10nContext = CDI.current().select(L10nContext::class.java).get()
        val locale = l10nContext.locale

        if (locale == null) return MessageBundles.get(bundleClass)

        // First, try to get a specific language "en-US" if not available fallback to a less specific language "en"
        // If that is also not available, then fallback to default.
        val tags = setOf<String>(
            locale.toLanguageTag(), // "en-US"
            locale.language // "en"
        )

        for (tag in tags) {
            val localized = Localized.Literal.of(tag)
            if (checkIfBundleExists(bundleClass, tag))
                return MessageBundles.get(bundleClass, localized)
        }

        return MessageBundles.get(bundleClass)
    }

    companion object {
        inline fun <reified T : Any> of() = L10nBundle(T::class.java)
    }
}