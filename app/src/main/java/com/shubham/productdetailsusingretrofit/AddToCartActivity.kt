package com.shubham.productdetailsusingretrofit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.productdetailsusingretrofit.databinding.ActivityAddToCartBinding

class AddToCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToCartBinding
    private lateinit var myAdapter: AddCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch cart items from CartManager
        val cartItems = CartManager.getCartItems().toMutableList() // Ensure it's mutable

        // Initialize adapter with a callback function to update total price
        myAdapter = AddCartAdapter(this, cartItems) { updateTotalPrice() }

        // Set up RecyclerView with the adapter
        binding.addCardRecyclerView.adapter = myAdapter
        binding.addCardRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initial display of total price
        updateTotalPrice()

        binding.btnCheckOrder.setOnClickListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        val cartItems = CartManager.getCartItems().toMutableList()
        myAdapter.updateData(cartItems)
        updateTotalPrice()
    }

    // Function to calculate and display the total price
    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun updateTotalPrice() {
        val cartItems = CartManager.getCartItems()
        val totalPrice = cartItems.sumByDouble { it.price * it.quantity }
        val formattedTotalPrice =
            if (totalPrice == 0.0) {
            "0.00" // Display "0.00" if total price is zero
        } else {
            String.format("%.2f", totalPrice) // Format to two decimal places
        }
        binding.txtPrice.text = "₹ $formattedTotalPrice"
    }

}
