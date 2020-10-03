package com.example.todolistapp.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.models.Task
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_task_adapter.view.*

class AdapterTask (var mContext:Context, var mList:ArrayList<Task>, private var keysList:ArrayList<String>):RecyclerView.Adapter<AdapterTask.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(task:Task, position: Int){
            itemView.text_view_task_title.text = task.title

            if(itemView.radio_button.isChecked){
                itemView.button_delete.visibility = View.VISIBLE
            }else{
                itemView.button_delete.visibility = View.INVISIBLE
            }
            itemView.button_delete.setOnClickListener {
                var databaseReference = FirebaseDatabase.getInstance().getReference("tasks")
                databaseReference.child(keysList[position]).setValue(null)
                Toast.makeText(mContext, "Task deleted successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_task_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var task = mList[position]
        holder.bind(task, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(l:ArrayList<Task>){
        mList = l
        notifyDataSetChanged()
    }
}