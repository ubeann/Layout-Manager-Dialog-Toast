package com.dicoding.layout_manager_dialog_toast

import android.os.Bundle
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "Review Mata Kuliah"
    private val list = ArrayList<Subject>()
    private lateinit var containerSubject:RecyclerView
    private lateinit var toggleLayoutManager:MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Setup recycle view
        toggleLayoutManager = findViewById(R.id.toggleLayoutManager)
        containerSubject = findViewById(R.id.containerSubject)
        containerSubject.setHasFixedSize(false)
        list.addAll(listSubject)
        showRecyclerList()
    }

    private val listSubject: ArrayList<Subject>
        get() {
            val dataImage = resources.obtainTypedArray(R.array.image)
            val dataName = resources.getStringArray(R.array.name)
            val dataDescription = resources.getStringArray(R.array.description)
            val listSubject = ArrayList<Subject>()
            for (i in dataName.indices) {
                val subject = Subject(dataImage.getResourceId(i, -1), dataName[i], dataDescription[i])
                listSubject.add(subject)
            }
            dataImage.recycle()
            return listSubject
        }

    private fun showRecyclerList() {
        containerSubject.layoutManager = LinearLayoutManager(this)
        val subjectAdapter = SubjectAdapter(list)
        containerSubject.adapter = subjectAdapter
        subjectAdapter.setOnItemClickCallback(object : SubjectAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Subject) {
//                showSelectedUser(data)
            }
        })
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            containerSubject.layoutManager = GridLayoutManager(this, 2)
        } else {
            containerSubject.layoutManager = LinearLayoutManager(this)
        }
        toggleLayoutManager.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.layoutLinear -> {
                        containerSubject.layoutManager = LinearLayoutManager(this)
                    }
                    R.id.layoutGrid -> {
                        containerSubject.layoutManager = GridLayoutManager(this, 2)
                    }
                }
            }
        }
    }

//    private fun showSelectedUser(user: User) {
//        val intent = Intent(this@MainActivity, DetailActivity::class.java)
//        intent.putExtra(DetailActivity.EXTRA_USER, user)
//        startActivity(intent)
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//    }
}