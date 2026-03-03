package com.project.ingredient_manage.contract.update

sealed interface UpdateIntent {
    object OnTopAppBarNavigationClick: UpdateIntent
    object OnCountMinusButtonClick: UpdateIntent
    object OnDeleteButtonClick: UpdateIntent
    object OnUpdateButtonClick: UpdateIntent
}
