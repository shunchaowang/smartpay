package com.lambo.smartpay.ecs.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.CredentialStatus;
import com.lambo.smartpay.core.persistence.entity.CredentialType;
import com.lambo.smartpay.core.persistence.entity.EncryptionType;
import com.lambo.smartpay.core.persistence.entity.FeeType;
import com.lambo.smartpay.core.persistence.entity.Merchant;
import com.lambo.smartpay.core.persistence.entity.MerchantStatus;
import com.lambo.smartpay.core.service.CredentialStatusService;
import com.lambo.smartpay.core.service.CredentialTypeService;
import com.lambo.smartpay.core.service.EncryptionTypeService;
import com.lambo.smartpay.core.service.FeeTypeService;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.MerchantStatusService;
import com.lambo.smartpay.ecs.web.exception.BadRequestException;
import com.lambo.smartpay.ecs.web.exception.IntervalServerException;
import com.lambo.smartpay.ecs.web.vo.MerchantCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

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

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model
            model) {

        Merchant merchant;
        try {
            merchant = merchantService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        MerchantCommand merchantCommand = createMerchantCommand(merchant);
        model.addAttribute("merchantCommand", merchantCommand);

        model.addAttribute("action", "show");
        return "main";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {

        Merchant merchant = UserResource.getCurrentUser().getMerchant();
        MerchantCommand merchantCommand = createMerchantCommand(merchant);

        model.addAttribute("merchantCommand", merchantCommand);
        model.addAttribute("action", "edit");
        return "main";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model,
                       @ModelAttribute("merchantCommand") MerchantCommand merchantCommand) {

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

//        merchant = UserResource.getCurrentUser().getMerchant();
//        MerchantCommand command = createMerchantCommand(merchant);
//        model.addAttribute("merchantCommand", command);
//        model.addAttribute("action", "show");

        return "redirect:/index";
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
        //merchant.setIdentity(merchantCommand.getIdentity());
        //merchant.setMerchantStatus(merchantStatus);
        //merchant.setName(merchantCommand.getName());
        merchant.setActive(true);
        merchant.setAddress(merchantCommand.getAddress());
        merchant.setContact(merchantCommand.getContact());
        merchant.setTel(merchantCommand.getTel());
        merchant.setEmail(merchantCommand.getEmail());
        merchant.setRemark(merchantCommand.getRemark());

        //set merchant credential
        //Credential credential = setCredential(merchantCommand, merchant);
        //merchant.setCredential(credential);

        return merchant;

    }

    private MerchantCommand createMerchantCommand(Merchant merchant) {
        MerchantCommand merchantCommand = new MerchantCommand();

        if (merchant.getId() != null) {
            merchantCommand.setId(merchant.getId());
        }
        if (merchant.getIdentity() != null) {
            merchantCommand.setIdentity(merchant.getIdentity());
        }

        merchantCommand.setMerchantStatusId(merchant.getMerchantStatus().getId());
        merchantCommand.setMerchantStatusName(merchant.getMerchantStatus().getName());
        merchantCommand.setName(merchant.getName());
        merchantCommand.setName(merchant.getName());
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
