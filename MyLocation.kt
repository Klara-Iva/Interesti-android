package com.example.myapplication


data class MyLocation(
    var id:String="",
    var lati: Double?=null,
    var long: Double?=null,
    var name: String="",
    var image: String
){
    constructor(): this("", null,null,"","")
}