package com.example.employee.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.employee.model.EmpModel

class MyDatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BookLibrary.db"
        private const val TABLE_CONTACTS = "Book"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "book_title"
        private const val COLUMN_EMAIL = "book_author"
        private const val COLUMN_AGE = "book_pages"
    }

    /**
     * Creating database
     */

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_CONTACTS ( $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " $COLUMN_NAME TEXT," +
                " $COLUMN_EMAIL TEXT," +
                " $COLUMN_AGE INTEGER);"
        db?.execSQL(CREATE_TABLE)
    }

    /**
     * Dropping database
     */

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    /**
     * Getting all the data first
     */

    fun getAllData(): List<EmpModel> {
        val empList = mutableListOf<EmpModel>()
        val db  = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)
        if(cursor.moveToFirst()){
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))

                val myModel = EmpModel(0, name, email, age)
                empList.add(myModel)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return empList
    }

    /**
     * Function to insert the data
     */

    fun addEmp(emp : EmpModel): Boolean{
        val db = writableDatabase

        val contentValue = ContentValues()
        contentValue.put(COLUMN_NAME, emp.name)
        contentValue.put(COLUMN_EMAIL, emp.email)
        contentValue.put(COLUMN_AGE, emp.age)

        val success = db.insert(TABLE_CONTACTS, null, contentValue)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    /**
     * Function to Delete
     */

    fun deleteEmp(id: Int){
        val db = writableDatabase
        db.delete(TABLE_CONTACTS, "_id=?", arrayOf(COLUMN_ID))
        db.close()
    }
}

