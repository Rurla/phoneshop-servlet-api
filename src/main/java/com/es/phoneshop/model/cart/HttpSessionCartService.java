package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HttpSessionCartService implements CartService {

    private static volatile CartService cartService;

    public static CartService getInstance() {
        if (cartService == null) {
            synchronized (HttpSessionCartService.class) {
                if (cartService == null) {
                    cartService = new HttpSessionCartService();
                }
            }
        }
        return cartService;
    }

    public void add(HttpServletRequest request, CartItem addedCartItem) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        if (addedCartItem.getQuantity() > productDao.getProduct(addedCartItem.getProductId()).getStock()) {
            throw new NotEnoughStockException();
        }
        List<CartItem> cartItems;
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cartItems = cart.getCartItems();
            cartItems.add(addedCartItem);
            cart.setCartItems(cartItems);
            request.getSession().setAttribute("cart", cart);
            return;
        }
        cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId() == addedCartItem.getProductId()) {
                int newQuantity = item.getQuantity() + addedCartItem.getQuantity();
                if (newQuantity > productDao.getProduct(item.getProductId()).getStock()) {
                    throw new NotEnoughStockException();
                }
                item.setQuantity(item.getQuantity() + addedCartItem.getQuantity());
                cart.setCartItems(cartItems);
                request.getSession().setAttribute("cart", cart);
                return;
            }
        }
        cartItems.add(addedCartItem);
        cart.setCartItems(cartItems);
        request.getSession().setAttribute("cart", cart);
    }

    @Override
    public void add(HttpServletRequest request, long productId, int quantity) {
        add(request, new CartItem(productId, quantity));
    }
}
