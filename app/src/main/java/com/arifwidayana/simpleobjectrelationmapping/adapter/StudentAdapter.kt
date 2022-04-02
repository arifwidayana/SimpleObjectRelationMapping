package com.arifwidayana.simpleobjectrelationmapping.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arifwidayana.simpleobjectrelationmapping.MainActivity
import com.arifwidayana.simpleobjectrelationmapping.databinding.ItemDataBinding
import com.arifwidayana.simpleobjectrelationmapping.model.Student
import com.arifwidayana.simpleobjectrelationmapping.model.StudentDatabase
import com.arifwidayana.simpleobjectrelationmapping.view.EditActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(private val listData : List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder (val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            tvId.text = listData[position].id.toString()
            tvName.text = listData[position].nama.toString()
            tvEmail.text = listData[position].email.toString()

            ivEdit.setOnClickListener {
                val intentEdit = Intent(it.context, EditActivity()::class.java)
                intentEdit.putExtra(EditActivity.STUDENT, listData[position])
                it.context.startActivity(intentEdit)
            }

            ivDelete.setOnClickListener {
                AlertDialog.Builder(it.context).setPositiveButton("Ya") { _,_ ->
                    val dataInstance = StudentDatabase.getInstance(holder.itemView.context)
                    GlobalScope.async {
                        val res = dataInstance?.studentDao()?.deleteDataStudent(listData[position])
                        (holder.itemView.context as MainActivity).runOnUiThread {
                            if (res != 0) {
                                Toast.makeText(it.context, "Data ${listData[position].id} Behasil Terhapus", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(it.context, "Data ${listData[position].id} Gagal Terhapus", Toast.LENGTH_SHORT).show()
                            }
                        }
                        (holder.itemView.context as MainActivity).fetchData()
                    }
                }.setNegativeButton("Tidak") { action,_ ->
                    action.dismiss()
                }.setMessage("Apakah Anda yakin ingin menghapus data ${listData[position].id}")
                    .setTitle("Konfirmasi Hapus Data")
                    .create()
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}