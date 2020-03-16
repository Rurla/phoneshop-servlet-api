package com.es.phoneshop.web.cart;

import com.es.phoneshop.Constants;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.DaoOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {

    private static final CartService CART_SERVICE = HttpSessionCartService.getInstance();

    private static final OrderService ORDER_SERVICE = DaoOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = CART_SERVICE.getCart(request);
        request.setAttribute("cartItems", cart.getItems());
        request.setAttribute("deliveryCosts", Constants.DELIVERY_COSTS);
        request.getRequestDispatcher("/WEB-INF/pages/orderCheckout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = CART_SERVICE.getCart(request);
        Order order = ORDER_SERVICE.cartToOrder(cart);
        Map<String, String> errors = new HashMap<>();
        Locale locale = request.getLocale();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);

        if (cart.getItems().size() == 0) {
            errors.put("cart", "Cart is empty.");
        }

        String paymentMethodString = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = null;
        if (paymentMethodString != null && !paymentMethodString.isEmpty()) {
            try {
                paymentMethod = PaymentMethod.valueOf(paymentMethodString.toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.put("paymentMethod", "Invalid payment method.");
            }
        } else {
            errors.put("paymentMethod", "Please, choose payment method.");
        }

        //Contact info
        String firstName = request.getParameter("firstName");
        if (firstName == null || firstName.isEmpty() ) {
            errors.put("firstName", "Please, enter first name.");
        }
        String lastName = request.getParameter("lastName");
        if (lastName == null || lastName.isEmpty()) {
            errors.put("lastName", "Please, enter last name.");
        }
        long phoneNumber = 0;
        try {
            phoneNumber = Long.parseLong(request.getParameter("phone"));
        } catch(NumberFormatException e) {
            errors.put("phone", "Invalid phone number.");
        }
        //Delivery info
        Date deliveryDate = null;
        try {
             deliveryDate = dateFormat.parse(request.getParameter("date"));
        } catch (ParseException e) {
            errors.put("date", "Invalid date.");
        }
        String deliveryAddress = request.getParameter("address");
        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            errors.put("address", "Please, enter address");
        }
        request.setAttribute("deliveryCosts", Constants.DELIVERY_COSTS);
        request.setAttribute("cartItems", cart.getItems());
        if (errors.size() == 0) {
            order.setPaymentMethod(paymentMethod);
            order.setFirstName(firstName);
            order.setLastName(lastName);
            order.setPhoneNumber(phoneNumber);
            order.setDeliveryDate(deliveryDate);
            order.setDeliveryAddress(deliveryAddress);
            order.setDeliveryCosts(Constants.DELIVERY_COSTS);
            long id = ORDER_SERVICE.placeOrder(order);
            CART_SERVICE.clearCart(request);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + id);
        } else {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/orderCheckout.jsp").forward(request, response);
        }

    }
}
