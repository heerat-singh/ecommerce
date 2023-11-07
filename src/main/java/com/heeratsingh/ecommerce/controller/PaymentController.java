package com.heeratsingh.ecommerce.controller;

import com.heeratsingh.ecommerce.model.OrderItem;
import com.heeratsingh.ecommerce.model.PaymentDetails;
import com.stripe.Stripe;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.heeratsingh.ecommerce.exception.OrderException;
import com.heeratsingh.ecommerce.exception.UserException;
import com.heeratsingh.ecommerce.model.Order;
import com.heeratsingh.ecommerce.model.User;
import com.heeratsingh.ecommerce.repository.OrderRepository;
import com.heeratsingh.ecommerce.response.ApiResponse;
import com.heeratsingh.ecommerce.response.PaymentLinkResponse;
import com.heeratsingh.ecommerce.service.OrderService;
import com.heeratsingh.ecommerce.service.UserService;
import com.heeratsingh.ecommerce.user.domain.OrderStatus;
import com.heeratsingh.ecommerce.user.domain.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${stripe.api.key}")
    private String apiKey;

    @Value("${stripe.api.secret}")
    private String apiSecret;

    private OrderService orderService;
    private UserService userService;
    private OrderRepository orderRepository;

    @Value("${my.domain}")
    String mydomain;

    public PaymentController(OrderService orderService,UserService userService,OrderRepository orderRepository) {
        this.orderService=orderService;
        this.userService=userService;
        this.orderRepository=orderRepository;
    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse>createPaymentLink(@PathVariable Long orderId,
                                                                @RequestHeader("Authorization")String jwt)
            throws StripeException, UserException, OrderException{

        Stripe.apiKey=apiSecret;

        Order order=orderService.findOrderById(orderId);
        try {
            // Instantiate a Stripe client with your key ID and secret
            StripeClient stripeClient=new StripeClient(apiSecret);

            /*CustomerCreateParams customerCreateParams= CustomerCreateParams
                    .builder()
                    .setAddress(CustomerCreateParams.Address.builder()
                            .setPostalCode(order.getShippingAddress().getZipCode())
                            .setLine1(order.getShippingAddress().getStreetAddress())
                            .setState(order.getShippingAddress().getState())
                            .setCity(order.getShippingAddress().getCity())
                            .build())
                    .setEmail(order.getUser().getEmail())
                    .setName(order.getUser().getFirstName()+" "+order.getUser().getLastName())
                    .setPhone(order.getUser().getMobile())
                    .build();



            Customer customer=stripeClient.customers().create(customerCreateParams);

             */
            //find customer or create customer
            CustomerSearchParams params =
                    CustomerSearchParams
                            .builder()
                            .setQuery("email:'" + order.getUser().getEmail() + "'")
                            .build();

            CustomerSearchResult result = Customer.search(params);

            Customer customer;

            // If no existing customer was found, create a new record
            if (result.getData().isEmpty()) {

                System.out.println("New Customer");
                CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                        .setName(order.getUser().getFirstName()+" "+order.getUser().getLastName())
                        .setEmail(order.getUser().getEmail())
                        .build();

                customer = Customer.create(customerCreateParams);
            } else {
                System.out.println("OLD Customer");
                customer = result.getData().get(0);
            }

            // Next, create a checkout session by adding the details of the checkout
            SessionCreateParams.Builder paramsBuilder =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setCustomer(customer.getId())
                            .setSuccessUrl(mydomain + "/payment/"+orderId)
                            .setCancelUrl(mydomain+ "/payment/failure");

            for (OrderItem orderItem : order.getOrderItems()) {
                paramsBuilder.addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .putMetadata("app_id", String.valueOf(orderItem.getId()))
                                                                .setName(orderItem.getProduct().getTitle())
                                                                .build()
                                                )
                                                .setCurrency("cad")
                                                .setUnitAmount((long)orderItem.getPrice()*60)
                                                .build())
                                .build());
            }



        Session session = Session.create(paramsBuilder.build());


        String paymentLinkId = session.getId();//paymentLink.getId();
        String paymentLinkUrl =session.getUrl();//paymentLink.getUrl();

        PaymentLinkResponse res=new PaymentLinkResponse(paymentLinkUrl,paymentLinkId);

        order.setOrderId(session.getId());
        order.setPaymentDetails(PaymentDetails.builder()
                .stripePaymentId(session.getPaymentIntent())
                .status(PaymentStatus.PENDING)
                .paymentId(session.getId())
                .build());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PLACED);
        orderRepository.save(order);

        return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.ACCEPTED);

    } catch (StripeException e) {

        System.out.println("Error creating payment link: " + e.getMessage());
    }

    return new ResponseEntity<PaymentLinkResponse>(new PaymentLinkResponse(),HttpStatus.ACCEPTED);
}

      @GetMapping("/payments/{order_Id}")
      public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String paymentId,@RequestParam("order_id")Long orderId) throws StripeException, OrderException {

        System.out.println("payment/orderid caleed");
        StripeClient stripeClient = new StripeClient(apiSecret);
        Order order =orderService.findOrderById(orderId);

        try {
            PaymentIntentSearchParams params=PaymentIntentSearchParams.builder()
                    .setQuery("paymentId:'" + order.getPaymentDetails().getStripePaymentId() + "'")
                    .build();
            PaymentIntentSearchResult payment=PaymentIntent.search(params);

            Session session=stripeClient.checkout().sessions().retrieve(paymentId);
            System.out.println("payment details client secret --- "+session.getClientSecret());

            if(session.getPaymentStatus().equals("paid")) {
                System.out.println("payment paid---");

                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
//			order.setOrderItems(order.getOrderItems());
                System.out.println(order.getPaymentDetails().getStatus()+" payment status ");
                orderRepository.save(order);
            }
            ApiResponse res=new ApiResponse("your order get placed", true);
            return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("errrr payment -------- ");
            new RedirectView("http://localhost:3000/payment/failed");
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse("null", true),HttpStatus.OK);

    }

}

