package com.example.rockpaperscissors.ui.menu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val name: String? by lazy {
        intent.getStringExtra(USER_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setNameOnTitle()
        setMenuClickListener()
    }

    private fun setNameOnTitle() {
        binding.tvTitle.text = getString(R.string.text_greeting, name)

    }

    private fun setMenuClickListener() {
        binding.ivVersusComputer.setOnClickListener {
            // todo
        }
        binding.ivVersusPlayer.setOnClickListener {
//            todo
        }

    }

    companion object {
        private val USER_NAME = "USER_NAME"

        fun startActivity(context: Context, name: String) {
            context.startActivity(Intent(context, MenuActivity::class.java).apply {
                putExtra(USER_NAME, name)
            })
        }
    }
}