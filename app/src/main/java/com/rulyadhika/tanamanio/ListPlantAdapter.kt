package com.rulyadhika.tanamanio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListPlantAdapter(private val listPlant: ArrayList<Plant>) :
    RecyclerView.Adapter<ListPlantAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val itemCategory: TextView = itemView.findViewById(R.id.tv_plant_type)
        val itemDifficulty: TextView = itemView.findViewById(R.id.tv_plant_difficulty)
        val itemPhoto: ImageView = itemView.findViewById(R.id.iv_plant_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_plant_card, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPlant.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, _, _, category, difficulty, photo) = listPlant[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.itemPhoto)
        holder.itemName.text = name
        holder.itemCategory.text = category
        holder.itemDifficulty.text = difficulty

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailPlantActivity::class.java);
            intent.putExtra(DetailPlantActivity.DETAIL_DATA, listPlant[holder.adapterPosition])

            holder.itemView.context.startActivity(intent)
        }
    }
}