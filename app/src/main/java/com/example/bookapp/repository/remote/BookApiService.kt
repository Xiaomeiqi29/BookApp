package com.example.bookapp.repository.remote

import com.example.bookapp.model.Book
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApiService {
    @GET("/books")
    suspend fun getAllBooks(): List<Book>?

    @POST("/books/save")
    suspend fun addBook(@Body book: Book): Book?

    @GET("/books/{id}")
    suspend fun getBookById(@Path("id") id: String): Book?

    @DELETE("/books/delete/{id}")
    suspend fun deleteBook(@Path("id") id: String)

    @POST("/books/save")
    suspend fun updateBook(@Body book: Book): Book?
}