package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.count
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostVewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val viewModel: PostVewModel by viewModels()
        viewModel.data.observe(this) { post ->

            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countLike.text = count(post.countLikes)
                like.setImageResource(if (post.likedByMe) (R.drawable.ic_liked_24) else (R.drawable.icon_like))

                like.setOnClickListener {
                    post.likedByMe = !post.likedByMe

                    if (post.likedByMe) {
                        post.countLikes += 1
                        countLike.text = count(post.countLikes)
                        like.setImageResource(R.drawable.ic_liked_24)
                    } else {
                        post.countLikes -= 1
                        countLike.text = count(post.countLikes)
                        like.setImageResource(R.drawable.icon_like)
                    }
                }
                share.setOnClickListener {
                    countShare.text = count(post.countShare)
                    post.countShare += 1
                }
            }
        }
    }
}