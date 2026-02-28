package com.project.ingredient_manage.contract

sealed interface ManageEffect {
    object NavigateToHome : ManageEffect
    object ShowSnackBar: ManageEffect
}
