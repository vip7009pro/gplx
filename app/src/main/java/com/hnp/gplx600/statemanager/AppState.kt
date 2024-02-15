package com.hnp.gplx600.statemanager

data class AppState(
  val counter: Int = 0
)

sealed class AppAction {
  object IncrementCounter: AppAction()
}
fun reduce(currentState: AppState, action: AppAction): AppState {
  return when (action) {
    is AppAction.IncrementCounter -> currentState.copy(counter = currentState.counter + 1)
    // handle other actions
  }
}