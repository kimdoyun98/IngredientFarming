package com.project.recipe.addrecipe.contract

import android.net.Uri
import com.project.model.recipe.IngredientUnit
import com.project.model.recipe.RecipeCategory
import com.project.recipe.addrecipe.model.IngredientUiModel
import com.project.recipe.addrecipe.model.RecipeStepUiModel

sealed interface AddRecipeIntent {
    object Back : AddRecipeIntent

    sealed interface Photo : AddRecipeIntent {
        object RecipePhotoNextButtonClick : Photo
        data class RecipePhotoSelect(val uri: Uri?) : Photo
        object PermissionGranted: Photo
        object PermissionDenied : Photo
        data class PermissionPermanentlyDenied(val openAppSettings: () -> Unit): Photo
    }

    sealed interface BasicInfo : AddRecipeIntent {
        object RecipeBasicInfoNextButtonClick : BasicInfo
        data class RecipeNameChange(val name: String) : BasicInfo
        data class SelectCategoryChip(val category: RecipeCategory) : BasicInfo
        data class RecipeCookTimeChange(val time: String) : BasicInfo
        data class RecipePeopleChange(val people: String) : BasicInfo
    }

    sealed interface Ingredients : AddRecipeIntent {
        object RecipeIngredientsNextButtonClick : Ingredients
        object IngredientAddButtonClick : Ingredients
        data class IngredientDeleteButtonClick(val ingredient: IngredientUiModel) : Ingredients
        data class IngredientNameChange(val ingredient: IngredientUiModel, val name: String) :
            Ingredients

        data class IngredientAmountChange(val ingredient: IngredientUiModel, val amount: String) :
            Ingredients

        data class IngredientUnitChange(
            val ingredient: IngredientUiModel,
            val unit: IngredientUnit
        ) : Ingredients
    }

    sealed interface RecipeStep : AddRecipeIntent {
        data class RecipeSaveButtonClick(val filePath: String?) : RecipeStep
        object StepAddButtonClick : RecipeStep
        data class RecipeStepChange(val step: RecipeStepUiModel, val value: String) : RecipeStep
        data class StepDeleteButtonClick(val step: RecipeStepUiModel) : RecipeStep
    }
}
