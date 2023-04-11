package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesAdapter {

    private lateinit var noteInput: EditText

    private lateinit var viewModel: NoteViewModel
    private lateinit var rView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rView = findViewById(R.id.recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

        val adapter = NoteAdapter(this, this)
        rView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]
        viewModel.allNotes.observe(this, Observer { list ->
            list ?.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onItemClicked(note:Note){
        viewModel.deleteNode(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
    }

    fun submitData(view: View) {
        val noteText = noteInput.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insert(Note(noteText))
            Toast.makeText(this, "$noteText Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}