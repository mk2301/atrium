package ch.tutteli.atrium.translations

import ch.tutteli.atrium.reporting.BUG_REPORT_URL
import ch.tutteli.atrium.reporting.translating.StringBasedTranslatable

/**
 * Contains translations which are used in error like messages.
 */
enum class ErrorMessages(override val value: String) : StringBasedTranslatable {
    REPRESENTATION_BASED_ON_SUBJECT_NOT_DEFINED("kann Representation NICHT evaluieren, da sie auf dem Subjekt der Behauptung beruht, welches nicht definiert ist."),
    SUBJECT_ACCESSED_TOO_EARLY("Konnte die zusätzlichen Aussagen (Assertions) nicht auswerten; das Subjekt (subject) wurde zu früh verwendet. Bitte erfassen Sie einen Bug-Report unter $BUG_REPORT_URL inklusive Stacktrace wenn möglich -- vielen Dank."),
}
