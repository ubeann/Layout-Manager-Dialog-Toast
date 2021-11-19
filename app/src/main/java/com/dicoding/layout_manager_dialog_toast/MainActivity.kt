package com.dicoding.layout_manager_dialog_toast

import android.app.Activity
import android.os.Bundle
import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "Review Mata Kuliah"
    private val list = ArrayList<Subject>()
    private val itemsDialog = arrayOf("Sangat Buruk", "Kurang", "Cukup", "Baik", "Sangat Baik")
    private val checkedItemDialog = 3
    private var selectedItemDialog:Int = 3
    private lateinit var customToast:Toast
    private lateinit var customToastLayout:View
    private lateinit var customToastText:TextView
    private lateinit var containerSubject:RecyclerView
    private lateinit var toggleLayoutManager:MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Set Variable
        toggleLayoutManager = findViewById(R.id.toggleLayoutManager)
        customToast = Toast(applicationContext)
        customToastLayout = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_container))
        customToastText = customToastLayout.findViewById(R.id.toast_text)

        // Setup recycle view
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
                showSelectedDialog(data)
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

    private fun showSelectedDialog(subject: Subject) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Review ${if (subject.name.length > 20) subject.name.take(20) + "..." else subject.name}")
            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                showCustomToast ("Berhasil mereview '${itemsDialog[selectedItemDialog]}' pada ${subject.name}.")
            }
            .setSingleChoiceItems(itemsDialog, checkedItemDialog) { _, which ->
                selectedItemDialog = which
            }
            .show()
    }

    private fun showCustomToast(message: String) {
        customToastText.text = message
        customToast.duration = Toast.LENGTH_SHORT
        customToast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL,0,168)
        customToast.view = customToastLayout
        customToast.show()
    }
}