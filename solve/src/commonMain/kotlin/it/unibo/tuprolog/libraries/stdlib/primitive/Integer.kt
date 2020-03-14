package it.unibo.tuprolog.libraries.stdlib.primitive

import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.solve.ExecutionContext

object Integer : TypeTester<ExecutionContext>("integer") {
    override fun testType(term: Term): Boolean = term is it.unibo.tuprolog.core.Integer
}