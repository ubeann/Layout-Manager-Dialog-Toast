package com.dicoding.layout_manager_dialog_toast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubjectAdapter(private val listSubject: ArrayList<Subject>) : RecyclerView.Adapter<SubjectAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var subjectImage: ImageView = itemView.findViewById(R.id.subject_image)
        var subjectTitle:TextView = itemView.findViewById(R.id.subject_title)
        var subjectDescription:TextView = itemView.findViewById(R.id.subject_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.card_subject, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (image, name, description) = listSubject[position]
        holder.subjectImage.setImageResource(image)
        holder.subjectTitle.text = name
        holder.subjectDescription.text = description
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listSubject[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listSubject.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Subject)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}