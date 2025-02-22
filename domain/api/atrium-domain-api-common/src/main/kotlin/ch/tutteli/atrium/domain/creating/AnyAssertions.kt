package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.*
import kotlin.reflect.KClass

/**
 * The access point to an implementation of [AnyAssertions].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val anyAssertions by lazy { loadSingleService(AnyAssertions::class) }


/**
 * Defines the minimum set of assertion functions and builders applicable to [Any] type,
 * which an implementation of the domain of Atrium has to provide.
 */
interface AnyAssertions {

    fun <T : Any> toBe(subjectProvider: SubjectProvider<T>, expected: T): Assertion
    fun <T> notToBe(subjectProvider: SubjectProvider<T>, expected: T): Assertion
    fun <T> isSame(subjectProvider: SubjectProvider<T>, expected: T): Assertion
    fun <T> isNotSame(subjectProvider: SubjectProvider<T>, expected: T): Assertion

    fun <T : Any?> toBeNull(subjectProvider: SubjectProvider<T>): Assertion

    fun <T : Any> toBeNullable(
        assertionContainer: Expect<T?>,
        type: KClass<T>,
        expectedOrNull: T?
    ): Assertion

    fun <T : Any> toBeNullIfNullGivenElse(
        assertionContainer: Expect<T?>,
        type: KClass<T>,
        assertionCreatorOrNull: (Expect<T>.() -> Unit)?
    ): Assertion

    /**
     * Convenience method for nullable-types which delegates to [isA].
     */
    fun <T : Any> notToBeNull(assertionContainer: Expect<T?>, subType: KClass<T>) = isA(assertionContainer, subType)

    /**
     * Convenience method for nullable-types which delegates to [isA].
     */
    fun <T : Any> notToBeNull(
        assertionContainer: Expect<T?>,
        type: KClass<T>,
        assertionCreator: Expect<T>.() -> Unit
    ) = isA(assertionContainer, type, assertionCreator)


    fun <TSub : Any> isA(assertionContainer: Expect<out Any?>, subType: KClass<TSub>): Expect<TSub>
    fun <TSub : Any> isA(
        assertionContainer: Expect<out Any?>,
        subType: KClass<TSub>,
        assertionCreator: Expect<TSub>.() -> Unit
    ): Expect<TSub>


    @Deprecated("Switch from Assert to Expect and use toBeNullable; will be removed with 1.0.0")
    fun <T : Any> isNullable(
        plant: AssertionPlantNullable<T?>,
        type: KClass<T>,
        expectedOrNull: T?
    ): Assertion

    @Deprecated("Switch from Assert to Expect and use notTobeNull; will be removed with 1.0.0")
    fun <T : Any> isNotNull(
        plant: AssertionPlantNullable<T?>,
        type: KClass<T>,
        assertionCreator: AssertionPlant<T>.() -> Unit
    ): Assertion

    @Deprecated("Switch from Assert to Expect and use toBe; will be removed with 1.0.0")
    fun <T : Any> isNotNullBut(
        plant: AssertionPlantNullable<T?>,
        type: KClass<T>,
        expected: T
    ): Assertion

    @Deprecated("Switch from Assert to Expect and use toBeNullIfNullGivenElse; will be removed with 1.0.0")
    fun <T : Any> isNullIfNullGivenElse(
        plant: AssertionPlantNullable<T?>,
        type: KClass<T>,
        assertionCreatorOrNull: (AssertionPlant<T>.() -> Unit)?
    ): Assertion
}

