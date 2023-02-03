package com.kanahia.boolianapp.models

data class Datam(
    val data : ArrayList<Data>
)

data class Data (
    val name: String,
    val index: Long,
    val total: Long,
    val videos: Long,
    val reads: Long,
    val image: String,
    val readList: List<Read>,
    val videoList: List<Video>,
    val quizList: List<Quiz>
)

data class Quiz (
    val type: String,
    val q: String,
    val o1: String,
    val o2: String,
    val o3: String,
    val o4: String,
    val ans: String
)

data class Read (
    val type: String,
    val name: String,
    val t1: String,
    val t2: String,
    val image: String,
    val minRead: String
)

data class Video (
    val type: String,
    val name: String,
    val url: String
)