package com.example.not_last_dish.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.not_last_dish.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = binding.nickname
        var password = binding.passwordField
        var fullname = binding.name
        var nickname = binding.nickname
        val login = binding.login
        val register = binding.registerStart

        login.setOnClickListener {
            val i = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}