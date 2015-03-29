package com.lambo.smartpay.manage.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.service.CredentialStatusService;
import com.lambo.smartpay.core.service.CredentialTypeService;
import com.lambo.smartpay.core.service.EncryptionTypeService;
import com.lambo.smartpay.core.service.FeeTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.MerchantStatusService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.core.service.UserStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.MerchantCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesMerchant;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.jgss.spi.MechanismFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by swang on 3/20/2015.
 */
@Controller
@RequestMapping("/merchant")
@Secured({"ROLE_ADMIN"})
public class MerchantController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantStatusService merchantStatusService;

    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteStatusService siteStatusService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStatusService userStatusService;

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
        return "merchant";
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

    @ModelAttribute("credentialStatuses")
    public List<CredentialStatus> credentialStatuses() {
        return credentialStatusService.getAll();
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
    @RequestMapping(value = {"/", "/index", "/indexMerchant"}, method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = {"/indexMerchantEdit"}, method = RequestMethod.GET)
    public String indexMerchantEdit(Model model) {
        logger.debug("~~~~~~~~~ indexMerchantEdit ~~~~~~~~~");
        model.addAttribute("domain", "MerchantEdit");
        return "main";
    }

    @RequestMapping(value = {"/indexMerchantFee"}, method = RequestMethod.GET)
    public String indexMerchantFee(Model model) {
        logger.debug("~~~~~~~~~ indexMerchantFee ~~~~~~~~~");
        model.addAttribute("domain", "MerchantFee");
        return "main";
    }

    @RequestMapping(value = {"/indexFreezeList"}, method = RequestMethod.GET)
    public String indexFreezeList(Model model) {
        logger.debug("~~~~~~~~~ indexFreezeList ~~~~~~~~~");
        model.addAttribute("domain", "FreezeList");
        return "main";
    }

    @RequestMapping(value = {"/indexUnfreezeList"}, method = RequestMethod.GET)
    public String indexUnfreezeList(Model model) {
        logger.debug("~~~~~~~~~ indexUnfreezeList ~~~~~~~~~");
        model.addAttribute("domain", "UnfreezeList");
        return "main";
    }

    // ajax for DataTables
    @RequestMapping(value = "/list{domain}", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listDomain(@PathVariable("domain") String domain,HttpServletRequest request) {

        logger.debug("~~~~~~~~~ listDomain ~~~~~~~~~" + domain);

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

        //
        String codeString = "";
        List<Merchant> merchants = null;
        Long recordsTotal;
        Long recordsFiltered;

        if(domain.equals("FreezeList"))
            codeString = ResourceProperties.MERCHANT_STATUS_NORMAL_CODE;
        if(domain.equals("UnfreezeList"))
            codeString = ResourceProperties.MERCHANT_STATUS_FROZEN_CODE;

        if(codeString.equals("")) {
            logger.debug("~~~~~~~~~~ merchant list ~~~~~~~~~~" + "all codeString ！！！");

            merchants = merchantService.findByCriteria(search, start,
                    length, order, ResourceProperties.JpaOrderDir.valueOf(orderDir));
            // count total and filtered
            recordsTotal = merchantService.countAll();
            recordsFiltered = merchantService.countByCriteria(search);

        }else {
            logger.debug("~~~~~~~~~~ merchant list ~~~~~~~~~~" + "codeString = " + codeString);
            // normal merchant status
            Merchant merchantCriteria = new Merchant();
            MerchantStatus status = null;
            try {
                status = merchantStatusService
                        .findByCode(codeString);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
                throw new BadRequestException("Cannot find MerchantStatus with Code",  codeString);
            }

            merchantCriteria.setMerchantStatus(status);
            merchants = merchantService.findByCriteria(merchantCriteria, search, start, length, order, ResourceProperties.JpaOrderDir.valueOf(orderDir));
            // count total and filtered
            recordsTotal = merchantService.countByCriteria(merchantCriteria);
            recordsFiltered = merchantService.countByCriteria(merchantCriteria, search);
        }

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

    @RequestMapping(value = "/show{domain}/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("domain") String domain, @PathVariable("id") Long id, Model
            model) {

        logger.debug("~~~~~~ whether come to here ??? " + "domain=" + domain + "id=" + id);

        Merchant merchant ;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        MerchantCommand merchantCommand = createMerchantCommand(merchant);
        model.addAttribute("merchantCommand", merchantCommand);
        if (domain != null) {
            model.addAttribute("domain", domain);
        }
        model.addAttribute("action", "show");
        return "main";
    }

    /**
     * ajax calls to freeze a merchant by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/freeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String freeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Merchant merchant;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Merchant.label", null, locale);
        try {
            merchant = merchantService.freezeMerchant(id);

            //TODO freeze all sites and users of the merchant

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notFrozenMessage = messageSource.getMessage("not.frozen.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notFrozenMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String frozenMessage = messageSource.getMessage("frozen.message",
                new String[]{label, merchant.getName()}, locale);
        response.setMessage(frozenMessage);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }

    /**
     * ajax calls to unfreeze a merchant by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String unfreeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Merchant merchant;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Merchant.label", null, locale);
        try {
            merchant = merchantService.unfreezeMerchant(id);

            //TODO unfreeze all sites and users of the merchant
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notUnfrozenMessage = messageSource.getMessage("not.unfrozen.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notUnfrozenMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String unfrozenMessage = messageSource.getMessage("unfrozen.message",
                new String[]{label, merchant.getName()}, locale);
        response.setMessage(unfrozenMessage);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
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

        logger.debug("create site here");

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

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        Merchant merchant;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        MerchantCommand merchantCommand = createMerchantCommand(merchant);

        model.addAttribute("merchantCommand", merchantCommand);
        model.addAttribute("action", "edit");
        return "main";
    }

    @RequestMapping(value = "/setfee/{id}", method = RequestMethod.GET)
    public String setfee(@PathVariable("id") Long id, Model model) {

        Merchant merchant;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "User " + id + " not found.");
        }

        MerchantCommand merchantCommand = createMerchantCommand(merchant);

        model.addAttribute("merchantCommand", merchantCommand);
        model.addAttribute("action", "setfee");
        return "main";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model,
                       @ModelAttribute("merchantCommand") MerchantCommand merchantCommand) {

        model.addAttribute("merchantCommand", merchantCommand);

        logger.debug("whether come to here ??? " + merchantCommand.getId());
        logger.debug("whether come to here ??? " + merchantCommand.getRemark());

        // message locale
        Locale locale = LocaleContextHolder.getLocale();

        Merchant merchant = setMerchant(merchantCommand);

        try {
            merchantService.update(merchant);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        return "main";
    }

    @RequestMapping(value = "/setfee", method = RequestMethod.POST)
    public String setfee(Model model,
                       @ModelAttribute("merchantCommand") MerchantCommand merchantCommand) {

        model.addAttribute("merchantCommand", merchantCommand);

        logger.debug("~~~~~~ whether come to here ??? " + merchantCommand.getId());

        // message locale
        Locale locale = LocaleContextHolder.getLocale();

        Merchant merchant = setMerchantFee(merchantCommand);

        try {
            merchantService.update(merchant);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }

        return "main";
    }

    /**
     * ajax calls to delete a user by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String delete(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Merchant merchant;

        logger.debug("here u r");

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Merchant.label", null, locale);
        try {
            merchant = merchantService.delete(id);
            logger.debug("here u r" + id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notDeleteMessage = messageSource.getMessage("not.deleted.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notDeleteMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String deletedMessage = messageSource.getMessage("deleted.message",
                new String[]{label, merchant.getName()}, locale);
        response.setMessage(deletedMessage);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
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

        //TODO HOW TO PARSE FROM STRING
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        Date date = Calendar.getInstance(locale).getTime();
        try {
            date = dateFormat.parse(merchantCommand.getCredentialExpirationTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        credential.setExpirationDate(date);
        credential.setCredentialStatus(credentialStatus);
        credential.setCredentialType(credentialType);
        return credential;
    }

    private Credential setCredential(MerchantCommand merchantCommand, Merchant merchant) {
        Credential credential = merchant.getCredential();
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

        //TODO HOW TO PARSE FROM STRING
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        Date date = Calendar.getInstance(locale).getTime();
        try {
            date = dateFormat.parse(merchantCommand.getCredentialExpirationTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        credential.setExpirationDate(date);
        credential.setCredentialStatus(credentialStatus);
        credential.setCredentialType(credentialType);
        credential.setContent(merchantCommand.getCredentialContent());
        //credential.setActive(true);
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

    //set encryption
    private Encryption setEncryption(MerchantCommand merchantCommand, Merchant merchant) {
        Encryption encryption = merchant.getEncryption();
        EncryptionType encryptionType = null;
        try {
            encryptionType = encryptionTypeService.get(merchantCommand.getEncryptionTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        encryption.setEncryptionType(encryptionType);
        encryption.setKey(merchantCommand.getEncryptionKey());
        //encryption.setActive(true);
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

    //set commission fee
    private Fee setCommissionFee(MerchantCommand merchantCommand, Merchant merchant) {
        Fee fee = merchant.getCommissionFee();
        FeeType feeType = null;
        try {
            feeType = feeTypeService.get(merchantCommand.getCommissionFeeTypeId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        //fee.setActive(true);
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

    private Fee setReturnFee(MerchantCommand merchantCommand,Merchant merchant) {
        Fee fee = merchant.getReturnFee();
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
        if (merchantCommand.getId() != null) {
            logger.debug("merchantCoomand.getId is not null");
            merchant.setId(merchantCommand.getId());
        }
        merchant.setIdentity(merchantCommand.getIdentity());
        merchant.setMerchantStatus(merchantStatus);
        merchant.setName(merchantCommand.getName());
        merchant.setActive(true);
        merchant.setAddress(merchantCommand.getAddress());
        merchant.setContact(merchantCommand.getContact());
        merchant.setTel(merchantCommand.getTel());
        merchant.setEmail(merchantCommand.getEmail());
        merchant.setRemark(merchantCommand.getRemark());

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

    private Merchant setMerchant(MerchantCommand merchantCommand) {
        Merchant merchant = null;
        MerchantStatus merchantStatus = null;
        try {
            merchant = merchantService.get(merchantCommand.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        try {
            merchantStatus = merchantStatusService.get(merchantCommand.getMerchantStatusId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        //set merchant basic info
        merchant.setIdentity(merchantCommand.getIdentity());
        merchant.setMerchantStatus(merchantStatus);
        merchant.setName(merchantCommand.getName());
        merchant.setActive(true);
        merchant.setAddress(merchantCommand.getAddress());
        merchant.setContact(merchantCommand.getContact());
        merchant.setTel(merchantCommand.getTel());
        merchant.setEmail(merchantCommand.getEmail());
        merchant.setRemark(merchantCommand.getRemark());

        //set merchant credential
        Credential credential = setCredential(merchantCommand, merchant);
        merchant.setCredential(credential);

        return merchant;

    }

    private Merchant setMerchantFee(MerchantCommand merchantCommand) {
        Merchant merchant = null;
        try {
            merchant = merchantService.get(merchantCommand.getId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        //set merchant encryption
        Encryption encryption = setEncryption(merchantCommand, merchant);
        merchant.setEncryption(encryption);

        //set merchant commissionFee&returnFee
        Fee commissionFee = setCommissionFee(merchantCommand, merchant);
        Fee returnFee = setReturnFee(merchantCommand, merchant);
        merchant.setCommissionFee(commissionFee);
        merchant.setReturnFee(returnFee);

        return merchant;

    }

    private MerchantCommand createMerchantCommand(Merchant merchant) {
        MerchantCommand merchantCommand = new MerchantCommand();

        /*
        //MerchantStatus merchantStatus = null;
        try {
            merchantStatus = merchantStatusService.get(merchantCommand.getMerchantStatusId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        */

        if(merchant.getId() != null) {merchantCommand.setId(merchant.getId());}
        if(merchant.getIdentity() != null) {merchantCommand.setIdentity(merchant.getIdentity());}

        merchantCommand.setMerchantStatusId(merchant.getMerchantStatus().getId());
        merchantCommand.setMerchantStatusName(merchant.getMerchantStatus().getName());
        merchantCommand.setName(merchant.getName());
        merchant.setName(merchantCommand.getName());
        merchantCommand.setActive(merchant.getActive());
        merchantCommand.setAddress(merchant.getAddress());
        merchantCommand.setContact(merchant.getContact());
        merchantCommand.setTel(merchant.getTel());
        merchantCommand.setEmail(merchant.getEmail());
        merchantCommand.setRemark(merchant.getRemark());

        // Credential info
        merchantCommand.setCredentialContent(merchant.getCredential().getContent());
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        merchantCommand.setCredentialExpirationTime(
                dateFormat.format(merchant.getCredential().getExpirationDate()));
        merchantCommand.setCredentialRemark(merchant.getCredential().getRemark());
        merchantCommand.setCredentialStatusId(merchant.getCredential().getCredentialStatus()
                .getId());
        merchantCommand
                .setCredentialStatusName(merchant.getCredential().getCredentialStatus().getName());
        merchantCommand.setCredentialTypeId(merchant.getCredential().getCredentialType().getId());
        merchantCommand
                .setCredentialTypeName(merchant.getCredential().getCredentialType().getName());

        // Encryption info
        merchantCommand.setEncryptionKey(merchant.getEncryption().getKey());
        merchantCommand.setEncryptionRemark(merchant.getEncryption().getRemark());
        merchantCommand.setEncryptionTypeId(merchant.getEncryption().getEncryptionType().getId());
        merchantCommand
                .setEncryptionTypeName(merchant.getEncryption().getEncryptionType().getName());

        // Commission Fee
        merchantCommand.setCommissionFeeRemark(merchant.getCommissionFee().getRemark());
        merchantCommand.setCommissionFeeTypeId(merchant.getCommissionFee().getFeeType().getId());
        merchantCommand
                .setCommissionFeeTypeName(merchant.getCommissionFee().getFeeType().getName());
        merchantCommand.setCommissionFeeValue(merchant.getCommissionFee().getValue());

        // Return Fee
        merchantCommand.setReturnFeeRemark(merchant.getReturnFee().getRemark());
        merchantCommand.setReturnFeeTypeId(merchant.getReturnFee().getFeeType().getId());
        merchantCommand.setReturnFeeTypeName(merchant.getReturnFee().getFeeType().getName());
        merchantCommand.setReturnFeeValue(merchant.getReturnFee().getValue());

        return merchantCommand;
    }
}
