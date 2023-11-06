package com.example.demapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demapp.Adapter.ToDoAdapter
import com.example.demapp.Model.ToDoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.annotation.Nullable



class TaskActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mFab: FloatingActionButton
    private lateinit var firestore: FirebaseFirestore
   private lateinit var adapter: ToDoAdapter
   private lateinit var mList: List<ToDoModel>
   private lateinit var query: Query
   /// private lateinit var listenerRegistration: ListenerRegistration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        recyclerView = findViewById(R.id.recycerlview)
        mFab = findViewById(R.id.floatingActionButton)
        firestore = FirebaseFirestore.getInstance()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this@TaskActivity)

        mFab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
        this.mList = ArrayList()
        var adapter = ToDoAdapter(this, mList)

        //val itemTouchHelper = ItemTouchHelper(TouchHelper(adapter))
       // itemTouchHelper.attachToRecyclerView(recyclerView)
        showData()
        recyclerView.adapter = adapter
        showData()
    }
    private fun showData() {
        var query = firestore.collection("task").orderBy("time", Query.Direction.DESCENDING)

        fun onEvent(@Nullable value: QuerySnapshot?, @Nullable error: FirebaseFirestoreException?) {
            for (documentChange in value!!.documentChanges) {
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val id = documentChange.document.id
                    val toDoModel = with(documentChange.document.toObject(ToDoModel::class.java)) {
                        id
                    }

                    // mList.add(toDoModel)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        //  mList.reverse()
    }

private fun <E> List<E>.add(toDoModel: String) {
    mList.add(toDoModel)
}

private fun <E> Collection<E>.reverse() {
    // TODO(reason = "Not yet implemented")
    mList.reverse()
}

}




