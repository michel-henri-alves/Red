package com.example.red

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.state.ToggleableState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    // v1
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }

    private lateinit var dbHelper: DBHelper
    private lateinit var pessoaAdapter: PessoaAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //db
        dbHelper = DBHelper(this)

        val inputText: EditText = findViewById(R.id.inputText)
        val submitButton: Button = findViewById(R.id.submitButton)
        recyclerView = findViewById(R.id.recyclerView)


        submitButton.setOnClickListener {
            val text = inputText.text.toString()
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

            //db
            if (text.isNotEmpty()) {
                dbHelper.addPessoa(text)
                Toast.makeText(this, "Nome salvo", Toast.LENGTH_SHORT).show()
                updateRecyclerView()
            }else {
                Toast.makeText(this, "Por favor, insira um nome", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        updateRecyclerView()

    }


    private fun updateRecyclerView() {
        val pessoas = dbHelper.getAllPessoas().toMutableList()
        pessoaAdapter = PessoaAdapter(pessoas, { pessoa ->
            dbHelper.deletePessoa(pessoa)
            pessoaAdapter.removePessoa(pessoa)
            Toast.makeText(this, "Nome excluído!", Toast.LENGTH_SHORT).show()
        }, { pessoa ->
            dbHelper.updatePessoa(pessoa)
            pessoaAdapter.updatePessoa(pessoa)
            Toast.makeText(this, "Nome atualizado!", Toast.LENGTH_SHORT).show()
        })
        recyclerView.adapter = pessoaAdapter
    }
}