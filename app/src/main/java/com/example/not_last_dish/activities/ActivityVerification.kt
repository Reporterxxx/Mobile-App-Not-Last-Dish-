package com.example.not_last_dish.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.not_last_dish.databinding.ActivityVerificationBinding

class ActivityVerification : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}