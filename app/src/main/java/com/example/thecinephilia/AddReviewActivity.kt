package com.example.thecinephilia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_review.*
import kotlinx.android.synthetic.main.item_comment.*
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*


class AddReviewActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)

        val db = FirebaseFirestore.getInstance().collection("reviews")
        val reviews = Comment()
        val user = FirebaseAuth.getInstance().currentUser

        if(user == null){
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.buttonSubmitComment).setOnClickListener{
            if(findViewById<RatingBar>(R.id.ratingBar2).rating.toInt() == 0)
                Toast.makeText(this, "Rating should be more than 0", Toast.LENGTH_SHORT).show()
            else if(TextUtils.isEmpty(editTextReview.text))
                Toast.makeText(this, "Please write proper review", Toast.LENGTH_SHORT).show()
            else {
                val id = db.document().id
                reviews.ratings = ratingBar2.rating
                reviews.review = editTextReview.text.toString()
                reviews.movieName = intent.getStringExtra("movieName")
                reviews.userName = user?.email

                val localDate = LocalDate.now()
                val date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.MAX))
                reviews.time = date
                db.document(id).set(reviews)
            }
        }
    }
}