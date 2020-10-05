package com.example.todolistapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.adapters.AdapterTask
import com.example.todolistapp.models.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var databaseReference: DatabaseReference
    var mList: ArrayList<Task> = ArrayList()
    lateinit var navView: NavigationView
    var userEmail:String? = null
    lateinit var drawerLayout: DrawerLayout
    var keysList: ArrayList<String> = ArrayList()
    var auth = FirebaseAuth.getInstance()
    private var adapterTask: AdapterTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseReference = FirebaseDatabase.getInstance().getReference(Task.COLLECTION_NAME)

        init()
    }

    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "To-Do List"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        setupToolbar()
        navView = nav_view
        drawerLayout = drawer_layout
        navView.setNavigationItemSelectedListener(this)
        button_add_task.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        var user = auth.currentUser
        if(user != null){
            userEmail = user.email
        }else{
            userEmail = "You are not signed in"
        }

        var headerView = navView.getHeaderView(0)
        headerView.text_view_header_email.text = userEmail

        getData()
        adapterTask = AdapterTask(this, mList, keysList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterTask

        var toggle = ActionBarDrawerToggle(this, drawerLayout, tool_bar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keysList.clear()
                for (data in dataSnapshot.children) {
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

    private fun dialogueLogout(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes", object:DialogInterface.OnClickListener{
            override fun onClick(dialogue: DialogInterface?, p1: Int) {
                auth.signOut()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }

        })

        builder.setNegativeButton("No", object:DialogInterface.OnClickListener{
            override fun onClick(dialogue: DialogInterface?, p1: Int) {
                dialogue?.dismiss()
            }

        })
        var alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                dialogueLogout()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.item_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            R.id.item_account ->  Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}