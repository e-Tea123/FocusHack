package com.csci448.focushack.ui.viewmodel.intent

import com.csci448.focushack.ui.viewmodel.effect.FocusHackEffect

sealed class ConsequenceIntent : FocusHackIntent() {
    object ToggleFocus : ConsequenceIntent()
    object ToggleUsageDialogue: ConsequenceIntent()

    data class onNotificationChange(val newValue: Boolean) : ConsequenceIntent()

    data class setConsequenceDetails(val enabled: Boolean,
        val detail: String,
        val onEnabledChange: ConsequenceIntent,
        val id: Int) : ConsequenceIntent()
    object consequenceNotifToggle : ConsequenceIntent()
}