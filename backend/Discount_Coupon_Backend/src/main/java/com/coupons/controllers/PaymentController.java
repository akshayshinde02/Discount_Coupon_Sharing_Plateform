package com.coupons.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.coupons.enums.PaymentStatus;
import com.coupons.exceptions.OrderException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Order;
import com.coupons.models.OrderStatus;
import com.coupons.repositories.OrderRepository;
import com.coupons.response.ApiResponse;
import com.coupons.response.PaymentLinkResponse;
import com.coupons.services.OrderService;
import com.coupons.services.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/user/payment")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    private OrderService orderService;
    private UserService userService;
    private OrderRepository orderRepository;

    public PaymentController(OrderService orderService, UserService userService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt)
            throws Exception, UserException, OrderException {

        Order order = orderService.findOrderById(orderId);

        try {

            // Instantiate a Razorpay client with your key ID and secret
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            // Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
            paymentLinkRequest.put("currency", "INR");

            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName() + " " + order.getUser().getLastName());
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            // notify.put("sms",true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable", true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url", "http://localhost:5173/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");

            // Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);

            PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);

            order.setOrderId(fetchedPayment.get("order_id"));
            orderRepository.save(order);

            // Print the payment link ID and URL
            System.out.println("Payment link ID: " + paymentLinkId);
            System.out.println("Payment link URL: " + paymentLinkUrl);
            System.out.println("Order Id : " + fetchedPayment.get("order_id") + fetchedPayment);

            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            System.out.println("Error creating payment link: " + e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
            @RequestParam("order_id") Long orderId) throws Exception, OrderException {

        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        Order order = orderService.findOrderById(orderId);

        try {

            Payment payment = razorpay.payments.fetch(paymentId);
            System.out.println("payment details --- " + payment + payment.get("status"));

            // Check if the payment is valid
            if (payment == null || !payment.has("status")) {
                throw new Exception("Invalid payment_id or payment not found.");
            }

            if (payment.get("status").equals("captured")) {
                System.out.println("payment details --- " + payment + payment.get("status"));

                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                // order.setOrderItems(order.getOrderItems());
                System.out.println(order.getPaymentDetails().getStatus() + "payment status ");
                orderRepository.save(order);
            } else {

                // Payment not successful
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.FAILED);
                orderRepository.save(order);

            }
            ApiResponse res = new ApiResponse("your order get placed", true);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("errrr payment -------- ");
            // new RedirectView("http://localhost:5173/payment/");
            throw new Exception(e.getMessage());
        }
    }

}
