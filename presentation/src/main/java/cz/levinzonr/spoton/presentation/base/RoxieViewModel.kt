/*
* Copyright (C) 2019. WW International, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package cz.levinzonr.spoton.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.roxie.Reducer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

/**
 * Store which manages business data and viewState.
 */
abstract class RoxieViewModel<A : BaseAction, S : BaseState, C : BaseChange> : ViewModel() {
    protected val changes: Channel<C> = Channel()

    protected abstract val initialState: S

    protected abstract val reducer: Reducer<S, C>

    protected val currentState: S
        get() = viewState.value ?: initialState

    protected val viewState = MutableLiveData<S>()

    private val tag by lazy { javaClass.simpleName }

    /**
     * Returns the current viewState. It is equal to the last value returned by the store's reducer.
     */

    protected var _viewState = MediatorLiveData<S>().apply {
        addSource(viewState) { data ->
            setValue(data)
        }
    }
    val observableState: LiveData<S> = _viewState

    protected fun <T> addStateSource(source: LiveData<T>, onChanged: (T) -> Unit) {
        _viewState.addSource(source, onChanged)
    }

    protected fun startActionsObserver() = viewModelScope.launch {
        changes.consumeAsFlow()
                .flowOn(Dispatchers.Main)
                .scan(initialState, reducer)
                .distinctUntilChanged()
                .collect {
                    viewState.postValue(it)
                }
    }

    /**
     * Dispatches an action. This is the only way to trigger a viewState change.
     */
    fun dispatch(action: A) {
        viewModelScope.launch {
            emitAction(action)
                    .collect { changes.send(it) }
        }
    }

    protected abstract fun emitAction(action: A): Flow<C>
}