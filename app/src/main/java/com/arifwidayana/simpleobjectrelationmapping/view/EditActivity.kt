package com.arifwidayana.simpleobjectrelationmapping.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arifwidayana.simpleobjectrelationmapping.R
import com.arifwidayana.simpleobjectrelationmapping.databinding.ActivityEditBinding
import com.arifwidayana.simpleobjectrelationmapping.model.Student
import com.arifwidayana.simpleobjectrelationmapping.model.StudentDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditBinding
    private var dataDB : StudentDatabase? = null

    companion object {
        const val STUDENT = "STUDENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataDB = StudentDatabase.getInstance(this)

        val objData = intent.getParcelableExtra<Student>(STUDENT)

        binding.apply {
            etName.setText(objData?.nama)
            etEmail.setText(objData?.email)

            btnSave.setOnClickListener {
                objData?.nama = etName.text.toString()
                objData?.email = etEmail.text.toString()

                GlobalScope.async {
                    val res = dataDB?.studentDao()?.updateDataStudent(objData!!)
                    runOnUiThread {
                        if (res != 0) {
                            Toast.makeText(this@EditActivity,"Sukses Mengubah Data ${objData?.id} ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@EditActivity,"Gagal Mengubah Data ${objData?.id} ", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                }
            }
        }


    }
}