package com.example.employee

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.employee.database.MyDatabaseHelper
import com.example.employee.model.EmpModel

class EmpAddActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var addEmp: Button

    private lateinit var myDatabaseHelper: MyDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_add)

        nameInput = findViewById(R.id.name_input)
        emailInput = findViewById(R.id.email_input)
        ageInput = findViewById(R.id.age_input)
        addEmp = findViewById(R.id.add_data_button)

        myDatabaseHelper = MyDatabaseHelper(this)


        addEmp.setOnClickListener {
            var i = 0
            var success = false
            if(nameInput.text.toString().isNotEmpty() &&
                    emailInput.text.toString().isNotEmpty()&&
                    ageInput.text.isNotEmpty()){

                val emp = EmpModel(i++, nameInput.text.toString(),  emailInput.text.toString(), ageInput.text.toString().toInt())
                var db = myDatabaseHelper
                success = db.addEmp(emp) as Boolean

                if(success){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}