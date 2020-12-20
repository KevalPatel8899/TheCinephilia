package com.example.thecinephilia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore

class AddMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        val spinner = findViewById<Spinner>(R.id.spinnerGenre);
        val db = FirebaseFirestore.getInstance().collection("Movies")
        val movie = Movie()


        val stringArray = arrayOf(
            "Action", "Animation", "Comedy", "Crime", "Drama", "Experimental", "Fantasy", "Historical", "Horror", "Romance", "Science Fiction", "Thriller", "Western", "Other"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, stringArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        findViewById<Button>(R.id.buttonSubmitMovies).setOnClickListener{

            if(findViewById<EditText>(R.id.editTextMovieName).text != null &&  findViewById<EditText>(R.id.editTextDescription).text != null ){
                val id = db.document().id
                movie.id = id
                movie.movieName = findViewById<EditText>(R.id.editTextMovieName).text.toString()
                movie.description = findViewById<EditText>(R.id.editTextDescription).text.toString()
                movie.genre = findViewById<Spinner>(R.id.spinnerGenre).selectedItem.toString()
                db.document(id).set(movie)

                findViewById<EditText>(R.id.editTextMovieName).text.clear()
                findViewById<EditText>(R.id.editTextDescription).text.clear()
                findViewById<Spinner>(R.id.spinnerGenre).setSelection(0)


            }
        }
    }
}