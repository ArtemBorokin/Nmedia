package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListener {
            override fun OnLikeListener(post: Post) {
                viewModel.like(post.id)
            }

            override fun OnShareListener(post: Post) {
                viewModel.share(post.id)
            }

            override fun OnRemoveListener(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun OnEditListener(post: Post) {
                viewModel.edit(post)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { post ->
            val newPost = adapter.currentList.size < post.size
            adapter.submitList(post) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.changeContentAndSave(text)

            binding.content.setText("")
            binding.group.visibility = View.GONE
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }
        binding.clear.setOnClickListener {
            binding.group.visibility = View.GONE
            binding.content.clearFocus()
            binding.content.text.clear()

            viewModel.cancelEdit()

            AndroidUtils.hideKeyboard(it)
        }

        viewModel.edited.observe(this) {post ->
            if (post.id != 0L) {
                binding.group.visibility = View.VISIBLE
                binding.content.setText(post.content)
                binding.content.requestFocus()
                binding.content.focusAndShowKeyboard()
                binding.contentLine.setText(post.content)
            }
        }
    }
}