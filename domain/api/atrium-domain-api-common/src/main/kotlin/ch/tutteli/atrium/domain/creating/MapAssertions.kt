package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.*
import kotlin.reflect.KClass

/**
 * The access point to an implementation of [MapAssertions].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val mapAssertions by lazy { loadSingleService(MapAssertions::class) }


/**
 * Defines the minimum set of assertion functions and builders applicable to [Map],
 * which an implementation of the domain of Atrium has to provide.
 */
interface MapAssertions {
    fun <K, V: Any, T : Map<out K, V?>> contains(
        assertionContainer: Expect<T>,
        valueType: KClass<V>,
        keyValuePairs: List<Pair<K, V?>>
    ): Assertion

    fun <K, V : Any, T : Map<out K, V?>> containsKeyWithValueAssertions(
        assertionContainer: Expect<T>,
        valueType: KClass<V>,
        keyValues: List<Pair<K, (Expect<V>.() -> Unit)?>>
    ): Assertion

    fun <K> containsKey(subjectProvider: SubjectProvider<Map<out K, *>>, key: K): Assertion
    fun <K> containsNotKey(subjectProvider: SubjectProvider<Map<out K, *>>, key: K): Assertion

    fun isEmpty(subjectProvider: SubjectProvider<Map<*, *>>): Assertion
    fun isNotEmpty(subjectProvider: SubjectProvider<Map<*, *>>): Assertion


    fun hasSize(plant: AssertionPlant<Map<*, *>>, size: Int): Assertion

    fun <K> keys(plant: AssertionPlant<Map<out K, *>>, assertionCreator: AssertionPlant<Set<K>>.() -> Unit): Assertion
    fun <V> values(plant: AssertionPlant<Map<*, V>>, assertionCreator: AssertionPlant<Collection<V>>.() -> Unit): Assertion


    @Deprecated("Switch from Assert to Expect; will be removed with 1.0.0")
    fun <K, V> contains(plant: AssertionPlant<Map<out K, V>>, keyValuePairs: List<Pair<K, V>>): Assertion
    @Deprecated("Switch from Assert to Expect; will be removed with 1.0.0")
    fun <K, V: Any> containsKeyWithValueAssertions(plant: AssertionPlant<Map<out K, V?>>, keyValues: List<Pair<K, (Assert<V>.() -> Unit)?>>): Assertion
    @Deprecated("Switch from Assert to Expect; will be removed with 1.0.0")
    fun <K, V: Any> getExisting(plant: AssertionPlant<Map<out K, V>>, key: K): AssertionPlant<V>
    @Deprecated("Switch from Assert to Expect; will be removed with 1.0.0")
    fun <K, V: Any> getExisting(plant: AssertionPlant<Map<out K, V>>, key: K, assertionCreator: AssertionPlant<V>.() -> Unit): Assertion
    @Deprecated("Switch from Assert to Expect and use getExisting instead; will be removed with 1.0.0")
    fun <K, V> getExistingNullable(plant: AssertionPlant<Map<out K, V>>, key: K): AssertionPlantNullable<V>
    @Deprecated("Switch from Assert to Expect and use getExisting instead; will be removed with 1.0.0")
    fun <K, V> getExistingNullable(plant: AssertionPlant<Map<out K, V>>, key: K, assertionCreator: AssertionPlantNullable<V>.() -> Unit): Assertion
}
