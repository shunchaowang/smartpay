package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.EncryptionCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesTransaction;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.service.EncryptionService;
import com.lambo.smartpay.core.service.EncryptionTypeService;
import com.lambo.smartpay.core.service.FeeTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.util.ResourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/21/2015.
 */
@Controller
@RequestMapping("/transaction")
@Secured({"ROLE_ADMIN"})
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private EncryptionTypeService encryptionTypeService;
    @Autowired
    private FeeTypeService feeTypeService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "transaction";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Transaction";
    }

    @ModelAttribute("encryptionTypes")
    public List<EncryptionType> encryptionTypes() {
        return encryptionTypeService.getAll();
    }

    @ModelAttribute("feeTypes")
    public List<FeeType> feeTypes() {
        return feeTypeService.getAll();
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

        List<DataTablesTransaction> dataTablesTransactions = new ArrayList<>();
        for (Merchant merchant : merchants) {
            DataTablesTransaction tablesTransaction = new DataTablesTransaction(merchant);
            dataTablesTransactions.add(tablesTransaction);
        }

        DataTablesResultSet<DataTablesTransaction> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesTransactions);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(resultSet);
    }

    @RequestMapping(value = "/editEncryption", method = RequestMethod.GET)
    public String editEncryption(@RequestParam("id") Long id, Model model) {
        Encryption encryption = null;
        try {
            encryption = encryptionService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Bad Request.");
        }
        EncryptionCommand encryptionCommand = new EncryptionCommand(encryption);
        model.addAttribute("encryptionCommand", encryptionCommand);
        return "admin/merchant/transaction/_encryptionModal";
    }

    @RequestMapping(value = "/editEncryption", method = RequestMethod.POST)
    @ResponseBody
    public String updateEncryption(HttpServletRequest request) {

        logger.debug("in updateEncryption.");
        Long id = Long.valueOf(request.getParameter("id"));
        String key = request.getParameter("encryptionKey");
        Long typeId = Long.valueOf(request.getParameter("encryptionTypeId"));
        EncryptionCommand encryptionCommand = new EncryptionCommand();
        encryptionCommand.setId(id);
        encryptionCommand.setEncryptionKey(key);
        encryptionCommand.setEncryptionTypeId(typeId);
        Locale locale = LocaleContextHolder.getLocale();
        String entity = messageSource.getMessage("encryption.label", null, locale);
        String message = messageSource.getMessage("updated.message", new String[]{entity,
                encryptionCommand.getId().toString()}, locale);

        Encryption encryption = null;
        try {
            encryption = encryptionService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource.getMessage("not.updated.message", new String[]{entity,
                    encryptionCommand.getId().toString()}, locale);
        }
        editEncryption(encryption, encryptionCommand);
        try {
            encryption = encryptionService.update(encryption);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            message = messageSource.getMessage("not.updated.message", new String[]{entity,
                    encryptionCommand.getId().toString()}, locale);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            message = messageSource.getMessage("not.updated.message", new String[]{entity,
                    encryptionCommand.getId().toString()}, locale);
        }

        JsonResponse response = new JsonResponse();
        response.setMessage(message);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }

    private void editEncryption(Encryption encryption, EncryptionCommand encryptionCommand) {
        encryption.setKey(encryptionCommand.getEncryptionKey());
        EncryptionType encryptionType = null;
        try {
            encryptionType = encryptionTypeService.get(encryptionCommand.getEncryptionTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        encryption.setEncryptionType(encryptionType);
    }
}
