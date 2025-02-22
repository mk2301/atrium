package ch.tutteli.atrium.domain.builders.creating.collectors

import ch.tutteli.atrium.assertions.AssertionGroup
import ch.tutteli.atrium.assertions.ExplanatoryAssertionGroupType
import ch.tutteli.atrium.assertions.builders.AssertionsOption
import ch.tutteli.atrium.core.Option
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.ExpectImpl

/**
 * Collects the assertions [assertionCreator] and uses them as [AssertionGroup.assertions].
 */
fun <T, G : ExplanatoryAssertionGroupType, R> AssertionsOption<G, R>.collectAssertions(
    assertionContainer: Expect<T>,
    assertionCreator: (Expect<T>.() -> Unit)?
) = collectAssertions(assertionContainer.maybeSubject, assertionCreator)

/**
 * Collects the assertions [assertionCreator] and uses them as [AssertionGroup.assertions].
 */
fun <T, G : ExplanatoryAssertionGroupType, R> AssertionsOption<G, R>.collectAssertions(
    maybeSubject: Option<T>,
    assertionCreator: (Expect<T>.() -> Unit)?
) = withAssertions(ExpectImpl.collector.forExplanation.throwIfNoAssertionIsCollected.collect(maybeSubject, assertionCreator))
