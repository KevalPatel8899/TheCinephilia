package com.example.thecinephilia


class Movie {

    var id:String ?= null
    var movieName:String ?= null
    var description:String ?= null
    var genre:String ?= null

    constructor()

    constructor(id: String, movieName: String, description: String, genre: String){
        this.id = id
        this.movieName = movieName
        this.description = description
        this.genre = genre
    }
}