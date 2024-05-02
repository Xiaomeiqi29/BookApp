package com.example.bookapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bookapp.R
import com.example.bookapp.databinding.ActivityBookDetailBinding
import com.example.bookapp.vm.BookDetailViewModel
import com.example.bookapp.vm.BookDetailViewModel.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_BOOK = "extra_key_book"
    }

    private lateinit var binding: ActivityBookDetailBinding
    private val vm: BookDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.book = vm.book
        binding.deleteBook.setOnClickListener {
            vm.deleteBook()
        }
        binding.saveBook.setOnClickListener {

        }
    }

    private fun initObservers() {
        vm.networkStatus.observe(this) {
            when (it) {
                is NetworkStatus.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }

                is NetworkStatus.Error -> {
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}