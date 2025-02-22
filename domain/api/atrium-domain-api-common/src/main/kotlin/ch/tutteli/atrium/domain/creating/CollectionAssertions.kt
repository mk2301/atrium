package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.SubjectProvider

/**
 * The access point to an implementation of [CollectionAssertions].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val collectionAssertions by lazy { loadSingleService(CollectionAssertions::class) }


/**
 * Defines the minimum set of assertion functions and builders applicable to [Collection],
 * which an implementation of the domain of Atrium has to provide.
 */
interface CollectionAssertions {
    fun isEmpty(subjectProvider: SubjectProvider<Collection<*>>): Assertion
    fun isNotEmpty(subjectProvider: SubjectProvider<Collection<*>>): Assertion

    fun hasSize(plant: AssertionPlant<Collection<*>>, size: Int): Assertion
    fun size(plant: AssertionPlant<Collection<*>>, assertionCreator: Assert<Int>.() -> Unit): Assertion
}
