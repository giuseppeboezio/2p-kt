package it.unibo.tuprolog.solve

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Substitution
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.libraries.Libraries
import it.unibo.tuprolog.primitive.Signature
import it.unibo.tuprolog.solve.exception.TuPrologRuntimeException
import it.unibo.tuprolog.solve.solver.ExecutionContextImpl
import it.unibo.tuprolog.solve.solver.statemachine.TimeDuration
import it.unibo.tuprolog.solve.solver.statemachine.TimeInstant
import it.unibo.tuprolog.solve.solver.statemachine.currentTime
import it.unibo.tuprolog.theory.ClauseDatabase

/** A base class for Solve requests and responses */
sealed class Solve {

    /** Class representing a Request to be full-filled by the Solver */
    data class Request<out C : ExecutionContext>(
            /** Signature of the goal to be solved in this [Request] */
            val signature: Signature,
            /** Arguments with which the goal is invoked in this [Request] */
            val arguments: List<Term>,
            /** The user's query, i.e. the 0-level goal triggering resolution */
            val initialSolverQuery: Struct,
            /** The context that's current at Request making */
            val context: C,
            /** The time instant when the request was submitted for resolution */
            val requestIssuingInstant: TimeInstant = currentTime(),
            /** The execution max duration after which the computation should end, because no more useful */
            val executionMaxDuration: TimeDuration = Long.MAX_VALUE
    ) : Solve() {
        init {
            when {
                signature.vararg -> require(arguments.count() >= signature.arity) {
                    "Trying to create Solve.Request of signature `$signature` with not enough arguments ${arguments.toList()}"
                }
                else -> require(arguments.count() == signature.arity) {
                    "Trying to create Solve.Request of signature `$signature` with wrong number of arguments ${arguments.toList()}"
                }
            }
            require(requestIssuingInstant >= 0) { "The request issuing instant can't be negative: $requestIssuingInstant" }
            require(executionMaxDuration >= 0) { "The execution max duration can't be negative: $executionMaxDuration" }
        }

        /** The current query [Struct] of this request */
        val query: Struct by lazy { signature withArgs arguments }

        /** Checks for equality only by means of [signature] and [arguments] fields */
        fun equalSignatureAndArgs(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Request<*>

            if (signature != other.signature) return false
            if (arguments != other.arguments) return false

            return true
        }

        /** Creates a new [Response] to this Request */
        fun replyWith(
                solution: Solution,
                libraries: Libraries? = null,
                flags: Map<Atom, Term>? = null,
                staticKB: ClauseDatabase? = null,
                dynamicKB: ClauseDatabase? = null
        ) = Response(solution, libraries, flags, staticKB, dynamicKB)

        /** Creates a new successful [Response] to this Request, with substitution */
        fun replySuccess(substitution: Substitution.Unifier = Substitution.empty(), libraries: Libraries? = null, flags: Map<Atom, Term>? = null, staticKB: ClauseDatabase? = null, dynamicKB: ClauseDatabase? = null) =
                replyWith(Solution.Yes(query, substitution), libraries, flags, staticKB, dynamicKB)

        /** Creates a new failed [Response] to this Request */
        fun replyFail(libraries: Libraries? = null, flags: Map<Atom, Term>? = null, staticKB: ClauseDatabase? = null, dynamicKB: ClauseDatabase? = null) =
                replyWith(Solution.No(query), libraries, flags, staticKB, dynamicKB)

        /** Creates a new halt [Response] to this Request, with cause exception */
        fun replyException(exception: TuPrologRuntimeException, libraries: Libraries? = null, flags: Map<Atom, Term>? = null, staticKB: ClauseDatabase? = null, dynamicKB: ClauseDatabase? = null) =
                replyWith(Solution.Halt(query, exception), libraries, flags, staticKB, dynamicKB)

        /** Creates a new successful or failed [Response] depending on [condition]; to be used when the substitution doesn't change */
        fun replyWith(condition: Boolean, libraries: Libraries? = null, flags: Map<Atom, Term>? = null, staticKB: ClauseDatabase? = null, dynamicKB: ClauseDatabase? = null) = when (condition) {
            true -> replySuccess(libraries = libraries, flags = flags, staticKB = staticKB, dynamicKB = dynamicKB)
            false -> replyFail(libraries, flags, staticKB, dynamicKB)
        }
    }


    /** Class representing a Response, from the Solver, to a [Solve.Request] */
    data class Response(
            /** The solution attached to the response */
            val solution: Solution,
            /** The set of loaded libraries after request execution (use `null` in case nothing changed) */
            val libraries: Libraries? = null,
            /** The map of loaded flags after request execution (use `null` in case nothing changed) */
            val flags: Map<Atom, Term>? = null,
            /** The Static KB after request execution (use `null` in case nothing changed) */
            val staticKB: ClauseDatabase? = null,
            /** The Dynamic KB after request execution (use `null` in case nothing changed) */
            val dynamicKB: ClauseDatabase? = null,

            // TODO replace with ExecutionFlowModification only, and update above methods and tests!!
            val context: ExecutionContextImpl? = null
    ) : Solve()
}
