package ch.tutteli.atrium.api.fluent.en_GB

import ch.tutteli.atrium.api.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Expect

class CharSequenceContainsExactlyAssertionsSpec : ch.tutteli.atrium.specs.integration.CharSequenceContainsExactlyAssertionsSpec(
    AssertionVerbFactory,
    getExactlyTriple(),
    getExactlyIgnoringCaseTriple(),
    getContainsNotPair(),
    "◆ ", "⚬ "
) {

    companion object : CharSequenceContainsSpecBase() {

        private fun getExactlyTriple() = Triple(
            "$contains.$exactly",
            { what: String, times: String -> "$contains $what $exactly $times" },
            Companion::containsExactly
        )

        private fun containsExactly(plant: Expect<CharSequence>, exactly: Int, a: Any, aX: Array<out Any>)
            = plant.contains.exactly(exactly).values(a, *aX)

        private fun getExactlyIgnoringCaseTriple() = Triple(
            "$contains.$ignoringCase.$exactly",
            { what: String, times: String -> "$contains $ignoringCase $what $exactly $times" },
            Companion::containsExactlyIgnoringCase
        )

        private fun containsExactlyIgnoringCase(plant: Expect<CharSequence>, exactly: Int, a: Any, aX: Array<out Any>)
            = plant.contains.ignoringCase.exactly(exactly).values(a, *aX)


        private fun getContainsNotPair() = containsNot to Companion::getErrorMsgContainsNot

        private fun getErrorMsgContainsNot(times: Int)
            = "use $containsNot instead of $exactly($times)"

    }
}
