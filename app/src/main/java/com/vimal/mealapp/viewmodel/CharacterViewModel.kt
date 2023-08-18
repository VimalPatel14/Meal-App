package com.vimal.mealapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimal.mealapp.api.NetworkState
import com.vimal.mealapp.model.Category
import com.vimal.mealapp.model.CategoryResponse
import com.vimal.mealapp.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val characterList = MutableLiveData<CategoryResponse>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loading = MutableLiveData<Boolean>()
    val allNotes: LiveData<List<Category>> = characterRepository.allNotes

    fun getAllCharacter() {
        viewModelScope.launch(exceptionHandler) {
            when (val response = characterRepository.getAllCharacter()) {
                is NetworkState.Success -> characterList.postValue(response.data!!)
                is NetworkState.Error -> onError("Network Error")
            }
        }
    }

    fun deleteCharacter(character: Category) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.delete(character)
    }

    fun deleteAllCharacter() = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.deleteAll()
    }

    fun updateCharacter(character: Category) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.update(character)
    }

    fun addCharacter(character: Category) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.insert(character)
    }

    fun addAllCharacter(characterList: List<Category>) =
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.insertAll(characterList)
        }

    private fun onError(message: String) {
        _errorMessage.value = message
        loading.value = false
    }
}