package com.example.bookapp.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.bookapp.model.Book
import com.example.bookapp.repository.BookRepository
import com.example.bookapp.view.BookDetailActivity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class BookDetailViewModelTest {

    @RelaxedMockK
    lateinit var bookRepository: BookRepository

    private lateinit var bookDetailViewModel: BookDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val mainDispatcherRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val book = Book("111111", "第一本书", "小王", "2023", "216482364783378")

    @Before
    fun setup() {
        savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
        every { savedStateHandle.get<Book>(BookDetailActivity.EXTRA_KEY_BOOK) } returns book
        MockKAnnotations.init(this, relaxed = true)
        bookDetailViewModel = BookDetailViewModel(bookRepository, savedStateHandle)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_save_a_book_success() {
        runTest {
            coEvery { bookRepository.saveBook(any()) } returns book

            bookDetailViewModel.saveBook()

            coVerify(exactly = 1) {
                bookRepository.saveBook(book)
                Assert.assertEquals(
                    BookDetailViewModel.NetworkStatus.Success,
                    bookDetailViewModel.networkStatus.value
                )
            }
        }
    }

    @Test
    fun should_save_a_book_failure() {
        runTest {
            val errorMessage = "error"
            val exception = mockk<Exception>()
            every { exception.message } returns errorMessage
            coEvery { bookRepository.saveBook(any()) }.throws(exception)
            bookDetailViewModel.saveBook()

            coVerify(exactly = 1) {
                bookRepository.saveBook(book)
                Assert.assertTrue(bookDetailViewModel.networkStatus.value is BookDetailViewModel.NetworkStatus.Error)
                Assert.assertEquals(
                    errorMessage,
                    (bookDetailViewModel.networkStatus.value as? BookDetailViewModel.NetworkStatus.Error)?.message
                )
            }
        }
    }

    @Test
    fun should_delete_a_book_success() {
        runTest {
            coEvery { bookRepository.deleteBook(any()) } returns Unit

            bookDetailViewModel.deleteBook()

            coVerify(exactly = 1) {
                bookRepository.deleteBook(book.id.orEmpty())
                Assert.assertEquals(
                    BookDetailViewModel.NetworkStatus.Success,
                    bookDetailViewModel.networkStatus.value
                )
            }
        }
    }

    @Test
    fun should_delete_a_book_failure() {
        runTest {
            val errorMessage = "error"
            val exception = mockk<Exception>()
            every { exception.message } returns errorMessage
            coEvery { bookRepository.deleteBook(any()) }.throws(exception)
            bookDetailViewModel.deleteBook()

            coVerify(exactly = 1) {
                bookRepository.deleteBook(book.id.orEmpty())
                Assert.assertTrue(bookDetailViewModel.networkStatus.value is BookDetailViewModel.NetworkStatus.Error)
                Assert.assertEquals(
                    errorMessage,
                    (bookDetailViewModel.networkStatus.value as? BookDetailViewModel.NetworkStatus.Error)?.message
                )
            }
        }
    }
}