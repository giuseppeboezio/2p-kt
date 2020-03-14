package it.unibo.tuprolog.libraries.stdlib.primitive

import it.unibo.tuprolog.core.Numeric
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.solve.ExecutionContext

object Number : TypeTester<ExecutionContext>("number") {
    override fun testType(term: Term): Boolean = term is Numeric
}