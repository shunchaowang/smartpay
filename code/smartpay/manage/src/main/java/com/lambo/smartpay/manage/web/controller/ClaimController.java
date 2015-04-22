package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.service.ClaimService;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.PaymentService;
import com.lambo.smartpay.core.service.PaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by swang on 4/20/2015.
 */
@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentStatusService paymentStatusService;
}
