package com.gy25m.bookowner.model

data class AladinApiResponce(var item:MutableList<Book>)

data class Book(
    var cover:String,
    var title:String,
    var author:String,
    var pubDate:String,
    var description:String,
    var categoryName:String
)