package com.example.red

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PessoaAdapter(
    private var pessoas: MutableList<Pessoa>,
    private val onDeleteClick: (Pessoa) -> Unit,
    private val onUpdateClick: (Pessoa) -> Unit
) :
    RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pessoa, parent, false)
        return PessoaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        val currentPessoa = pessoas[position]
//        holder.nomeTextView.text = currentPessoa.nome
        holder.nomeEditText.setText(currentPessoa.nome)

        holder.updateButton.setOnClickListener {
            val novoNome = holder.nomeEditText.text.toString()
            if (novoNome.isNotEmpty()) {
                currentPessoa.nome = novoNome
                onUpdateClick(currentPessoa)
            }
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentPessoa)
        }
    }

    override fun getItemCount() = pessoas.size

    fun updatePessoa(pessoa: Pessoa) {
        val position = pessoas.indexOf(pessoa)
        if (position != -1) {
            pessoas[position] = pessoa
            notifyItemChanged(position)
        }
    }

    fun removePessoa(pessoa: Pessoa) {
        val position = pessoas.indexOf(pessoa)
        if (position != -1) {
            pessoas.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class PessoaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nomeTextView: TextView = itemView.findViewById(R.id.nomeTextView)
        val nomeEditText: EditText = itemView.findViewById(R.id.nomeEditText)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}