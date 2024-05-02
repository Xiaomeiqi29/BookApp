package com.example.bookapp.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BookDataModule {
    @Binds
    abstract fun provideBookRepositoryModule(
        bookRepository: BookRepositoryImpl
    ): BookRepository
}