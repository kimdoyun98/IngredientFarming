package com.project.ingredient_manage.contract.update

sealed interface UpdateEffect {
    object PopBackStack: UpdateEffect
}
