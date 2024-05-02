package com.example.bookapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.bookapp.R
import com.example.bookapp.databinding.ActivityBookListBinding
import com.example.bookapp.view.adapter.BookListAdapter
import com.example.bookapp.view.adapter.OnActionListener
import com.example.bookapp.vm.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListActivity : AppCompatActivity(), OnActionListener {

    private lateinit var binding: ActivityBookListBinding
    private val bookListAdapter by lazy { BookListAdapter(this) }
    private val vm: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list)

        initViews()
        initObservers()
        vm.getAllBooks()
    }

    private fun initViews() {
        binding.bookList.adapter = bookListAdapter
    }

    private fun initObservers() {
        vm.books.observe(this) {
            bookListAdapter.books = it
        }

        vm.error.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun editBook() {
        TODO("Not yet implemented")
    }

    override fun deleteBook() {
        TODO("Not yet implemented")
    }
}