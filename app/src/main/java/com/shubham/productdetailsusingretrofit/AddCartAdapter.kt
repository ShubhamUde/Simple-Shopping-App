package com.shubham.productdetailsusingretrofit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AddCartAdapter(
    val context: Context,
    var productArrayList: MutableList<Product>, // Change to MutableList
    val onQuantityChanged: () -> Unit // Callback to notify quantity change
) : RecyclerView.Adapter<AddCartAdapter.MyCardViewHolder>() {

    class MyCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.cartImageMy)
        val title = itemView.findViewById<TextView>(R.id.cartTitle)
        val price = itemView.findViewById<TextView>(R.id.cartPrice)
        val plusImg = itemView.findViewById<ImageView>(R.id.cartPlusImg)
        val quantityText = itemView.findViewById<TextView>(R.id.cartQuantityText)
        val minusImg = itemView.findViewById<ImageView>(R.id.cartMinusImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_add_items, parent, false)
        return MyCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        val currentItem = productArrayList[position]

        holder.title.text = currentItem.title
        holder.price.text = String.format("₹%.2f", currentItem.price * currentItem.quantity)
        holder.quantityText.text = currentItem.quantity.toString()

        Picasso.get().load(currentItem.thumbnail).into(holder.image)

        holder.plusImg.setOnClickListener {
            currentItem.quantity++
            notifyItemChanged(position) // Notify item change
            onQuantityChanged() // Notify that quantity has changed
        }

        holder.minusImg.setOnClickListener {
            if (currentItem.quantity > 1) {
                currentItem.quantity--
                notifyItemChanged(position) // Notify item change
                onQuantityChanged() // Notify that quantity has changed
            } else if (currentItem.quantity == 1) {
                CartManager.removeProduct(currentItem.id)
                productArrayList.removeAt(position)
                notifyItemRemoved(position) // Notify item removal
                if (productArrayList.isEmpty()) {
                    onQuantityChanged() // Notify that cart is empty
                }
            }
        }
    }

    fun updateData(newProductList: List<Product>) {
        productArrayList = newProductList.toMutableList()
        notifyDataSetChanged() // Refresh the adapter
    }
}
