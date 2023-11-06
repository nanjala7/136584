package com.example.demapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demapp.Model.ToDoModel
import com.example.demapp.R
import com.example.demapp.TaskActivity
import com.google.firebase.firestore.FirebaseFirestore


class ToDoAdapter(private val activity: TaskActivity, private val todoList: List<ToDoModel>) : RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //TODO("Not yet implemented")
        val view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false)
       // firestore = FirebaseFirestore.getInstance()
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")

        return todoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      //  TODO("Not yet implemented")
        val toDoModel = todoList[position]
        holder.mCheckBox.text = toDoModel.task
        holder.mDueDateTv.text = "Due On ${toDoModel.due}"
        holder.mCheckBox.isChecked = toBoolean(toDoModel.status!!)
        holder.mCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                firestore.collection("task").document(toDoModel.TaskId.toString()).update("status", 1)
            } else {
                firestore.collection("task").document(toDoModel.TaskId.toString()).update("status", 0)
            }
        }
    }
    private fun toBoolean(status: Int): Boolean {
        return status != 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mDueDateTv: TextView = itemView.findViewById(R.id.due_date_tv)
        val mCheckBox: CheckBox = itemView.findViewById(R.id.mcheckbox)

    }
}