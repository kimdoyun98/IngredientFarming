package com.project.ingredient_manage.update.contract

sealed interface UpdateIntent {
    object OnTopAppBarNavigationClick: UpdateIntent
    object OnCountMinusButtonClick: UpdateIntent
    object OnDeleteButtonClick: UpdateIntent
    object OnUpdateButtonClick: UpdateIntent
}
