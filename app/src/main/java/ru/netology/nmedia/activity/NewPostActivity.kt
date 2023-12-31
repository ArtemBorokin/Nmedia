package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.content.requestFocus()

        val text = intent?.getStringExtra(Intent.EXTRA_TEXT)
        binding.content.setText(text)

        binding.save.setOnClickListener {
            val text = binding.content.text?.toString()
            val intent = Intent()
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {

                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, text))
            }
            finish()
        }
    }
}
