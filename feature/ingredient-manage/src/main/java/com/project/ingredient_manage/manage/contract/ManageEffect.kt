package com.project.ingredient_manage.manage.contract

sealed interface ManageEffect {
    object NavigateToHome : ManageEffect
    object NavigateToDefaultIngredientManage: ManageEffect
    object ShowSnackBar: ManageEffect
    data class NavigateToUpdateIngredient(val id: Int): ManageEffect
}
