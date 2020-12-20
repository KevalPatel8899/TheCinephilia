package com.example.thecinephilia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val db = FirebaseFirestore.getInstance().collection("Movies")

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("movieName")
        val description = intent.getStringExtra("description")
        val genre = intent.getStringExtra("genre")

        findViewById<TextView>(R.id.textViewMovieName).text = name
        findViewById<TextView>(R.id.textViewMovieDescription).text = description
        findViewById<TextView>(R.id.textViewMovieGenre).text = genre

        findViewById<FloatingActionButton>(R.id.floatingActionButtonAddComment).setOnClickListener {
            startActivity(Intent(applicationContext, AddReviewActivity::class.java))
        }

    }
}