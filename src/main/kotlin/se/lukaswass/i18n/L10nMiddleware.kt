package se.lukaswass.i18n

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.HttpHeaders
import org.jboss.resteasy.reactive.server.ServerRequestFilter
import java.util.Locale

@ApplicationScoped
class L10nMiddleware @Inject constructor(
    private val l10nContext: L10nContext
) {
    @ServerRequestFilter
    fun setLocale(context: ContainerRequestContext) {
        val clientLanguageHeader = context.getHeaderString("Client-Language")
        val acceptLanguageHeader = context.getHeaderString(HttpHeaders.ACCEPT_LANGUAGE)

        var locale: Locale?
        locale = parseClientLanguageHeaderToLocale(clientLanguageHeader)

        if (locale == null) locale = parseAcceptLanguageHeaderToLocale(acceptLanguageHeader)

        l10nContext.locale = locale
    }

    private fun parseLocale(value: String?): Locale? {
        if (value.isNullOrEmpty()) return null

        return Locale.forLanguageTag(value)
    }

    private fun parseClientLanguageHeaderToLocale(headerValue: String?): Locale? {
        return parseLocale(headerValue)
    }

    private fun parseAcceptLanguageHeaderToLocale(headerValue: String?): Locale? {
        if (headerValue.isNullOrBlank()) return null

        val firstLanguage = headerValue.split(",").first()
        return parseLocale(firstLanguage)
    }
}