package com.vimal.mealapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimal.mealapp.api.NetworkState
import com.vimal.mealapp.model.MealResponse
import com.vimal.mealapp.repository.MealDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val characterRepository: MealDetailRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val mealList = MutableLiveData<MealResponse>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()

    fun getAllCharacter(id: String) {
        viewModelScope.launch(exceptionHandler) {
            when (val response = characterRepository.getAllCharacter(id)) {
                is NetworkState.Success -> mealList.postValue(response.data)
                is NetworkState.Error -> onError("Network Error")
            }
        }
    }

    /*suspend fun getAllMeal(meal: String): LiveData<List<Meal>> {
        return characterRepository.getAllMeal(meal)
    }

    fun deleteCharacter(character: Meal) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.delete(character)
    }

    fun deleteAllCharacter() = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.deleteAll()
    }

    fun updateCharacter(character: Meal) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.update(character)
    }

    fun addCharacter(character: Meal) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.insert(character)
    }

    fun addAllCharacter(characterList: List<Meal>) =
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.insertAll(characterList)
        }*/

    private fun onError(message: String) {
        _errorMessage.value = message
        loading.value = false
    }
}