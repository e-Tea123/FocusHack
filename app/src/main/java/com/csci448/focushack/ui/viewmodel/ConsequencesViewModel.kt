package com.csci448.focushack.ui.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.serialization.saved
import androidx.lifecycle.viewModelScope
import com.csci448.focushack.ui.viewmodel.effect.ConsequenceEffect
import com.csci448.focushack.ui.viewmodel.intent.ConsequenceIntent
import com.csci448.focushack.ui.viewmodel.state.ConsequenceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConsequencesViewModel
internal constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), IViewModelContract<ConsequenceState, ConsequenceIntent, ConsequenceEffect> {

    companion object{

    }

    private var _savedState: ConsequenceState by savedStateHandle.saved(
        key = "SAVED_CARD_STATE",
        init = { ConsequenceState() }
    )

    private val _stateFlow: MutableStateFlow<ConsequenceState> = MutableStateFlow(_savedState)
    override val stateFlow: StateFlow<ConsequenceState> = _stateFlow.asStateFlow()

    private val _effectFlow: MutableStateFlow<ConsequenceEffect?> = MutableStateFlow(null)
    override val effectFlow: SharedFlow<ConsequenceEffect?> = _effectFlow.asSharedFlow()


    override fun handleIntent(intent: ConsequenceIntent){
        when(intent) {
            is ConsequenceIntent.ToggleFocus -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        _savedState = state.copy(focusEnabled = !state.focusEnabled)
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.ToggleUsageDialogue -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        _savedState = state.copy(showUsageDialogue = !state.showUsageDialogue)
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.onNotificationChange -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        _savedState = state.copy(notificationsEnabled = intent.newValue)
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.setConsequenceDetails -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        _savedState = state.copy(
                            detailDetail = intent.detail,
                            detailEnabled = intent.enabled,
                            detailOnEnabledChange = intent.onEnabledChange,
                            detailID = intent.id)
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.consequenceNotifToggle -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newList: List<Boolean> = state.consequencesEnabled.mapIndexed { idx, value->
                            if(idx == 0) !value
                            else value
                        }
                        _savedState = state.copy(consequencesEnabled = newList.toMutableList())
                        if(state.detailID == 1){
                            _savedState = state.copy(consequencesEnabled = newList.toMutableList(),
                                detailEnabled = !state.detailEnabled)
                        }
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.consequenceMessageToggle -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newList: List<Boolean> = state.consequencesEnabled.mapIndexed { idx, value->
                            if(idx == 1) !value
                            else value
                        }
                        _savedState = state.copy(consequencesEnabled = newList.toMutableList())
                        if(state.detailID == 1){
                            _savedState = state.copy(consequencesEnabled = newList.toMutableList(),
                                detailEnabled = !state.detailEnabled)
                        }
                        _savedState
                    }
                }
            }
            is ConsequenceIntent.consequenceSelfieToggle -> {
                viewModelScope.launch {
                    _stateFlow.update { state ->
                        val newList: List<Boolean> = state.consequencesEnabled.mapIndexed { idx, value->
                            if(idx == 2) !value
                            else value
                        }
                        _savedState = state.copy(consequencesEnabled = newList.toMutableList())
                        if(state.detailID == 1){
                            _savedState = state.copy(consequencesEnabled = newList.toMutableList(),
                                detailEnabled = !state.detailEnabled)
                        }
                        _savedState
                    }
                }
            }
        }
    }
}