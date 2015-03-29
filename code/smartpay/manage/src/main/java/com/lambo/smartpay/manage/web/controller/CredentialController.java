package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.service.CredentialStatusService;
import com.lambo.smartpay.core.service.CredentialTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.table.DataTablesCredential;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swang on 3/21/2015.
 */
@Controller
@RequestMapping("/credential")
@Secured({"ROLE_ADMIN"})
public class CredentialController {

    private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private CredentialStatusService credentialStatusService;
    @Autowired
    private CredentialTypeService credentialTypeService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "credential";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Credential";
    }

    @ModelAttribute("credentialTypes")
    public List<CredentialType> credentialTypes() {
        return credentialTypeService.getAll();
    }

    @ModelAttribute("credentialStatuses")
    public List<CredentialStatus> credentialStatuses() {
        return credentialStatusService.getAll();
    }

    // index view
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    // ajax for DataTables
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public String list(HttpServletRequest request) {

        // parse sorting column
        String orderIndex = request.getParameter("order[0][column]");
        String order = request.getParameter("columns[" + orderIndex + "][name]");

        // parse sorting direction
        String orderDir = StringUtils.upperCase(request.getParameter("order[0][dir]"));

        // parse search keyword
        String search = request.getParameter("search[value]");

        // parse pagination
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));

        if (start == null || length == null || order == null || orderDir == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        List<Merchant> merchants = merchantService.findByCriteria(search, start,
                length, order, ResourceProperties.JpaOrderDir.valueOf(orderDir));

        // count total and filtered
        Long recordsTotal = merchantService.countAll();
        Long recordsFiltered = merchantService.countByCriteria(search);

        if (merchants == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesCredential> dataTablesCredentials = new ArrayList<>();
        for (Merchant merchant : merchants) {
            DataTablesCredential tablesCredential = new DataTablesCredential(merchant);
            dataTablesCredentials.add(tablesCredential);
        }

        DataTablesResultSet<DataTablesCredential> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesCredentials);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

}
