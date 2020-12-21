package com.example.thecinephilia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val user = FirebaseAuth.getInstance().currentUser

        if(user == null){
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }



        val name = intent.getStringExtra("movieName")
        val description = intent.getStringExtra("description")
        val genre = intent.getStringExtra("genre")

        val reviewDB = FirebaseFirestore.getInstance().collection("reviews");
        val query = reviewDB.orderBy("time", Query.Direction.ASCENDING).whereEqualTo("movieName",name)
        val options = FirestoreRecyclerOptions.Builder<Comment>().setQuery(query, Comment::class.java).build()
        var adapter: MovieActivity.reviewAdapter? = null

        recyclerViewComments.layoutManager = LinearLayoutManager(this)
        adapter = reviewAdapter(options)
        recyclerViewComments.adapter = adapter
        adapter!!.startListening()

        findViewById<TextView>(R.id.textViewMovieName).text = name
        findViewById<TextView>(R.id.textViewMovieDescription).text = description
        findViewById<TextView>(R.id.textViewMovieGenre).text = genre

        buttonMovieList.setOnClickListener{
            finish()
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.floatingActionButtonAddComment).setOnClickListener {
            var intent = Intent(applicationContext, AddReviewActivity::class.java)
            intent.putExtra("movieName", name)
            intent.putExtra("description",description)
            intent.putExtra("genre",genre)
            startActivity(intent)
        }
    }

    private inner class reviewViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {}


    private inner class reviewAdapter internal constructor(options: FirestoreRecyclerOptions<Comment>) :
            FirestoreRecyclerAdapter<Comment, reviewViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reviewViewHolder {

            // inflate the item_restaurant.xml layout template to populate the recyclerview
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
            return reviewViewHolder(view)
        }

        override fun onBindViewHolder(holder: reviewViewHolder, position: Int, model: Comment) {
            holder.itemView.textViewUsername.text = model.userName
            holder.itemView.textViewComment.text = model.review
            holder.itemView.ratingBar.rating = model.ratings!!
            holder.itemView.textViewTimeStemp.text = model.time.toString()
        }
    }


}