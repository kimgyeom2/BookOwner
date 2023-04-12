package com.gy25m.bookowner.model

data class AladinBookList(var item: MutableList<Hot>)

data class Hot(
    var title:String,
    var description:String,
    var cover:String
)
