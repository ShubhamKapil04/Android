package com.example.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employee.adapter.EmpAdapter
import com.example.employee.database.MyDatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    private lateinit var myDatabaseHelper: MyDatabaseHelper
    private lateinit var empAdapter: EmpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        myDatabaseHelper = MyDatabaseHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val empList = myDatabaseHelper.getAllData()
        empAdapter = EmpAdapter(empList)
        recyclerView.adapter = empAdapter



        floatingActionButton.setOnClickListener {
            val intent = Intent(this, EmpAddActivity::class.java)
            startActivity(intent)
        }

    }
}