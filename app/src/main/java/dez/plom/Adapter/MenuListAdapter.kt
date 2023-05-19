package dez.plom.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dez.plom.R
import dez.plom.model.Katmodel

class MenuListAdapter( val katlist: List<Katmodel?>?,  val clickListener: KatListClickListener): RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        holder.bind(katlist?.get(position)!!)
        holder.itemView.setOnClickListener{
            clickListener.onItemCLick(katlist?.get(position)!!)
        }

    }

    override fun getItemCount(): Int {
        return  katlist?.size!!
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagee: ImageView = view.findViewById(R.id.imajefood)
        val nameres: TextView = view.findViewById(R.id.tv_name)
        val address: TextView = view.findViewById(R.id.tv_food_name)

        fun bind(katlogmodel: Katmodel?) {

            nameres.text = katlogmodel?.name
            address.text = "Адрес: " + katlogmodel?.address

            Glide.with(imagee)
                .load(katlogmodel?.image)
                .into(imagee)
        }

    }
    interface  KatListClickListener{
        fun onItemCLick(katlogmodel: Katmodel?)
    }
}