package com.shaga.vadim

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.musfickjamil.snackify.Snackify
import com.shaga.vadim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var emailAdmin = ""
        binding.AuthAdmin.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }
        val emailId = FirebaseAuth.getInstance().currentUser
        if (emailId != null) emailAdmin = emailId.email.toString()
        if (emailAdmin.contains("admin55")) {
            binding.AuthAdmin.visibility = View.VISIBLE
        }
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {



            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextNumberPassword.text.toString()
            if (emailAdmin.contains("admin55")) {


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)

                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Snackify.success(
                                findViewById(android.R.id.content),
                                "Аккаунт создан!",
                                Snackify.LENGTH_LONG
                            ).show()
                        }
                    }.addOnFailureListener {
                        Snackify.error(
                            findViewById(android.R.id.content),
                            it.message.toString(),
                            Snackify.LENGTH_SHORT
                        ).show()
                    }
            }
        else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, SecondActivity::class.java))
                }
            }
            }
        }

    }
}