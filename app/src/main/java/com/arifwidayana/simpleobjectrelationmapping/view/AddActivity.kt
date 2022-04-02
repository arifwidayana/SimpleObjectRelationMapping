package com.arifwidayana.simpleobjectrelationmapping.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arifwidayana.simpleobjectrelationmapping.R
import com.arifwidayana.simpleobjectrelationmapping.databinding.ActivityAddBinding
import com.arifwidayana.simpleobjectrelationmapping.model.Student
import com.arifwidayana.simpleobjectrelationmapping.model.StudentDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding
    var dataDB : StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataDB = StudentDatabase.getInstance(this)

        binding.btnSave.setOnClickListener {
            val objData = Student(null, binding.etName.text.toString(), binding.etEmail.text.toString())

            GlobalScope.async {
                val res = dataDB?.studentDao()?.insertDataStudent(objData)
                runOnUiThread {
                    if (res != 0.toLong()) {
                        Toast.makeText(this@AddActivity, "Sukses Menambahkan Data ${objData.id}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddActivity, "Gagal Menambahkan Data ${objData.id}", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }
        }
    }
}