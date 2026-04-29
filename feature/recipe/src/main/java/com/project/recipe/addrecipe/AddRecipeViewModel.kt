package com.project.recipe.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.recipe.SaveRecipeUseCase
import com.project.recipe.addrecipe.contract.AddRecipeEffect
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.recipe.addrecipe.model.IngredientUiModel
import com.project.recipe.addrecipe.model.RecipeStepUiModel
import com.project.recipe.addrecipe.model.asRecipe
import com.project.recipe.addrecipe.util.AddRecipeBackStack
import com.project.recipe.addrecipe.util.RecipeSaveState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val saveRecipeUseCase: SaveRecipeUseCase
) : ContainerHost<AddRecipeState, AddRecipeEffect>, ViewModel() {
    override val container = container<AddRecipeState, AddRecipeEffect>(AddRecipeState())
    private val backStack = ArrayDeque<AddRecipeBackStack>()
    private val recentIngredientNumber = container.stateFlow
        .map { it.recentIngredientNumber }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    private val recentRecipeStepNumber = container.stateFlow
        .map { it.recentRecipeStepNumber }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    init {
        backStack.add(AddRecipeBackStack.RecipePhotoScreen())

        recentIngredientNumber
            .filter { it > 0 }
            .onEach {
                intent {
                    val ingredients = state.ingredients.toMutableList()
                    ingredients.add(IngredientUiModel(id = it))

                    reduce { state.copy(ingredients = ingredients.toImmutableList()) }
                }
            }
            .launchIn(viewModelScope)

        recentRecipeStepNumber
            .filter { it > 0 }
            .onEach {
                intent {
                    val steps = state.recipeSteps.toMutableList()
                    steps.add(RecipeStepUiModel(id = it))

                    reduce { state.copy(recipeSteps = steps.toImmutableList()) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: AddRecipeIntent) {
        when (intent) {
            is AddRecipeIntent.Back -> intent {
                if (state.currentBackstack is AddRecipeBackStack.RecipePhotoScreen) {
                    postSideEffect(AddRecipeEffect.NavigateToRecipeList)
                    return@intent
                }

                backStack.removeLast()

                reduce { state.copy(currentBackstack = backStack.last()) }
            }

            is AddRecipeIntent.Photo -> {
                handlePhoto(intent)
            }

            is AddRecipeIntent.BasicInfo -> {
                handleBasicInfo(intent)
            }

            is AddRecipeIntent.Ingredients -> {
                handleIngredients(intent)
            }

            is AddRecipeIntent.RecipeStep -> {
                handleRecipStep(intent)
            }
        }
    }

    private fun handlePhoto(intent: AddRecipeIntent.Photo) {
        when (intent) {
            is AddRecipeIntent.Photo.RecipePhotoNextButtonClick -> intent {
                backStack.add(AddRecipeBackStack.RecipeBasicInfoScreen())

                reduce { state.copy(currentBackstack = backStack.last()) }
            }

            is AddRecipeIntent.Photo.RecipePhotoSelect -> intent {
                if (intent.uri != null) {
                    reduce { state.copy(photo = intent.uri) }
                } else {
                    postSideEffect(AddRecipeEffect.UriIsNull)
                }
            }

            is AddRecipeIntent.Photo.PermissionDenied -> intent {
                postSideEffect(AddRecipeEffect.PermissionDenied)
            }

            is AddRecipeIntent.Photo.PermissionPermanentlyDenied -> intent {
                postSideEffect(AddRecipeEffect.PermissionPermanentlyDenied(intent.openAppSettings))
            }
        }
    }

    private fun handleBasicInfo(intent: AddRecipeIntent.BasicInfo) {
        when (intent) {
            is AddRecipeIntent.BasicInfo.RecipeNameChange -> intent {
                reduce {
                    state.copy(
                        name = intent.name,
                        isEnableBasicInfoNextButton = state.isEnableBasicInfoNextButton(name = intent.name)
                    )
                }
            }

            is AddRecipeIntent.BasicInfo.RecipeCookTimeChange -> intent {
                reduce {
                    state.copy(
                        time = intent.time,
                        isEnableBasicInfoNextButton = state.isEnableBasicInfoNextButton(time = intent.time)
                    )
                }
            }

            is AddRecipeIntent.BasicInfo.RecipePeopleChange -> intent {
                reduce {
                    state.copy(
                        people = intent.people,
                        isEnableBasicInfoNextButton = state.isEnableBasicInfoNextButton(people = intent.people)
                    )
                }
            }

            is AddRecipeIntent.BasicInfo.SelectCategoryChip -> intent {
                reduce {
                    state.copy(
                        selectedCategory = intent.category,
                        isEnableBasicInfoNextButton = state.isEnableBasicInfoNextButton(
                            selectedCategory = intent.category
                        )
                    )
                }
            }

            is AddRecipeIntent.BasicInfo.RecipeBasicInfoNextButtonClick -> intent {
                backStack.add(AddRecipeBackStack.RecipeIngredientsScreen())

                reduce { state.copy(currentBackstack = backStack.last()) }
            }
        }
    }

    private fun handleIngredients(intent: AddRecipeIntent.Ingredients) {
        when (intent) {
            is AddRecipeIntent.Ingredients.IngredientNameChange -> intent {
                val ingredients = state.ingredients.map {
                    if (it.id == intent.ingredient.id) it.copy(name = intent.name)
                    else it
                }.toImmutableList()

                reduce {
                    state.copy(
                        ingredients = ingredients,
                        isEnableIngredientsNextButton = state.isEnableIngredientsNextButton(
                            ingredients
                        )
                    )
                }
            }

            is AddRecipeIntent.Ingredients.IngredientAmountChange -> intent {
                val ingredients = state.ingredients.map {
                    if (it.id == intent.ingredient.id) it.copy(amount = intent.amount)
                    else it
                }.toImmutableList()

                reduce {
                    state.copy(
                        ingredients = ingredients,
                        isEnableIngredientsNextButton = state.isEnableIngredientsNextButton(
                            ingredients
                        )
                    )
                }
            }

            is AddRecipeIntent.Ingredients.IngredientUnitChange -> intent {
                val ingredients = state.ingredients.map {
                    if (it.id == intent.ingredient.id) it.copy(unit = intent.unit)
                    else it
                }.toImmutableList()

                reduce {
                    state.copy(
                        ingredients = ingredients,
                        isEnableIngredientsNextButton = state.isEnableIngredientsNextButton(
                            ingredients
                        )
                    )
                }
            }

            is AddRecipeIntent.Ingredients.IngredientAddButtonClick -> intent {
                reduce { state.copy(recentIngredientNumber = state.recentIngredientNumber + 1) }
            }

            is AddRecipeIntent.Ingredients.IngredientDeleteButtonClick -> intent {
                if (state.ingredients.size == 1) return@intent

                val ingredients = state.ingredients.toMutableList()
                ingredients.remove(intent.ingredient)

                val newIngredients = ingredients.toImmutableList()
                reduce {
                    state.copy(
                        ingredients = newIngredients,
                        isEnableIngredientsNextButton = state.isEnableIngredientsNextButton(
                            newIngredients
                        )
                    )
                }
            }

            is AddRecipeIntent.Ingredients.RecipeIngredientsNextButtonClick -> intent {
                backStack.add(AddRecipeBackStack.RecipeStepsScreen())

                reduce { state.copy(currentBackstack = backStack.last()) }
            }
        }
    }

    private fun handleRecipStep(intent: AddRecipeIntent.RecipeStep) {
        when (intent) {
            is AddRecipeIntent.RecipeStep.RecipeSaveButtonClick -> intent {
                viewModelScope.launch {
                    reduce { state.copy(recipeSaveState = RecipeSaveState.Loading) }

                    val isSuccess = saveRecipeUseCase.invoke(state.asRecipe(intent.filePath))

                    if (isSuccess) {
                        postSideEffect(AddRecipeEffect.NavigateToRecipeList)

                    } else {
                        reduce { state.copy(recipeSaveState = RecipeSaveState.Error) }
                        postSideEffect(AddRecipeEffect.SaveError())
                    }
                }
            }

            is AddRecipeIntent.RecipeStep.StepAddButtonClick -> intent {
                reduce { state.copy(recentRecipeStepNumber = state.recentRecipeStepNumber + 1) }
            }

            is AddRecipeIntent.RecipeStep.RecipeStepChange -> intent {
                val recipeSteps = state.recipeSteps.map {
                    if (it.id == intent.step.id) it.copy(description = intent.value)
                    else it
                }.toImmutableList()

                reduce {
                    state.copy(
                        recipeSteps = recipeSteps,
                        isEnableSaveButton = state.isEnableSaveButton(recipeSteps)
                    )
                }
            }

            is AddRecipeIntent.RecipeStep.StepDeleteButtonClick -> intent {
                if (state.recipeSteps.size == 1) return@intent

                val recipeSteps = state.recipeSteps.toMutableList()
                recipeSteps.remove(intent.step)

                val newRecipeSteps = recipeSteps.toImmutableList()
                reduce {
                    state.copy(
                        recipeSteps = newRecipeSteps,
                        isEnableSaveButton = state.isEnableSaveButton(newRecipeSteps)
                    )
                }
            }
        }
    }
}
