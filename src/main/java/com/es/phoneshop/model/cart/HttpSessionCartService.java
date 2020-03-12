package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.InvalidNumberException;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HttpSessionCartService implements CartService {

    private static volatile CartService cartService;

    private static final ProductDao productDao = ArrayListProductDao.getInstance();

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
        if (productDao.getProduct(addedCartItem.getProductId()).getAvailable() < addedCartItem.getQuantity()) {
            throw new NotEnoughStockException();
        }
        List<CartItem> cartItems;
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cartItems = cart.getCartItems();
            addUnchecked(request, cartItems, addedCartItem, cart);
            recalculateCart(cart);
            return;
        }
        cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId() == addedCartItem.getProductId()) {
                addChecked(request, cartItems, item, addedCartItem, cart);
                recalculateCart(cart);
                return;
            }
        }
        addUnchecked(request, cartItems, addedCartItem, cart);
        recalculateCart(cart);
    }

    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            return cart;
        }
        cart = new Cart();
        request.getSession().setAttribute("cart", cart);
        return cart;
    }

    @Override
    public void removeCartItem(Cart cart, long productId) {
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.removeIf(cartItem -> {
                    boolean remove = cartItem.getProductId() == productId;
                    if (remove) {
                        Product product = productDao.getProduct(productId);
                        product.setAvailable(product.getAvailable() + cartItem.getQuantity());
                        productDao.updateProduct(product);
                    }
                    return remove;
                }
        );
        cart.setCartItems(cartItems);
        recalculateCart(cart);
    }

    @Override
    public void updateCartItem(Cart cart, long productId, int quantity) {
        List<CartItem> cartItems = cart.getCartItems();
        AtomicInteger beforeQuantity = new AtomicInteger();
        Product product = productDao.getProduct(productId);
        cartItems.forEach(cartItem -> {
            if (cartItem.getProductId() == productId) {
                beforeQuantity.set(cartItem.getQuantity());
                product.setAvailable(product.getAvailable() + beforeQuantity.get());
                if (quantity <= 0) {
                    throw new InvalidNumberException();
                }
                if (product.getAvailable() < quantity) {
                    throw new NotEnoughStockException();
                }
                cartItem.setQuantity(quantity);
                product.setAvailable(product.getAvailable() - quantity);
                productDao.updateProduct(product);
            }
        });
        cart.setCartItems(cartItems);
        recalculateCart(cart);
    }

    @Override
    public void recalculateCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        AtomicReference<BigDecimal> atomicTotalPrice = new AtomicReference<>(new BigDecimal(0));
        cartItems.forEach(cartItem -> {
            ProductDao productDao = ArrayListProductDao.getInstance();
            Product product = productDao.getProduct(cartItem.getProductId());
            BigDecimal totalPrice = atomicTotalPrice.get();
            BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
            atomicTotalPrice.set(totalPrice.add(product.getPrice().multiply(quantity)));
        });
        cart.setTotalPrice(atomicTotalPrice.get());
    }

    @Override
    public void add(HttpServletRequest request, long productId, int quantity) {
        add(request, new CartItem(productId, quantity));
    }

    private void addUnchecked(HttpServletRequest request, List<CartItem> cartItems, CartItem cartItem, Cart cart) {
        Product product = productDao.getProduct(cartItem.getProductId());
        product.setAvailable(product.getAvailable() - cartItem.getQuantity());
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        productDao.updateProduct(product);
        request.getSession().setAttribute("cart", cart);
    }

    private void addChecked(HttpServletRequest request, List<CartItem> cartItems, CartItem item, CartItem addedCartItem, Cart cart) {
        if (item.getProductId() == addedCartItem.getProductId()) {
            if (productDao.getProduct(item.getProductId()).getAvailable() < addedCartItem.getQuantity()) {
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
