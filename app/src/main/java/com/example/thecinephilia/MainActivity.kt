package com.example.thecinephilia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.view.*


class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance().collection("Movies")
    val query = db.orderBy("movieName", Query.Direction.ASCENDING)
    val options = FirestoreRecyclerOptions.Builder<Movie>().setQuery(query, Movie::class.java).build()
    private var adapter: movieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMovies.layoutManager = LinearLayoutManager(this)
        adapter = movieAdapter(options)
        recyclerViewMovies.adapter = adapter
        adapter!!.startListening()

        findViewById<FloatingActionButton>(R.id.floatingActionButtonAddMovie).setOnClickListener {
            startActivity(Intent(applicationContext,AddMovieActivity::class.java))
        }
    }

    // create inner classes needed to bind the data to the recyclerview
    private inner class movieViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {}

    private inner class movieAdapter internal constructor(options: FirestoreRecyclerOptions<Movie>) :
        FirestoreRecyclerAdapter<Movie, movieViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {

            // inflate the item_restaurant.xml layout template to populate the recyclerview
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return movieViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: movieViewHolder,
            position: Int,
            model: Movie
        ) {

            holder.itemView.textViewMovie.text = model.movieName
            holder.itemView.textViewGenre.text = model.genre

            holder.itemView.setOnClickListener{

            }
        }
        }
}