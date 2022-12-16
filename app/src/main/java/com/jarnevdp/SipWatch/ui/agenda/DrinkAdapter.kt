package com.jarnevdp.SipWatch.ui.agenda

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jarnevdp.SipWatch.R
import com.jarnevdp.SipWatch.readFromFile
import com.jarnevdp.SipWatch.removeLineFromFile

class DrinkAdapter(private val context: Context, drinkModelArrayList: ArrayList<DrinkModel>) :
RecyclerView.Adapter<DrinkAdapter.Viewholder>() {
    private var drinkModelArrayList: ArrayList<DrinkModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.drink_card, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val model: DrinkModel = drinkModelArrayList[position]
        holder.drinkTitle.text = model.getDrink_name()
        holder.drinkAmount.text = model.getDrink_amount()
        holder.drinkType.text = model.getDrink_type()
        holder.drinkTime.text = model.getDrink_time()
        holder.drinkDate.text = model.getDrink_date()

        holder.deleteButton.setOnClickListener {
            val removeline = model.getDrink_date() + ";" + model.getDrink_time() + ";" +
                    model.getDrink_name() + ";" + model.getDrink_type() +
                    ";" + model.getDrink_amount()
            drinkModelArrayList = removeLineFromFile(context, model.getDrink_date()+"drinkStorage.txt", model,
                drinkModelArrayList, removeline)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, drinkModelArrayList.size)
        }
    }

    override fun getItemCount(): Int {
        return drinkModelArrayList.size
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkTitle: TextView
        val drinkAmount: TextView
        val drinkType: TextView
        val drinkTime: TextView
        val drinkDate: TextView
        val deleteButton: Button
        init {
            drinkDate = itemView.findViewById(R.id.text_drink_date)
            drinkTime = itemView.findViewById(R.id.text_drink_time)
            drinkTitle = itemView.findViewById(R.id.text_drink_name)
            drinkAmount = itemView.findViewById(R.id.text_drink_amount)
            drinkType = itemView.findViewById(R.id.text_drink_type)
            deleteButton = itemView.findViewById(R.id.delete_drink)
        }
    }

    init {
        this.drinkModelArrayList = drinkModelArrayList
    }
}