package com.example.bookapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bookapp.R
import com.example.bookapp.databinding.ActivityBookListBinding
import com.example.bookapp.view.adapter.BookListAdapter
import com.example.bookapp.view.adapter.OnActionListener

class BookListActivity : AppCompatActivity(), OnActionListener {

    private lateinit var binding: ActivityBookListBinding
    private val bookListAdapter by lazy {
        BookListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list)

        initViews()
    }

    private fun initViews() {
        binding.bookList.adapter = bookListAdapter
    }

    override fun editBook() {
        TODO("Not yet implemented")
    }

    override fun deleteBook() {
        TODO("Not yet implemented")
    }
}