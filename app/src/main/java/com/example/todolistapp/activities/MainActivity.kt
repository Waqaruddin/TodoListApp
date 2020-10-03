package com.example.todolistapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.adapters.AdapterTask
import com.example.todolistapp.models.Task
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    var mList:ArrayList<Task> = ArrayList()
    var keysList:ArrayList<String> = ArrayList()
    private var adapterTask:AdapterTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseReference = FirebaseDatabase.getInstance().getReference(Task.COLLECTION_NAME)

        init()
    }

    private fun init() {
        button_add_task.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        getData()
        adapterTask = AdapterTask(this, mList, keysList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterTask
    }

    private fun getData() {
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keysList.clear()
                for(data in dataSnapshot.children){
                    var task = data.getValue(Task::class.java)
                    var key = data.key
                    mList.add(task!!)
                    keysList.add(key!!)
                }
                adapterTask!!.setData(mList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}