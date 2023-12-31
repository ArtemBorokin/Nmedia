package ru.netology.nmedia.activity

import android.content.Intent
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

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) { text ->
            text ?: return@registerForActivityResult
            viewModel.changeContentAndSave(text.toString())
            viewModel.save()
        }

        val newOrEditLauncher = registerForActivityResult(NewPostContract) {
            val text = it ?: return@registerForActivityResult
            viewModel.changeContentAndSave(text)
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun OnLike(post: Post) {
                viewModel.like(post.id)
                newPostLauncher.launch(post.content)
            }

            override fun OnShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }

                val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(chooser)
            }

            override fun OnRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun OnEdit(post: Post) {
                viewModel.edit(post)
                newOrEditLauncher.launch(post.content)
            }

            override fun PlayVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

                val chooser = Intent.createChooser(intent, null)
                startActivity(chooser)


        })

        binding.list.adapter = adapter

        binding.newPostButton.setOnClickListener{
            newPostContract.launch(null)
        }


        binding.list.adapter = adapter
        viewModel.data.observe(this) { post ->
            val newPost = adapter.currentList.size < post.size
            adapter.submitList(post) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.add.setOnClickListener {
            newPostLauncher.launch(null)
        }
    }
}