package cz.levinzonr.spotistats.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.util.SingleEvent

abstract class BaseViewModel<A: BaseAction,C: BaseChange,S: BaseState> : BaseViewModel<A, S, C>() {


    protected val _navigationLiveData: MutableLiveData<SingleEvent<Route>> = MutableLiveData()

    val navigationLiveData: LiveData<SingleEvent<Route>>
        get() = _navigationLiveData

    fun navigateTo(route: Route) {
        _navigationLiveData.postValue(SingleEvent(route))
    }


}
