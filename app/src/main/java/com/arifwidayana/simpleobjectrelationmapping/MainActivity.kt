package com.arifwidayana.simpleobjectrelationmapping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arifwidayana.simpleobjectrelationmapping.adapter.StudentAdapter
import com.arifwidayana.simpleobjectrelationmapping.databinding.ActivityMainBinding
import com.arifwidayana.simpleobjectrelationmapping.model.StudentDatabase
import com.arifwidayana.simpleobjectrelationmapping.view.AddActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var dataDB : StudentDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataDB = StudentDatabase.getInstance(this)

        addDataStudent()

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun addDataStudent() {
        fetchData()
        binding.fabAdd.setOnClickListener {
            val addData = Intent(this, AddActivity::class.java)
            startActivity(addData)
        }
    }

    fun fetchData() {
        GlobalScope.launch {
            val listData = dataDB?.studentDao()?.getDataStudent()
            runOnUiThread {
                listData?.let {
                    val adapter = StudentAdapter(it)
                    binding.rvData.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
    }
}