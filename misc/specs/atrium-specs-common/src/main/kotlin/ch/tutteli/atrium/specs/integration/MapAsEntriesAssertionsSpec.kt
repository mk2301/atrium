package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.cc.en_GB.*
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.specs.verbs.AssertionVerbFactory
import org.spekframework.spek2.Spek

abstract class MapAsEntriesAssertionsSpec(
    verbs: AssertionVerbFactory,
    asEntriesFunName: String,
    asEntriesFeature: Feature0<Map<String, Int>, Set<Map.Entry<String, Int>>>,
    asEntries: Fun1<Map<String, Int>, Assert<Set<Map.Entry<String, Int>>>.() -> Unit>,
    describePrefix: String = "[Atrium] "
) : Spek({

    include(object : SubjectLessSpec<Map<String, Int>>("$describePrefix[$asEntriesFunName] ",
        asEntriesFeature.forSubjectLess().adjustName { "$it feature"  },
        asEntries.forSubjectLess { contains("a" to 1) }
    ) {})

    val holdingSubject: Map<String, Int> = mapOf("a" to 1, "c" to 3, "e" to 5, "g" to 7)
    val failingSubject: Map<String, Int> = mapOf("b" to 2, "d" to 4, "f" to 6, "h" to 8)

    include(object : CheckingAssertionSpec<Map<String, Int>>(verbs, "$describePrefix[$asEntriesFunName] ",
        asEntriesFeature.forChecking()
        checkingTriple(asEntriesFunName, { asEntriesFeature(this).contains { isKeyValue("g", 7) } }, holdingSubject, failingSubject),
        checkingTriple(asEntriesWithCreatorFun, { asEntries(this){ contains { isKeyValue("g", 7) } } }, holdingSubject, failingSubject)
    ) {})

    fun describeFun(vararg funName: String, body: SpecBody.() -> Unit) =
        describeFun(describePrefix, funName, body = body)

    describeFun(asEntriesFunName) {
        test("transformation can be applied and an assertion made") {
            verbs.checkImmediately(mapOf(1 to "a", 2 to "b")).asEntries().contains.inAnyOrder.only.entries(
                { isKeyValue(2, "b") },
                { key{ isGreaterThan(0)}.and.value.startsWith("a") }
            )
        }
    }
})
