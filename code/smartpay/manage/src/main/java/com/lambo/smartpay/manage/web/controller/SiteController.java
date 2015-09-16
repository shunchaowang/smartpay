package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.exception.MissingRequiredFieldException;
import com.lambo.smartpay.core.exception.NoSuchEntityException;
import com.lambo.smartpay.core.exception.NotUniqueException;
import com.lambo.smartpay.core.persistence.entity.*;
import com.lambo.smartpay.core.service.MerchantService;
import com.lambo.smartpay.core.service.SiteService;
import com.lambo.smartpay.core.service.SiteStatusService;
import com.lambo.smartpay.core.util.ResourceProperties;
import com.lambo.smartpay.manage.util.*;
import com.lambo.smartpay.manage.web.exception.BadRequestException;
import com.lambo.smartpay.manage.web.exception.IntervalServerException;
import com.lambo.smartpay.manage.web.exception.RemoteAjaxException;
import com.lambo.smartpay.manage.web.vo.SiteCommand;
import com.lambo.smartpay.manage.web.vo.table.DataTablesResultSet;
import com.lambo.smartpay.manage.web.vo.table.DataTablesSite;
import com.lambo.smartpay.manage.web.vo.table.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by linly on 3/16/2015.
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteStatusService siteStatusService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/index/all"}, produces = "application/json;charset=UTF-8" )
    public String index(Model model ,HttpServletRequest request) {
       String ms= request.getParameter("message");
        model.addAttribute("_view", "site/indexAll");
        return "main";
    }

    @RequestMapping(value = "/list/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request) {
        DataTablesParams params = new DataTablesParams(request);
        if (params.getOffset() == null || params.getMax() == null
                || params.getOrder() == null || params.getOrderDir() == null) {
            throw new BadRequestException("400", "Bad Request.");
        }

        Site siteCriteria = new Site();
        siteCriteria.setActive(true);
        List<Site> sites = siteService.findByCriteria(siteCriteria,
                params.getSearch(),
                Integer.valueOf(params.getOffset()),
                Integer.valueOf(params.getMax()), params.getOrder(),
                ResourceProperties.JpaOrderDir.valueOf(params.getOrderDir()));
        Long recordsTotal = siteService.countByCriteria(siteCriteria);
        Long recordsFiltered = siteService.countByCriteria(siteCriteria, params.getSearch());

        if (sites == null || recordsTotal == null || recordsFiltered == null) {
            throw new RemoteAjaxException("500", "Internal Server Error.");
        }

        List<DataTablesSite> dataTablesSites = new ArrayList<>();
        for (Site site : sites) {
            DataTablesSite tableSite = new DataTablesSite(site);
            dataTablesSites.add(tableSite);
        }

        DataTablesResultSet<DataTablesSite> result = new DataTablesResultSet<>();
        result.setData(dataTablesSites);
        result.setRecordsFiltered(recordsFiltered.intValue());
        result.setRecordsTotal(recordsTotal.intValue());

        return JsonUtil.toJson(result);
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        model.addAttribute("_view", "site/create");
        model.addAttribute("siteCommand", new SiteCommand());
        model.addAttribute("merchants", merchantService.getAll());
        model.addAttribute("siteStatuses", siteStatusService.getAll());
        return "main";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("siteCommand") SiteCommand siteCommand,
                         RedirectAttributes attributes) {

        // message locale
        Locale locale = LocaleContextHolder.getLocale();
        //TODO verify required fields
        Long count = siteService.countAll();
        String identity = "S" + String.format("%07d", count);
        while (siteService.findByIdentity(identity) != null) {
            count++;
            identity = "S" + String.format("%07d", count);
        }
        siteCommand.setIdentity(identity);
        // check uniqueness
        if (siteService.findByUrl(siteCommand.getUrl()) != null) {
            String fieldLabel = messageSource.getMessage("site.url.label", null, locale);
            model.addAttribute("message",
                    messageSource.getMessage("not.unique.message",
                            new String[]{fieldLabel, siteCommand.getName()}, locale));
            model.addAttribute("siteCommand", siteCommand);
            model.addAttribute("_view", "site/create");
        }

        Site site = createSite(siteCommand);
        try {
            siteService.create(site);
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        } catch (NotUniqueException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }
        String fieldLabel = messageSource.getMessage("site.label", null, locale);
        attributes.addFlashAttribute("message",
                messageSource.getMessage("created.message",
                        new String[]{fieldLabel, siteCommand.getName()}, locale));

        return "redirect:/site/index/all";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {

        String siteId = request.getParameter("siteId");
        if (StringUtils.isBlank(siteId)) {
            throw new BadRequestException("400", "Site id is blank.");
        }
        Long id = Long.valueOf(siteId);
        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site " + id + " not found.");
        }

        SiteCommand siteCommand = new SiteCommand(site);
        ModelAndView view = new ModelAndView("site/_editDialog");
        view.addObject("siteCommand", siteCommand);
        view.addObject("siteStatuses", siteStatusService.getAll());
        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request) {

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("site.label", null, locale);
        Site site = editSite(request);
        try {
            site = siteService.update(site);
        } catch (NotUniqueException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, site.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        } catch (MissingRequiredFieldException e) {
            e.printStackTrace();
            String notSavedMessage = messageSource.getMessage("not.saved.message",
                    new String[]{label, site.getIdentity()}, locale);
            response.setMessage(notSavedMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String message = messageSource.getMessage("saved.message",
                new String[]{label, site.getIdentity()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String delete(@RequestParam(value = "id") Long id) {

        if (id == null) {
            throw new BadRequestException("400", "id is null.");
        }

        Site site;

        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("Site.label", null, locale);
        try {
            site = siteService.delete(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            String notDeleteMessage = messageSource.getMessage("not.deleted.message",
                    new String[]{label, id.toString()}, locale);
            response.setMessage(notDeleteMessage);
            throw new BadRequestException("400", e.getMessage());
        }

        String deletedMessage = messageSource.getMessage("deleted.message",
                new String[]{label, site.getName()}, locale);
        response.setMessage(deletedMessage);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String approve(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("site.label", null, locale);
        String message = "";
        //Do approve
        try {
            site = siteService.approveSite(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.approve.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("approve.message", new String[]{label, site.getName()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/decline", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String decline(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("site.label", null, locale);
        String message = "";
        //Do approve
        try {
            site = siteService.declineSite(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.decline.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("decline.message", new String[]{label, site.getName()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/freeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String freeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("site.label", null, locale);
        String message = "";
        //Do approve
        try {
            site = siteService.freezeSite(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.frozen.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("frozen.message", new String[]{label, site.getName()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }


    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String unfreeze(@RequestParam(value = "id") Long id) {

        if (id == null) {
            return null;
        }
        Site site = null;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            return null;
        }
        JsonResponse response = new JsonResponse();
        Locale locale = LocaleContextHolder.getLocale();
        String label = messageSource.getMessage("site.label", null, locale);
        String message = "";
        //Do approve
        try {
            site = siteService.unfreezeSite(id);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            message = messageSource
                    .getMessage("not.unfrozen.message", new String[]{label, id.toString()}, locale);
            response.setMessage(message);
            throw new BadRequestException("400", e.getMessage());
        }

        message = messageSource
                .getMessage("unfrozen.message", new String[]{label, site.getName()}, locale);
        response.setMessage(message);
        return JsonUtil.toJson(response);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {

        Site site;
        try {
            site = siteService.get(id);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", "Site  " + id + " not found.");
        }
        SiteCommand siteCommand = new SiteCommand(site);
        model.addAttribute("siteCommand", siteCommand);

        model.addAttribute("_view", "site/show");
        return "main";
    }

    // create SiteCommand from User
    private Site createSite(SiteCommand siteCommand) {
        //
        Site site = new Site();
        SiteStatus siteStatus = null;
        Merchant merchant = null;

        try {
            merchant = merchantService.get(siteCommand.getMerchant());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        try {
            siteStatus = siteStatusService.get(siteCommand.getSiteStatusId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        site.setIdentity(siteCommand.getIdentity());
        site.setMerchant(merchant);
        site.setName(siteCommand.getName());
        site.setUrl(siteCommand.getUrl());
        site.setReturnUrl(siteCommand.getReturnUrl());
        site.setSiteStatus(siteStatus);
        site.setRemark(siteCommand.getRemark());

        return site;
    }

    private Site editSite(HttpServletRequest request) {

        Site site = null;
        try {
            site = siteService.get(Long.valueOf(request.getParameter("id")));
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new BadRequestException("400", e.getMessage());
        }
        site.setName(request.getParameter("name"));
        site.setReturnUrl(request.getParameter("returnUrl"));
        site.setUrl(request.getParameter("url"));
        site.setRemark(request.getParameter("remark"));
        Long siteStatusId = Long.valueOf(request.getParameter("siteStatus"));
        SiteStatus siteStatus = null;
        try {
            siteStatus = siteStatusService.get(siteStatusId);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
            throw new IntervalServerException("500", e.getMessage());
        }
        site.setSiteStatus(siteStatus);
        return site;
    }
    /**
     * 模板下载
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downtemplate", method = RequestMethod.GET)
    public void download(HttpServletRequest request,HttpServletResponse response
            ,HttpSession session){
        logger.info("下载模板downtemplate");
        response.setContentType("text/html;charset=UTF-8");
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            request.setCharacterEncoding("UTF-8");
            /*URL xmlpath = this.getClass().getClassLoader().getResource("");
            File f = new File(xmlpath + "/xls/ORDER_SENDGOODS_TEMPLATE.xls");*/
            String realpath = session.getServletContext().getRealPath("/WEB-INF");
            File f = new File(this.getClass().getResource("/").getPath() + "/xls/Mall_bulk_loading.xls");
            response.setContentType("application/x-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="
                    +new String("商城批量加载.xls".getBytes(), "iso-8859-1"));
            response.setHeader("Content-Length",String.valueOf(f.length()));
            byte[] data = new byte[1024];
            in = new BufferedInputStream(new FileInputStream(f));
            out = new BufferedOutputStream(response.getOutputStream());
            int len = 0;
            while (-1 != (len=in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }

    /**
     * 上传商城批量上传的模板
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadOrderSendGoods", method = RequestMethod.POST)
    public String   handleFormUpload(@RequestParam("file") MultipartFile file,RedirectAttributes attr,HttpServletRequest request) {
        String errMsg = "";
        String returnString = "";
        if (!file.isEmpty()) {
            try{
                this.SaveFileFromInputStream(file.getInputStream(),
                        this.getClass().getResource("/").getPath(), "java.xls");
                File f = new File(this.getClass().getResource("/").getPath()+"/xls/java.xls");
                List<List<Object>> list = new ArrayList<List<Object>>();
                int startrows = 2;// 从标题开始算行号
                String msg = ExcelFileUtil.readExcel(f,
                        ImportFileTemplete.ORDER_SENDGOODS_FORMAT1, list, 0);
                if (null != msg) {
                   /* response.setContentType("text/html;charset=UTF-8");
                    try {
                        response.getWriter().print("<script>alert('商户信息文件表结构非法!');location.href='importInitzj_vendor'</script>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    return "main";
                }
                else{

                    if (null != list && list.size() > 0) {
                        //startrows = startrows + 2;
                        int succcount = 0;// 成功条数
                        int errorcount = 0;// 失败条数

                        // 商户编号验证
                        for (int i = 0; i < list.size(); i++) {
                            Site site = new Site();
                            if (list.get(i).get(0) != null) {
                                Merchant merchant = null;
                                merchant = merchantService.findByIdentity(list.get(i).get(0).toString().trim());
                                if(merchant==null || merchant.getId()==null){
                                    errMsg+="商户编号不存在，在第"+(startrows+i)+"条";
                                }
                                else {
                                    site.setMerchant(merchant);
                                }
                            }else{
                                errorcount++;
                                errMsg+="请输入商户编号,在第"+(startrows+i)+"条";
                            }
                            if (list.get(i).get(1) != null) {
                                site.setName(list.get(i).get(1).toString().trim());// 商城名称
                            }
                            if (list.get(i).get(2) != null) {


                                site.setUrl(list.get(i).get(2).toString().trim());// 商城地址
                                site.setReturnUrl(list.get(i).get(2).toString().trim()); //商城返回地址
                            }
                            site.setActive(false);
                            SiteStatus status=siteStatusService.get(1l);
                            site.setSiteStatus(status);

                            Long count = siteService.countAll();
                            String identity = "S" + String.format("%07d", count);
                            while (siteService.findByIdentity(identity) != null) {
                                count++;
                                identity = "S" + String.format("%07d", count);
                            }
                            site.setIdentity(identity);

                            try{
                                siteService.create(site);
                                succcount++;





                            }catch(Exception e){
                                e.printStackTrace();
                                errorcount++;
                                errMsg+="失败在第"+(i+startrows)+"条<br/>";
                            }
                        }
                        if(errorcount==0){
                            returnString +="成功插入"+succcount+"条";

                        }
                        else{

                            returnString += "成功插入"+succcount+"条<br/>"+
                            "失败"+errorcount+"条<br/>"
                                    +errMsg;
                        }

                    }

                }
            }catch(Exception ex){

                ex.printStackTrace();
            }
        }



//        errMsg="共有成功1条"+"失败2条"+"失败在第2条";
        request.setAttribute("message", returnString);

        return "forward:/site/index/all";
//        request.setAttribute("errMsg1",errMsg);
////        attr.addAttribute("errMsg",errMsg);
//       attr.addFlashAttribute("errMsg2",errMsg);
//        return "forward:/site/index/all";

     //  return "redirect:/site/index/all";
     //   return "forward:/site/index/all";
      //  return "redirect:/site/index/all";
      //  return  "redirect:/site/index/all";
    }
    //将MultipartFile 转换为File
    public static void SaveFileFromInputStream(InputStream stream,String path,String savefile) throws IOException
    {
        FileOutputStream fs=new FileOutputStream( path + "/xls/"+ savefile);
        //  System.out.println("------------"+path + "/"+ savefile);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread=stream.read(buffer))!=-1)
        {
            bytesum+=byteread;
            fs.write(buffer,0,byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }
}
