package it.unibo.tuprolog.core

open class InvalidClauseException(val term: Term, cause: Throwable? = null) : TuprologException(cause) {
    override val message: String?
        get() = "Term `$term` is not a valid clause"
}