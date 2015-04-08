package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.persistence.entity.ShipmentStatus;
import com.lambo.smartpay.core.service.OrderService;
import com.lambo.smartpay.core.service.OrderStatusService;
import com.lambo.smartpay.core.service.ShipmentService;
import com.lambo.smartpay.core.service.ShipmentStatusService;
import com.lambo.smartpay.ecs.util.DataTablesParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by swang on 4/8/2015.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private ShipmentStatusService shipmentStatusService;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "shipment";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Shipment";
    }

    @ModelAttribute("paymentStatuses")
    public List<ShipmentStatus> shipmentStatuses() {
        return shipmentStatusService.getAll();
    }

    @RequestMapping(value = {"/", "", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    public String list() {
        return null;
    }

    @RequestMapping(value = {"/shipping"}, method = RequestMethod.GET)
    public String indexWaitForShipping(Model model) {

        model.addAttribute("domain", "WaitForShipping");
        return "shipping";
    }

    @RequestMapping(value = {"/listWaitForShipping"}, method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String listWaitForShipping(Model model, HttpServletRequest request) {

        model.addAttribute("domain", "WaitForShipping");

        DataTablesParams params = new DataTablesParams(request);
        return "shipping";
    }
}
