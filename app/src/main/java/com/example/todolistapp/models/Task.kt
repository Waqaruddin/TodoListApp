package com.example.todolistapp.models

data class Task(
    var title:String? = null,
    var description:String? = null
){
    companion object{
        const val COLLECTION_NAME = "tasks"
    }
}