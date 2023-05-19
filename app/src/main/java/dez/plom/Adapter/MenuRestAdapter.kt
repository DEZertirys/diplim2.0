package dez.plom.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dez.plom.R
import dez.plom.model.Menus

class MenuRestAdapter(val menuList: List<Menus?>?, val clickListener: MenuListClickListener): RecyclerView.Adapter<MenuRestAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuRestAdapter.MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.menulist, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuRestAdapter.MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (menuList == null) return 0 else menuList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)
        val addToCartButton: TextView = view.findViewById(R.id.addToCartButton)
        val addMoreLayout: LinearLayout = view.findViewById(R.id.addMoreLayout)
        val imageMinus: ImageView = view.findViewById(R.id.imageMinus)
        val imageAddOne: ImageView = view.findViewById(R.id.imageAddOne)
        val tvCount: TextView = view.findViewById(R.id.tvCount)


        fun bind(menus: Menus) {
            menuName.text = menus?.name
            menuPrice.text = "Цена: ₽ ${menus?.price}"
            addToCartButton.setOnClickListener {
                menus?.totalInCart = 1
                clickListener.addToCartClickListener(menus)
                addMoreLayout?.visibility = View.VISIBLE
                addToCartButton.visibility = View.GONE
                tvCount.text = menus?.totalInCart.toString()
            }
            imageMinus.setOnClickListener {
                var total: Int =  menus.totalInCart
                total--
                if(total > 0) {
                    menus?.totalInCart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = menus?.totalInCart.toString()
                } else {
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addToCartButton.visibility = View.VISIBLE
                }
            }

            imageAddOne.setOnClickListener {
                var total: Int    = menus.totalInCart
                total++
                if(total <= 100) {
                    menus.totalInCart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = total.toString()
                }
            }


            Glide.with(thumbImage)
                .load(menus?.url)
                .into(thumbImage)
        }
    }
    interface  MenuListClickListener{
        fun addToCartClickListener(menus: Menus)
        fun updateCartClickListener(menus: Menus)
        fun removeFromCartClickListener(menus: Menus)
    }
}

