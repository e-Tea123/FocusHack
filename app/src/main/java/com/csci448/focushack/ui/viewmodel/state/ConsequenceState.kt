package com.csci448.focushack.ui.viewmodel.state

import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ConsequenceState(
    var focusEnabled: Boolean = false,
    var showUsageDialogue: Boolean = false,
    var notificationsEnabled: Boolean = true,

    var detailDetail: String = "",
    var detailEnabled: Boolean = true,
    @Transient
    var detailOnEnabledChange: ConsequenceIntent = ConsequenceIntent.ToggleUsageDialogue,
    var detailID: Int = 0,

    var consequencesEnabled: MutableList<Boolean> = mutableListOf(true, true, true),

    var launchConsequence: Int = 0
) : FocusHackState