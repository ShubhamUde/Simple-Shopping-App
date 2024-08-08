package com.shubham.productdetailsusingretrofit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shubham.productdetailsusingretrofit.databinding.ActivityProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val rating = intent.getDoubleExtra("rating", 0.0)
        val price = intent.getDoubleExtra("price", 0.0)
        val status = intent.getStringExtra("status")
        val description = intent.getStringExtra("description")

        Picasso.get().load(image).into(binding.productImg)

        binding.productTitle.text = title
        binding.productRating.text = "Rating: $rating/5"
        binding.productPrice.text = "₹ $price"
        binding.productStatus.text = status
        binding.productAbout.text = description

        binding.btnAddToCard.setOnClickListener {
            addToCart(image, title, price)
        }
    }

    private fun addToCart(image: String?, title: String?, price: Double) {
        if (image != null && title != null) {
            val product = Product(
                availabilityStatus = "In Stock",
                brand = "",
                category = "",
                description = "",
                discountPercentage = 0.0,
                id = title.hashCode(), // Unique ID based on title hash
                images = listOf(image),
                minimumOrderQuantity = 1,
                price = price,
                rating = 0.0,
                returnPolicy = "",
                shippingInformation = "",
                sku = "",
                stock = 0,
                tags = listOf(),
                thumbnail = image,
                title = title,
                warrantyInformation = "",
                weight = 0,
                quantity = 1
            )
            CartManager.addProduct(product)

            val intent = Intent(this, AddToCartActivity::class.java)
            startActivity(intent)
        }
    }
}
