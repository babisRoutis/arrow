@file:JvmName("ComposeCopyKt")
@file:OptIn(ExperimentalContracts::class)

package arrow.optics

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmName

/**
 * Modifies the value in this [MutableState]
 * by applying the function [block] to the current value.
 */
@Suppress("WRONG_INVOCATION_KIND") // withMutableSnapshot doesn't have a contract
public inline fun <T> MutableState<T>.update(crossinline block: (T) -> T) {
  contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
  Snapshot.withMutableSnapshot {
    value = block(value)
  }
}

/**
 * Modifies the value in this [MutableState]
 * by performing the operations in the [Copy] [block].
 */
public fun <T> MutableState<T>.updateCopy(block: Copy<T>.() -> Unit) {
  update { it.copy(block) }
}

/**
 * Updates the value in this [MutableStateFlow]
 * by performing the operations in the [Copy] [block].
 */
public fun <T> MutableStateFlow<T>.updateCopy(block: Copy<T>.() -> Unit) {
  update { it.copy(block) }
}
