package com.example.thecinephilia

import java.util.*

class Comment {

    var userName:String ?= null
    var movieName:String ?= null
    var review:String ?= null
    var ratings:Float ?= null
    var time: Date ?= null

    constructor()
    constructor( userName: String, movieName: String, review: String, ratings: Float, time: Date){
        this.userName = userName
        this.movieName = movieName
        this.review = review
        this.ratings = ratings
        this.time = time
    }
}