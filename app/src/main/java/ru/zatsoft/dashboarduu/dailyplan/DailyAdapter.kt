package ru.zatsoft.dashboarduu.dailyplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zatsoft.dashboarduu.R

class DailyAdapter(private val dailyList: MutableList<DailyItem>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    private var onDailyClickListener: OnDailyClickListener? = null

    interface OnDailyClickListener {
        fun onDailyClick(item: DailyItem, position: Int)
    }

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.findViewById(R.id.item_hour)
        val content: TextView = itemView.findViewById(R.id.item_content)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_plan_item, parent, false)

        return DailyViewHolder(itemView)
    }

    override fun getItemCount() = dailyList.size

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = dailyList[position]
        val hour = item.hour
        holder.hour.text =  hour
        holder.content.text = item.task
        holder.itemView.setOnClickListener {
            if (onDailyClickListener != null) {
                onDailyClickListener!!.onDailyClick(item, position)
            }
        }
    }

    fun setOnDailyClickListener(onDailyClickListener: OnDailyClickListener){
        this.onDailyClickListener = onDailyClickListener
    }
}

