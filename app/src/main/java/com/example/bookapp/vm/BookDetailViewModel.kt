package com.example.bookapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.model.Book
import com.example.bookapp.model.UIBook
import com.example.bookapp.repository.BookRepository
import com.example.bookapp.view.BookDetailActivity.Companion.EXTRA_KEY_BOOK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    sealed class NetworkStatus {
        object Success : NetworkStatus()
        class Error(val message: String?) : NetworkStatus()
    }

    val originalBook: Book? = stateHandle.get<Book>(EXTRA_KEY_BOOK)
    val book = UIBook(originalBook)

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus: LiveData<NetworkStatus> = _networkStatus

    private val _buttonEnable = MutableLiveData<Boolean>()
    val buttonEnable: LiveData<Boolean> = _buttonEnable

    fun deleteBook() {
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(originalBook?.id.orEmpty())
                _networkStatus.value = NetworkStatus.Success
            } catch (e: Exception) {
                _networkStatus.value = NetworkStatus.Error(e.message)
            }
        }
    }

    fun saveBook() {
        viewModelScope.launch {
            try {
                val book = book.convertToBook()
                bookRepository.saveBook(book)
                _networkStatus.value = NetworkStatus.Success
            } catch (e: Exception) {
                _networkStatus.value = NetworkStatus.Error(e.message)
            }
        }
    }

    fun updateButtonState() {
        _buttonEnable.value = book.hasUpdate && !book.invalid
    }
}