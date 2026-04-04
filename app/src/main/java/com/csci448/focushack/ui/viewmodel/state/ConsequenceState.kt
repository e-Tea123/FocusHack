package com.csci448.focushack.ui.viewmodel.state

import kotlinx.serialization.Serializable

@Serializable
data class ConsequenceState(
    var focusEnabled: Boolean = false
) : FocusHackState