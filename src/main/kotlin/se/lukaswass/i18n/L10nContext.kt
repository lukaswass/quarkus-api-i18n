package se.lukaswass.i18n

import jakarta.enterprise.context.RequestScoped
import java.util.Locale

@RequestScoped
class L10nContext {
    var locale: Locale? = null
}