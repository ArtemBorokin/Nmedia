package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import ru.netology.nmedia.count.count
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.post.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу." +
                    " Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLike.text = count(post.countLikes)
            //if (post.likedByMe) {
            like.setImageResource(if (post.likedByMe) (R.drawable.ic_liked_24) else (R.drawable.icon_like))
            //}

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                //like.setImageResource(
                if (post.likedByMe) {
                    post.countLikes += 1
                    countLike.text = count(post.countLikes)
                    like.setImageResource(R.drawable.ic_liked_24)
                }

                else {
                    post.countLikes -= 1
                    countLike.text = count(post.countLikes)
                    like.setImageResource(R.drawable.icon_like)
                    //)
                }
            }
            share.setOnClickListener {
                countShare.text = count(post.countShare)
                post.countShare += 1
            }
        }
    }
}