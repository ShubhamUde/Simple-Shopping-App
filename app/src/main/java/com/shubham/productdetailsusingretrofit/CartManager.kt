package com.shubham.productdetailsusingretrofit

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        // Check if product already exists in the cart
        val existingProduct = cartItems.find { it.id == product.id }
        if (existingProduct != null) {
            existingProduct.quantity += product.quantity
        } else {
            cartItems.add(product)
        }
    }

    fun removeProduct(productId: Int) {
        cartItems.removeAll { it.id == productId }
    }

    fun getCartItems(): List<Product> {
        return cartItems
    }

    fun clearCart() {
        cartItems.clear()
    }

}
