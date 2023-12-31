package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        println(resources.displayMetrics.heightPixels) // 1794
        println(resources.displayMetrics.widthPixels) // 1080
        println(resources.displayMetrics.densityDpi) // 420
        println(resources.displayMetrics.density) // 2.625

        binding.root.setOnClickListener {
        }

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContentAndSave(result)
        }

        val newOrEditLauncher = registerForActivityResult(NewPostResultContract()) {
            if (it == null) {
                viewModel.cancelEdit()
                return@registerForActivityResult
            }
            viewModel.changeContentAndSave(it)
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun LikeListener(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun RemoveListener(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun EditListener(post: Post) {
                viewModel.edit(post)
                newOrEditLauncher.launch(post.content)
            }

            override fun PlayVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun ShareListener(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/*"
                }

                val chooser = Intent.createChooser(intent, null)
                startActivity(chooser)
            }
        })
        binding.list.adapter = adapter

        binding.newPostButton.setOnClickListener {
            newPostContract.launch(null)
        }

        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
    }
}