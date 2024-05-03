package com.example.bookapp.repository

import com.example.bookapp.model.Book
import com.example.bookapp.repository.remote.BookApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class BookRepositoryImplTest {

    @RelaxedMockK
    lateinit var bookApiService: BookApiService

    private lateinit var bookRepository: BookRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        bookRepository = BookRepositoryImpl(bookApiService)
    }

    @Test
    fun should_get_all_books() {
        runBlocking {
            val firstBook = Book("111111", "第一本书", "小王", "2023", "216482364783378")
            val secondBook = Book("222222", "第二本书", "小白", "2024", "837934658943568")
            val books = listOf(firstBook, secondBook)

            coEvery { bookApiService.getAllBooks() } returns books
            val result = bookRepository.getAllBooks()

            coVerify(exactly = 1) {
                bookApiService.getAllBooks()
                Assert.assertEquals(firstBook, result?.get(0))
                Assert.assertEquals(2, result?.size)
            }
        }
    }

    @Test
    fun should_get_a_book_by_id() {
        runBlocking {
            val id = "111111"
            val book = Book(id, "第一本书", "小王", "2023", "216482364783378")

            coEvery { bookApiService.getBookById(any()) } returns Response.success(book)
            val result = bookRepository.getBookById(id)

            coVerify(exactly = 1) {
                bookApiService.getBookById(id)
                Assert.assertEquals(book, result?.body())
            }
        }
    }

    @Test
    fun should_save_a_books() {
        runBlocking {
            val book = Book("111111", "第一本书", "小王", "2023", "216482364783378")

            coEvery { bookApiService.saveBook(any()) } returns book
            val result = bookRepository.saveBook(book)

            coVerify(exactly = 1) {
                bookApiService.saveBook(book)
                Assert.assertEquals(book, result)
            }
        }
    }

    @Test
    fun should_delete_a_book() {
        runBlocking {
            val id = "111111"

            coEvery { bookApiService.deleteBook(any()) } returns Unit
            bookRepository.deleteBook(id)

            coVerify(exactly = 1) {
                bookApiService.deleteBook(id)
            }
        }
    }
}

