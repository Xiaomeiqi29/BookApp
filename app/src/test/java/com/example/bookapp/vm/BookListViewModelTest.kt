package com.example.bookapp.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bookapp.model.Book
import com.example.bookapp.repository.BookRepository
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
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
class BookListViewModelTest {

    @RelaxedMockK
    lateinit var bookRepository: BookRepository
    private lateinit var bookListViewModel: BookListViewModel

    @get:Rule
    val mainDispatcherRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        bookListViewModel = BookListViewModel(bookRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_get_all_books_success() {
        runTest {
            val firstBook = Book("111111", "第一本书", "小王", "2023", "216482364783378")
            val secondBook = Book("222222", "第二本书", "小白", "2024", "837934658943568")
            val books = listOf(firstBook, secondBook)

            coEvery { bookRepository.getAllBooks() } returns books
            bookListViewModel.fetchBookList()

            coVerify(exactly = 1) {
                bookRepository.getAllBooks()
                Assert.assertEquals(firstBook, bookListViewModel.books.value?.get(0))
                Assert.assertEquals(2, bookListViewModel.books.value?.size)
            }
        }
    }

    @Test
    fun should_get_all_books_failure() {
        runTest {
            val errorMessage = "error"
            val exception = mockk<Exception>()
            every { exception.message } returns errorMessage
            coEvery { bookRepository.getAllBooks() }.throws(exception)
            bookListViewModel.fetchBookList()

            coVerify(exactly = 1) {
                bookRepository.getAllBooks()
                Assert.assertEquals(errorMessage, bookListViewModel.error.value)
            }
        }
    }

    @Test
    fun should_get_a_book_by_id_success() {
        runTest {
            val id = "111111"
            val book = Book(id, "第一本书", "小王", "2023", "216482364783378")

            bookListViewModel.inputContent.value = id
            coEvery { bookRepository.getBookById(any()) } returns Response.success(book)
            bookListViewModel.getBookById()

            coVerify(exactly = 1) {
                bookRepository.getBookById(id)
                Assert.assertEquals(book, bookListViewModel.searchResult.value)
            }
        }
    }

    @Test
    fun should_get_a_book_by_id_failure() {
        runTest {
            val id = "111111"

            bookListViewModel.inputContent.value = id
            val errorMessage = "error"
            val exception = mockk<Exception>()
            every { exception.message } returns errorMessage
            coEvery { bookRepository.getBookById(any()) }.throws(exception)
            bookListViewModel.getBookById()

            coVerify(exactly = 1) {
                bookRepository.getBookById(id)
                Assert.assertEquals(errorMessage, bookListViewModel.error.value)
            }
        }
    }
}