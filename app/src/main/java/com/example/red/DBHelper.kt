package com.example.red

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Pessoa"
        private const val DATABASE_VERSION = 2
        private const val TABLE_PESSOA = "Pessoa"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_PESSOA (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOME TEXT)")
        
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PESSOA")
        onCreate(db)
    }

    fun addPessoa(nome: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOME, nome)

        db.insert(TABLE_PESSOA, null, values)
        db.close()
    }

    fun getAllPessoas(): List<Pessoa> {
        val pessoas = mutableListOf<Pessoa>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_PESSOA", null)

        if (cursor.moveToFirst()) {
            do {
                val pessoa = Pessoa(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
                )
                pessoas.add(pessoa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return pessoas
    }

    fun deletePessoa(pessoa: Pessoa) {
        val db = this.writableDatabase
        db.delete(TABLE_PESSOA, "$COLUMN_ID = ?", arrayOf(pessoa.id.toString()))
        db.close()
    }

    fun updatePessoa(pessoa: Pessoa) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, pessoa.nome)
        }
        db.update(TABLE_PESSOA, values, "$COLUMN_ID = ?", arrayOf(pessoa.id.toString()))
        db.close()
    }
}