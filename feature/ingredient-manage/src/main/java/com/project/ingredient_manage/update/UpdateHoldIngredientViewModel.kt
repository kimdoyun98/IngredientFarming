package com.project.ingredient_manage.update

import androidx.lifecycle.ViewModel
import com.project.ingredient_manage.contract.update.UpdateEffect
import com.project.ingredient_manage.contract.update.UpdateIntent
import com.project.ingredient_manage.contract.update.UpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UpdateHoldIngredientViewModel @Inject constructor(

): ContainerHost<UpdateState, UpdateEffect>, ViewModel() {
    override val container = container<UpdateState, UpdateEffect>(UpdateState())

    fun onIntent(intent: UpdateIntent){

    }
}
