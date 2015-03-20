package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.exception.MissingRequiredFieldException;
import com.lambo.smartpay.exception.NoSuchEntityException;
import com.lambo.smartpay.exception.NotUniqueException;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.MerchantCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesMerchant;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.persistence.entity.Credential;
import com.lambo.smartpay.persistence.entity.CredentialStatus;
import com.lambo.smartpay.persistence.entity.CredentialType;
import com.lambo.smartpay.persistence.entity.Encryption;
import com.lambo.smartpay.persistence.entity.EncryptionType;
import com.lambo.smartpay.persistence.entity.Fee;
import com.lambo.smartpay.persistence.entity.FeeType;
import com.lambo.smartpay.persistence.entity.Merchant;
import com.lambo.smartpay.persistence.entity.MerchantStatus;
import com.lambo.smartpay.service.CredentialStatusService;
import com.lambo.smartpay.service.CredentialTypeService;
import com.lambo.smartpay.service.EncryptionTypeService;
import com.lambo.smartpay.service.FeeTypeService;
import com.lambo.smartpay.service.MerchantService;
import com.lambo.smartpay.service.MerchantStatusService;
import com.lambo.smartpay.util.ResourceProperties;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/20/2015.
 */
@Controller
@RequestMapping("/admin/merchant")
@Secured({"ROLE_ADMIN"})
public class AdminMerchantController {

    private static final Logger logger = LoggerFactory.getLogger(AdminMerchantController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantStatusService merchantStatusService;

    @Autowired
    private CredentialStatusService credentialStatusService;
    @Autowired
    private CredentialTypeService credentialTypeService;
    @Autowired
    private EncryptionTypeService encryptionTypeService;
    @Autowired
    private FeeTypeService feeTypeService;
    @Autowired
    private MessageSource messageSource;

    // here goes all model across the whole controller
    @ModelAttribute("controller")
    public String controller() {
        return "admin/merchant";
    }

    @ModelAttribute("domain")
    public String domain() {
        return "Merchant";
    }

    @ModelAttribute("merchantStatuses")
    public List<MerchantStatus> merchantStatuses() {
        return merchantStatusService.getAll();
    }

    @ModelAttribute("credentialTypes")
    public List<CredentialType> credentialTypes() {
        return credentialTypeService.getAll();
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
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    // ajax for DataTables
    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = "application/json")
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

        List<DataTablesMerchant> dataTablesMerchants = new ArrayList<>();
        for (Merchant merchant : merchants) {
            DataTablesMerchant tablesMerchant = new DataTablesMerchant(merchant);
            dataTablesMerchants.add(tablesMerchant);
        }

        DataTablesResultSet<DataTablesMerchant> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesMerchants);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(resultSet);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("merchantCommand", new MerchantCommand());
        model.addAttribute("action", "create");
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(Model model,
                       @ModelAttribute("merchantCommand") MerchantCommand merchantCommand) {
        model.addAttribute("merchantCommand", new MerchantCommand());

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields
        // check uniqueness
        if (merchantService.findByName(merchantCommand.getName()) != null) {
            String fieldLabel = messageSource.getMessage("name.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, merchantCommand.getName()}, locale));
            model.addAttribute("merchantCommand", merchantCommand);
            model.addAttribute("action", "create");
        }

        Merchant merchant = createMerchant(merchantCommand);
        try {
            merchantService.create(merchant);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        return "main";
    }


    private Credential createCredential(MerchantCommand merchantCommand) {
        Credential credential = new Credential();
        // default credential status would be Approved 500
        CredentialStatus credentialStatus = null;
        CredentialType credentialType = null;
        try {
            credentialStatus = credentialStatusService
                    .get(merchantCommand.getCredentialStatusId());
            credentialType = credentialTypeService.get(merchantCommand.getCredentialTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        credential.setContent(merchantCommand.getCredentialContent());
        credential.setActive(true);

        credential.setCredentialStatus(credentialStatus);
        credential.setCredentialType(credentialType);
        return credential;
    }

    private Encryption createEncryption(MerchantCommand merchantCommand) {
        Encryption encryption = new Encryption();
        EncryptionType encryptionType = null;
        try {
            encryptionType = encryptionTypeService.get(merchantCommand.getEncryptionTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        encryption.setEncryptionType(encryptionType);
        encryption.setKey(merchantCommand.getEncryptionKey());
        encryption.setActive(true);
        return encryption;
    }

    private Fee createCommissionFee(MerchantCommand merchantCommand) {
        Fee fee = new Fee();
        FeeType feeType = null;
        try {
            feeType = feeTypeService.get(merchantCommand.getCommissionFeeTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setActive(true);
        fee.setValue(merchantCommand.getCommissionFeeValue());
        fee.setFeeType(feeType);

        return fee;
    }

    private Fee createReturnFee(MerchantCommand merchantCommand) {
        Fee fee = new Fee();
        FeeType feeType = null;
        try {
            feeType = feeTypeService.get(merchantCommand.getReturnFeeTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setActive(true);
        fee.setValue(merchantCommand.getReturnFeeValue());
        fee.setFeeType(feeType);

        return fee;
    }

    private Merchant createMerchant(MerchantCommand merchantCommand) {
        Merchant merchant = new Merchant();
        MerchantStatus merchantStatus = null;
        try {
            merchantStatus = merchantStatusService.get(merchantCommand.getMerchantStatusId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        merchant.setMerchantStatus(merchantStatus);
        merchant.setName(merchantCommand.getName());
        merchant.setActive(true);
        merchant.setAddress(merchantCommand.getAddress());
        merchant.setContact(merchantCommand.getContact());
        merchant.setEmail(merchantCommand.getEmail());

        // create all persisting relationships
        Credential credential = createCredential(merchantCommand);
        Encryption encryption = createEncryption(merchantCommand);
        Fee commissionFee = createCommissionFee(merchantCommand);
        Fee returnFee = createReturnFee(merchantCommand);

        merchant.setCredential(credential);
        merchant.setEncryption(encryption);
        merchant.setCommissionFee(commissionFee);
        merchant.setReturnFee(returnFee);
        return merchant;
    }
}
