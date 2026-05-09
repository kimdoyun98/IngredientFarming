package com.project.ingredient_manage.update.contract

sealed interface UpdateEffect {
    object PopBackStack: UpdateEffect
}
