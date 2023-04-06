package com.example.employee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employee.R
import com.example.employee.model.EmpModel

class EmpAdapter(private val empData: List<EmpModel>): RecyclerView.Adapter<EmpAdapter.EmpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.item_view, parent, false)
        return EmpViewHolder(view)
    }

    override fun getItemCount(): Int {
       return empData.size
    }

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        holder.bind(empData[position])
    }

    class EmpViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        fun bind(model: EmpModel) {
            name.text = model.name
            email.text = model.email
            age.text =model.age.toString()
        }

        var name: TextView = ItemView.findViewById(R.id.name_text_view)
        var email: TextView = ItemView.findViewById(R.id.email_text_view)
        var age: TextView = ItemView.findViewById(R.id.age_text_view)
    }
}