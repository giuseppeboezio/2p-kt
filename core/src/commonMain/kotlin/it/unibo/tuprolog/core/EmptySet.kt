package it.unibo.tuprolog.core

import it.unibo.tuprolog.core.impl.EmptyListImpl
import it.unibo.tuprolog.core.impl.EmptySetImpl
import kotlin.jvm.JvmStatic
import it.unibo.tuprolog.core.Set as LogicSet

interface EmptySet : Empty, LogicSet {

    override val isEmptySet: Boolean
        get() = true

    override fun freshCopy(): EmptySet = this

    override fun freshCopy(scope: Scope): EmptySet = this

    companion object {
        const val FUNCTOR: String = Empty.EMPTY_SET_FUNCTOR

        @JvmStatic
        operator fun invoke(): EmptySet = EmptySetImpl

        @JvmStatic
        val instance: EmptySet = EmptySetImpl
    }
}
