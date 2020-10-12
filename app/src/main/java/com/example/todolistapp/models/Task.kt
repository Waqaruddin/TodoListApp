package com.example.todolistapp.models

data class Task(
    var title:String? = null,
    var description:String? = null,
    var status:String? = "incomplete"
){
    companion object{
        const val COLLECTION_NAME = "tasks"
    }
}