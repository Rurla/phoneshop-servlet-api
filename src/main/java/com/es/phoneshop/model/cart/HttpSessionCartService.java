package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (productDao.getProduct(addedCartItem.getProductId()).getAvailable() < addedCartItem.getQuantity() ) {
            throw new NotEnoughStockException();
        }
        List<CartItem> cartItems;
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cartItems = cart.getCartItems();
            addUnchecked(request, cartItems, addedCartItem, cart);
            return;
        }
        cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId() == addedCartItem.getProductId()) {
                addChecked(request, cartItems, item, addedCartItem, cart);
                return;
            }
        }
        addUnchecked(request, cartItems, addedCartItem, cart);
    }

    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if (cart != null) {
            return cart;
        }
        cart = new Cart();
        request.getSession().setAttribute("cart", cart);
        return cart;
    }

    @Override
    public void removeCartItem(HttpServletRequest request) {
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.removeIf(cartItem ->
                cartItem.getProductId() == Integer.parseInt(request.getParameter("productId"))
        );
        cart.setCartItems(cartItems);
    }

    @Override
    public void updateCartItem(Cart cart, long productId, int quantity) {
        List<CartItem> cartItems = cart.getCartItems();
        AtomicInteger beforeQuantity = new AtomicInteger();
        ProductDao productDao = ArrayListProductDao.getInstance();
        Product product = productDao.getProduct(productId);
        cartItems.forEach(cartItem -> {
            if (cartItem.getProductId() == productId) {
                beforeQuantity.set(cartItem.getQuantity());
                product.setAvailable(product.getAvailable() + beforeQuantity.get());
                if (product.getAvailable() >= quantity) {
                    cartItem.setQuantity(quantity);
                    product.setAvailable(product.getAvailable() - quantity);
                    productDao.updateProduct(product);
                }
            }
        });
        cart.setCartItems(cartItems);
    }

    @Override
    public void add(HttpServletRequest request, long productId, int quantity) {
        add(request, new CartItem(productId, quantity));
    }

    private void addUnchecked(HttpServletRequest request, List<CartItem> cartItems, CartItem cartItem, Cart cart) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        Product product = productDao.getProduct(cartItem.getProductId());
        product.setAvailable(product.getAvailable() - cartItem.getQuantity());
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        productDao.updateProduct(product);
        request.getSession().setAttribute("cart", cart);
    }

    private void addChecked(HttpServletRequest request, List<CartItem> cartItems, CartItem item, CartItem addedCartItem, Cart cart) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        if (item.getProductId() == addedCartItem.getProductId()) {
            if (productDao.getProduct(item.getProductId()).getAvailable() < addedCartItem.getQuantity() ) {
                throw new NotEnoughStockException();
            }
            item.setQuantity(item.getQuantity() + addedCartItem.getQuantity());
            cart.setCartItems(cartItems);
            Product product = productDao.getProduct(addedCartItem.getProductId());
            product.setAvailable(product.getAvailable() - addedCartItem.getQuantity());
            productDao.updateProduct(product);
            request.getSession().setAttribute("cart", cart);
        }
    }
}
