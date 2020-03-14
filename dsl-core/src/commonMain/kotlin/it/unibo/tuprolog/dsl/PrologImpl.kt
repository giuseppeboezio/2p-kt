package it.unibo.tuprolog.dsl

import it.unibo.tuprolog.core.Scope
import it.unibo.tuprolog.core.Term

internal class PrologImpl : Prolog, Scope by Scope.empty() {

    private val anyToTermConverter = AnyToTermConverter.of(this);

    override fun Any.toTerm(): Term =
        anyToTermConverter.toTerm(this)
}