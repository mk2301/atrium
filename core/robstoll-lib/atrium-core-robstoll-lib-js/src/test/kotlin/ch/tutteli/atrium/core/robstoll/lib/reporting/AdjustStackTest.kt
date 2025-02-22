package ch.tutteli.atrium.core.robstoll.lib.reporting

import ch.tutteli.atrium.api.cc.en_GB.*
import ch.tutteli.atrium.core.polyfills.stackBacktrace
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.reporting.AtriumErrorAdjuster
import ch.tutteli.atrium.reporting.reporter
import ch.tutteli.atrium.verbs.internal.AssertionVerb
import ch.tutteli.atrium.verbs.internal.assert
import ch.tutteli.atrium.verbs.internal.expect
import kotlin.test.Test

class AdjustStackTest {

    @Test
    fun noOp_containsMochaAndAtrium() {
        expect {
            assertNoOp(1).toBe(2)
        }.toThrow<AssertionError> {
            property(AssertionError::stackBacktrace).contains(
                { contains("mocha") },
                { contains("atrium-core-api-js.js") }
            )
        }
    }

    @Test
    fun removeRunner_containsAtriumButNotMocha() {
        expect {
            assertRemoveRunner(1).toBe(2)
        }.toThrow<AssertionError> {
            property(AssertionError::stackBacktrace)
                .containsNot.entry { contains("mocha") }
                .contains { contains("atrium-core-api-js.js") }
        }
    }

    @Test
    fun removeRunner_containsAtriumButNotMochaInCause() {
        val adjuster = AssertImpl.coreFactory.newRemoveRunnerAtriumErrorAdjuster()
        val throwable = IllegalArgumentException("hello", UnsupportedOperationException("world"))
        adjuster.adjust(throwable)
        assert(throwable.cause!!.stackBacktrace)
            .containsNot.entry { contains("mocha") }
            .contains { contains("atrium-core-robstoll-lib-js") }
    }

    @Test
    fun removeAtrium_containsMochaButNotAtrium() {
        expect {
            assertRemoveAtrium(1).toBe(2)
        }.toThrow<AssertionError> {
            property(AssertionError::stackBacktrace)
                .contains { contains("mocha") }
                .containsNot.entry { contains("atrium-core-api-js.js") }
        }
    }

    @Test
    fun removeAtrium_containsMochaButNotAtriumInCause() {
        val adjuster = AssertImpl.coreFactory.newRemoveAtriumFromAtriumErrorAdjuster()
        val throwable = IllegalArgumentException("hello", UnsupportedOperationException("world"))
        adjuster.adjust(throwable)
        assert(throwable.cause!!.stackBacktrace)
            .contains { contains("mocha") }
            .containsNot.entry { contains("atrium-core-robstoll-lib-js") }
    }

    private fun <T : Any> assertNoOp(subject: T) = createAssert(
        subject, AssertImpl.coreFactory.newNoOpAtriumErrorAdjuster()
    )

    private fun <T : Any> assertRemoveRunner(subject: T) = createAssert(
        subject, AssertImpl.coreFactory.newRemoveRunnerAtriumErrorAdjuster()
    )

    private fun <T : Any> assertRemoveAtrium(subject: T) = createAssert(
        subject, AssertImpl.coreFactory.newRemoveAtriumFromAtriumErrorAdjuster()
    )

    @Suppress("DEPRECATION")
    private fun <T : Any> createAssert(subject: T, adjuster: AtriumErrorAdjuster) =
        AssertImpl.coreFactory.newReportingPlant(
            AssertionVerb.ASSERT, { subject },
            AssertImpl.coreFactory.newThrowingAssertionChecker(DelegatingReporter(reporter, adjuster))
        )
}
