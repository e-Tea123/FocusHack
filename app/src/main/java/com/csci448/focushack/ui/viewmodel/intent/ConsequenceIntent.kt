package com.csci448.focushack.ui.viewmodel.intent

import com.csci448.focushack.ui.viewmodel.effect.FocusHackEffect

sealed class ConsequenceIntent : FocusHackIntent() {
    object ToggleFocus : ConsequenceIntent()
}