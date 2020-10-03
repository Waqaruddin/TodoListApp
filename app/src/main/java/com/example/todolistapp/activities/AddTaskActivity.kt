package com.example.todolistapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolistapp.R
import com.example.todolistapp.models.Task
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        init()
    }

    private fun init() {
        button_add_task.setOnClickListener {
            var title = edit_text_task.text.toString()
            var description = edit_text_decsription.text.toString()
            var task = Task(title, description)
            var databaseReference = FirebaseDatabase.getInstance().getReference("tasks")

            var taskId = databaseReference.push().key
            databaseReference.child(taskId!!).setValue(task)
            Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}