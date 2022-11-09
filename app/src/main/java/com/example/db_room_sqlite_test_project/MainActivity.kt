package com.example.db_room_sqlite_test_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.db_room_sqlite_test_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        db.getDao().getAllItem().asLiveData().observe(this){ list ->
            binding.tvList.text = ""
            list.forEach {
                val text = "Id: ${it.id} Name: ${it.title} Price: ${it.subtitle}\n"
                binding.tvList.append(text)
            }
        }
        binding.button.setOnClickListener {
            val item = Item(null,
                binding.edTitle.text.toString(),
                binding.edSubtitle.text.toString()
            )
            Thread{
                db.getDao().insertItem(item)
            }.start()
        }
    }
}