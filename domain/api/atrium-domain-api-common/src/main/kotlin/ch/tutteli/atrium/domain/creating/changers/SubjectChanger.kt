package ch.tutteli.atrium.domain.creating.changers

import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.creating.BaseAssertionPlant
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * The access point to an implementation of [SubjectChanger].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val subjectChanger by lazy { loadSingleService(SubjectChanger::class) }

/**
 * Defines the contract to change the subject of an assertion container (e.g. the subject of [Expect]) by creating
 * a new [Expect] whereas the new [Expect] delegates assertion checking to a given original assertion container.
 */
interface SubjectChanger {

    @Deprecated(
        "Do no longer use Assert, use Expect instead - this method was introduced in 0.9.0 to ease the migration from Assert to Expect; will be removed with 1.0.0"
    )
    fun <T, R: Any> unreported(
        originalPlant: BaseAssertionPlant<T, *>,
        transformation: (T) -> R
    ): Assert<R>

    @Deprecated(
        "Do no longer use Assert, use Expect instead - this method was introduced in 0.9.0 to ease the migration from Assert to Expect; will be removed with 1.0.0"
    )
    fun <T, R> unreportedNullable(
        originalPlant: BaseAssertionPlant<T, *>,
        transformation: (T) -> R
    ): AssertionPlantNullable<R>

    /**
     * Changes to a new subject according to the given [transformation] without showing it
     * in reporting and returns a new [Expect] for the new subject.
     *
     * Explained a bit more in depth: it creates a new [Expect] incorporating the given [transformation]
     * whereas the new [Expect] delegates assertion checking to the given [originalAssertionContainer] -
     * the change as such will not be reflected in reporting.
     *
     * This method is useful if you want to make feature assertion(s) but you do not want that the feature is shown up
     * in reporting. For instance, if a class can behave as another class (e.g. `Sequence::asIterable`) or you want to
     * hide a conversion (e.g. `Int::toChar`) then you can use this function.
     *
     * Notice, in case the change to the new subject is not always safe (you assert so but it does not have to be),
     * then you should use [reported] so that the assertion is reflected in reporting.
     *
     * @param originalAssertionContainer the assertion container with the current subject (before the change) --
     *   if you use `ExpectImpl.changeSubject.unreported(...)` within an assertion function (an extension function of
     *   [Expect]) then you usually pass `this` (so the instance of [Expect]) for this parameter.
     * @param transformation Provides the subject.
     *
     * @return the newly created [Expect].
     */
    fun <T, R> unreported(
        originalAssertionContainer: Expect<T>,
        transformation: (T) -> R
    ): Expect<R>


    /**
     * Changes to a new subject according to the given [transformation] but only if the current subject
     * [canBeTransformed] to the new subject -- the change as such is reflected in reporting by the given
     * [description] and [representation].
     *
     * Explained a bit more in depth: it creates a new [Expect] incorporating the given [transformation]
     * whereas the new [Expect] delegates assertion checking to the given [originalAssertionContainer].
     *
     * This method is useful if you want to change the subject whereas the change as such is assertion like as well, so
     * that it should be reported as well. For instance, say you want to change the subject of type `Int?` to `Int`.
     * Since the subject could also be `null` it makes sense to report this assertion instead of failing
     * with an exception.
     *
     * @param originalAssertionContainer the assertion container with the current subject (before the change) --
     *   if you use `ExpectImpl.changeSubject.reported(...)` within an assertion function (an extension function of
     *   [Expect]) then you usually pass `this` (so the instance of [Expect]) for this parameter.
     * @param description Describes the kind of subject change (e.g. in case of a type change `is a`).
     * @param representation Representation of the change (e.g. in case of a type transformation the KClass).
     * @param canBeTransformed Indicates whether it is safe to transform to the new subject.
     * @param transformation Provides the subject.
     * @param subAssertions Optionally, subsequent assertions for the new subject. This is especially useful if the
     *   change fails since we can then already show to you (in error reporting) what you wanted to assert about
     *   the new subject (which gives you more context to the error).
     *
     * @return the newly created [Expect].
     */
    fun <T, R> reported(
        originalAssertionContainer: Expect<T>,
        description: Translatable,
        representation: Any,
        canBeTransformed: (T) -> Boolean,
        transformation: (T) -> R,
        subAssertions: (Expect<R>.() -> Unit)?
    ): Expect<R>
}
