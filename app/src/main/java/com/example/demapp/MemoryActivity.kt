package com.example.demapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MemoryActivity : AppCompatActivity() {
    var fab: FloatingActionButton? = null
    private lateinit var recyclerView: RecyclerView
    private var dataList = ArrayList<DataClass>()
    private lateinit var adapter: MyAdapter
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Images")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)
        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        dataList = ArrayList()
        adapter = MyAdapter(this, dataList)
        recyclerView.adapter = adapter
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val dataClass = dataSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


        fab?.setOnClickListener {
            val intent = Intent(this@MemoryActivity, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}