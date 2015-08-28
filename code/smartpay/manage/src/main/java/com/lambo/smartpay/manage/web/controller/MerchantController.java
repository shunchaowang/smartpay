package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.Credential;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.Encryption;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.Fee;
import com.lambo.smartpay.core.persistence.entity.FeeCategory;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.persistence.entity.WithdrawalSetting;
import com.lambo.smartpay.core.service.CredentialStatusService;
import com.lambo.smartpay.core.service.CredentialTypeService;
import com.lambo.smartpay.core.service.EncryptionTypeService;
import com.lambo.smartpay.core.service.FeeCategoryService;
import com.lambo.smartpay.core.service.FeeTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.MerchantStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.DataTablesParams;
import com.lambo.smartpay.manage.util.JsonUtil;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.MerchantCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesMerchant;
import com.lambo.smartpay.manage.web.vo.table.DataTablesMerchantSetting;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by swang on 3/20/2015.
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

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
    @Resource
    private FeeCategoryService feeCategoryService;

    // index view
    @RequestMapping(value = {"/index/all"}, method = RequestMethod.GET)
    public String indexAll(Model model) {
        model.addAttribute("_view", "merchant/indexAll");
        return "main";
    }

    // fee view
    @RequestMapping(value = {"/index/setting"}, method = RequestMethod.GET)
    public String indexSetting(Model model) {
        model.addAttribute("_view", "merchant/indexSetting");
        return "main";
    }

    // archive view
    //TODO @PreAuthorize("isAuthenticated() and hasPermission('', 'isAdmin')")
    @RequestMapping(value = {"/index/archive"}, method = RequestMethod.GET)
    public String indexArchive(Model model) {
        model.addAttribute("_view", "merchant/indexArchive");
        return "main";
    }

    // ajax for DataTables
    @RequestMapping(value = "/list/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listAll(HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);

        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }
        Merchant merchantCriteria = new Merchant();
        merchantCriteria.setActive(true);
        List<Merchant> merchants = merchantService.findByCriteria(merchantCriteria,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = merchantService.countAll();
        Long recordsFiltered = merchantService.countByCriteria(params.getSearch());

        logger.debug("here before if null: " + merchants + recordsTotal + recordsFiltered);
        if (merchants == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }
        logger.debug("here after if null: " + merchants + recordsTotal + recordsFiltered);

        List<DataTablesMerchant> dataTablesMerchants = new ArrayList<>();
        for (Merchant merchant : merchants) {
            DataTablesMerchant tablesMerchant = new DataTablesMerchant(merchant);
            dataTablesMerchants.add(tablesMerchant);
        }
        logger.debug("here after tables");

        DataTablesResultSet<DataTablesMerchant> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesMerchants);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());
        logger.debug("All merchants: " + JsonUtil.toJson(resultSet));
        return JsonUtil.toJson(resultSet);
    }

    // ajax for DataTables of merchant settings
    @RequestMapping(value = "/list/setting", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listSetting(HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);

        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        Merchant merchantCriteria = new Merchant();
        merchantCriteria.setActive(true);
        List<Merchant> merchants = merchantService.findByCriteria(merchantCriteria,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = merchantService.countAll();
        Long recordsFiltered = merchantService.countByCriteria(params.getSearch());

        if (merchants == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesMerchantSetting> dataTablesMerchantSettings = new ArrayList<>();
        for (Merchant merchant : merchants) {
            DataTablesMerchantSetting merchantSetting = new DataTablesMerchantSetting(merchant);
            dataTablesMerchantSettings.add(merchantSetting);
        }

        DataTablesResultSet<DataTablesMerchantSetting> resultSet = new DataTablesResultSet<>();
        resultSet.setData(dataTablesMerchantSettings);
        resultSet.setRecordsTotal(recordsTotal.intValue());
        resultSet.setRecordsFiltered(recordsFiltered.intValue());

        return JsonUtil.toJson(resultSet);
    }

    // ajax for DataTables
    @RequestMapping(value = "/list/archive", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listArchive(HttpServletRequest request) {

        DataTablesParams params = new DataTablesParams(request);

        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        Merchant merchantCriteria = new Merchant();
        merchantCriteria.setActive(false);
        List<Merchant> merchants = merchantService.findByCriteria(merchantCriteria,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = merchantService.countAll();
        Long recordsFiltered = merchantService.countByCriteria(params.getSearch());

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

        return JsonUtil.toJson(resultSet);
    }


    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Merchant merchant;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Merchant  " + id + " not found.");
        }
        MerchantCommand merchantCommand = new MerchantCommand(merchant);
        model.addAttribute("merchantCommand", merchantCommand);

        model.addAttribute("_view", "merchant/show");
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
        String label = messageSource.getMessage("merchant.label", null, locale);
        try {
            merchant = merchantService.freezeMerchant(id);
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
        return JsonUtil.toJson(response);
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
        String label = messageSource.getMessage("merchant.label", null, locale);
        try {
            merchant = merchantService.unfreezeMerchant(id);
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
        return JsonUtil.toJson(response);
    }

    /**
     * ajax calls to unfreeze a merchant by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/archive", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String archive(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Merchant merchant;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("merchant.label", null, locale);
        try {
            merchant = merchantService.archiveMerchant(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notUnfrozenMessage = messageSource.getMessage("not.archive.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notUnfrozenMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String unfrozenMessage = messageSource.getMessage("archive.message",
                new String[]{label, merchant.getName()}, locale);
        response.setMessage(unfrozenMessage);
        return JsonUtil.toJson(response);
    }


    /**
     * ajax calls to unfreeze a merchant by id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/restore", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String restore(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Merchant merchant;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("merchant.label", null, locale);
        try {
            merchant = merchantService.restoreMerchant(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notUnfrozenMessage = messageSource.getMessage("not.restore.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notUnfrozenMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String unfrozenMessage = messageSource.getMessage("restore.message",
                new String[]{label, merchant.getName()}, locale);
        response.setMessage(unfrozenMessage);
        return JsonUtil.toJson(response);
    }



    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        MerchantCommand command = new MerchantCommand();
        model.addAttribute("merchantCommand", command);
        List<MerchantStatus> merchantStatuses = merchantStatusService.getAll();
        model.addAttribute("merchantStatuses", merchantStatuses);
        List<CredentialType> credentialTypes = credentialTypeService.getAll();
        model.addAttribute("credentialTypes", credentialTypes);
        List<CredentialStatus> credentialStatuses = credentialStatusService.getAll();
        model.addAttribute("credentialStatuses", credentialStatuses);
        List<EncryptionType> encryptionTypes = encryptionTypeService.getAll();
        model.addAttribute("encryptionTypes", encryptionTypes);

        List<FeeType> feeTypes = feeTypeService.getAll();
        model.addAttribute("feeTypes", feeTypes);
        model.addAttribute("_view", "merchant/create");
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(Model model,
                       @ModelAttribute("merchantCommand") MerchantCommand merchantCommand) {
        model.addAttribute("merchantCommand", new MerchantCommand());

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields

        // set identity
        Long count = merchantService.countAll();
        String identity = "M" + String.format("%07d", count);
        while (merchantService.findByIdentity(identity) != null) {
            count++;
            identity = "M" + String.format("%07d", count);
        }
        merchantCommand.setIdentity(identity);
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
        return "redirect:/merchant/index/all";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {

        String merchantId = request.getParameter("merchantId");
        if (StringUtils.isBlank(merchantId)) {
            throw new BadRequestException("400", "Merchant id is blank.");
        }
        Long id = Long.valueOf(merchantId);
        Merchant merchant = null;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Merchant " + id + " not found.");
        }

        MerchantCommand merchantCommand = new MerchantCommand(merchant);
        List<MerchantStatus> merchantStatuses = merchantStatusService.getAll();
        List<CredentialType> credentialTypes = credentialTypeService.getAll();
        List<CredentialStatus> credentialStatuses = credentialStatusService.getAll();

        ModelAndView view = new ModelAndView("merchant/_basicInfoDialog");
        view.addObject("merchantCommand", merchantCommand);
        view.addObject("merchantStatuses", merchantStatuses);
        view.addObject("credentialStatuses", credentialStatuses);
        view.addObject("credentialTypes", credentialTypes);
        return view;
    }

    /**
     * Edit basic info for the merchant.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveBasicInfo(HttpServletRequest request) {
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("merchant.label", null, locale);
        Merchant merchant = editMerchantBasicInfo(request);
        try {
            merchant = merchantService.update(merchant);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, merchant.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, merchant.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{label, merchant.getIdentity()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/edit/setting", method = RequestMethod.GET)
    public ModelAndView editSetting(HttpServletRequest request) {

        String merchantId = request.getParameter("merchantId");
        if (StringUtils.isBlank(merchantId)) {
            throw new BadRequestException("400", "Merchant id is blank.");
        }
        Long id = Long.valueOf(merchantId);
        Merchant merchant = null;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Merchant " + id + " not found.");
        }

        MerchantCommand merchantCommand = new MerchantCommand(merchant);
        List<EncryptionType> encryptionTypes = encryptionTypeService.getAll();
        List<FeeType> feeTypes = feeTypeService.getAll();

        ModelAndView view = new ModelAndView("merchant/_settingDialog");
        view.addObject("merchantCommand", merchantCommand);
        view.addObject("encryptionTypes", encryptionTypes);
        view.addObject("feeTypes", feeTypes);
        return view;
    }

    /**
     * Edit transaction fees for the merchant.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit/setting", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveSetting(HttpServletRequest request) {
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("merchant.label", null, locale);
        Merchant merchant = editMerchantSetting(request);
        try {
            merchant = merchantService.update(merchant);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, merchant.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, merchant.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{label, merchant.getIdentity()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
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

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("merchant.label", null, locale);
        try {
            merchant = merchantService.delete(id);

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
        return JsonUtil.toJson(response);
    }

    private Merchant editMerchantBasicInfo(HttpServletRequest request) {
        Merchant merchant = null;
        try {
            merchant = merchantService.get(Long.valueOf(request.getParameter("id")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        merchant.setName(request.getParameter("name"));
        merchant.setEmail(request.getParameter("email"));
        merchant.setAddress(request.getParameter("address"));
        merchant.setContact(request.getParameter("contact"));
        merchant.setTel(request.getParameter("tel"));
        merchant.setRemark(request.getParameter("remark"));
        Long merchantStatusId = Long.valueOf(request.getParameter("merchantStatus"));
        MerchantStatus merchantStatus = null;
        try {
            merchantStatus = merchantStatusService.get(merchantStatusId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }
        merchant.setMerchantStatus(merchantStatus);

        // set credential
        Credential credential = merchant.getCredential();
        credential.setContent(request.getParameter("credentialContent"));
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        Date date = Calendar.getInstance(locale).getTime();
        try {
            date = dateFormat.parse(request.getParameter("credentialExpirationTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        credential.setExpirationDate(date);
        CredentialStatus credentialStatus = null;
        try {
            credentialStatus = credentialStatusService
                    .get(Long.valueOf(request.getParameter("credentialStatus")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        credential.setCredentialStatus(credentialStatus);
        CredentialType credentialType = null;
        try {
            credentialType = credentialTypeService
                    .get(Long.valueOf(request.getParameter("credentialType")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        credential.setCredentialType(credentialType);

        return merchant;
    }

    private Merchant editMerchantSetting(HttpServletRequest request) {
        Merchant merchant = null;
        try {
            merchant = merchantService.get(Long.valueOf(request.getParameter("id")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        EncryptionType encryptionType = null;
        FeeType commissionFeeType = null;
        FeeType returnFeeType = null;
        try {
            encryptionType = encryptionTypeService
                    .get(Long.valueOf(request.getParameter("encryptionType")));
            commissionFeeType = feeTypeService
                    .get(Long.valueOf(request.getParameter("commissionFeeType")));
            returnFeeType = feeTypeService
                    .get(Long.valueOf(request.getParameter("returnFeeType")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        merchant.getEncryption().setKey(request.getParameter("encryptionKey"));
        merchant.getEncryption().setEncryptionType(encryptionType);

        //TODO commented out
//        merchant.getCommissionFee().setValue(
//                Float.valueOf(request.getParameter("commissionFeeValue")));
//        merchant.getCommissionFee().setFeeType(commissionFeeType);
//        merchant.getReturnFee().setValue(Float.valueOf(request.getParameter("returnFeeValue")));
//        merchant.getReturnFee().setFeeType(returnFeeType);

        return merchant;
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

    private Set<Fee> createCommissionFees(MerchantCommand merchantCommand) {
        Set<Fee> fees = new HashSet<>();

        FeeType feeType = null;
        FeeCategory feeCategory = null;
        Fee fee = new Fee();

        // visa fee
        try {
            feeType = feeTypeService.get(merchantCommand.getCommissionVisaFeeTypeId());
            feeCategory = feeCategoryService.findByCode(ResourceProperties.FEE_CATEGORY_VISA_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setActive(true);
        fee.setValue(merchantCommand.getCommissionVisaFeeValue());
        fee.setFeeType(feeType);
        fee.setFeeCategory(feeCategory);
        fees.add(fee);

        // master fee
        try {
            feeType = feeTypeService.get(merchantCommand.getCommissionMasterFeeTypeId());
            feeCategory = feeCategoryService.findByCode(ResourceProperties.FEE_CATEGORY_VISA_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setActive(true);
        fee.setValue(merchantCommand.getCommissionMasterFeeValue());
        fee.setFeeType(feeType);
        fee.setFeeCategory(feeCategory);
        fees.add(fee);

        // jcb fee
        try {
            feeType = feeTypeService.get(merchantCommand.getCommissionJcbFeeTypeId());
            feeCategory = feeCategoryService.findByCode(ResourceProperties.FEE_CATEGORY_VISA_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setValue(merchantCommand.getCommissionJcbFeeValue());
        fee.setFeeType(feeType);
        fee.setFeeCategory(feeCategory);
        fees.add(fee);

        return fees;
    }

    private WithdrawalSetting createWithdrawalSetting(MerchantCommand merchantCommand) {

        WithdrawalSetting setting = new WithdrawalSetting();

        // basic info
        setting.setMaxDays(merchantCommand.getWithdrawSettingMaxDays());
        setting.setMinDays(merchantCommand.getWithdrawSettingMinDays());

        // fee
        Fee fee = new Fee();
        FeeType feeType = null;
        FeeCategory feeCategory = null;
        try {
            feeType = feeTypeService.get(merchantCommand.getWithdrawFeeTypeId());
            feeCategory = feeCategoryService
                    .findByCode(ResourceProperties.FEE_CATEGORY_WITHDRAWAL_SECURITY_CODE);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        fee.setValue(merchantCommand.getWithdrawFeeValue());
        fee.setFeeType(feeType);
        fee.setFeeCategory(feeCategory);
        setting.setSecurityFee(fee);

        return setting;
    }

    //TODO commented out, not used right now
    private Fee createReturnFee(MerchantCommand merchantCommand) {
        Fee fee = new Fee();
        FeeType feeType = null;
        //TODO commented out
//        try {
//            feeType = feeTypeService.get(merchantCommand.getReturnFeeTypeId());
//        } catch (NoSuchEntityException e) {
//            e.printStackTrace();
//        }
//        fee.setActive(true);
//        fee.setValue(merchantCommand.getReturnFeeValue());
//        fee.setFeeType(feeType);

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
            logger.debug("Merchant " + merchantCommand.getId() + "is not null");
            merchant.setId(merchantCommand.getId());
        }
        merchant.setIdentity(merchantCommand.getIdentity());
        merchant.setMerchantStatus(merchantStatus);
        merchant.setName(merchantCommand.getName());
        merchant.setAddress(merchantCommand.getAddress());
        merchant.setContact(merchantCommand.getContact());
        merchant.setTel(merchantCommand.getTel());
        merchant.setEmail(merchantCommand.getEmail());
        merchant.setRemark(merchantCommand.getRemark());

        // create all persisting relationships
        Credential credential = createCredential(merchantCommand);
        Encryption encryption = createEncryption(merchantCommand);
        merchant.setCredential(credential);
        merchant.setEncryption(encryption);

        // create fees for merchant
        merchant.setFees(createCommissionFees(merchantCommand));
        // create withdrawal setting
        merchant.setWithdrawalSetting(createWithdrawalSetting(merchantCommand));

        return merchant;
    }
}
