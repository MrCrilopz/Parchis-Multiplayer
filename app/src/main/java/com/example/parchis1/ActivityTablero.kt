package com.example.parchis1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parchis1.databinding.ActivityCrearPartidaBinding
import com.example.parchis1.databinding.ActivityTableroBinding
import com.google.firebase.FirebaseApp

class ActivityTablero : AppCompatActivity() {
    private lateinit var binding: ActivityTableroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseApp.initializeApp(this);

    }
}