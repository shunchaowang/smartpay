package com.lambo.smartpay.manage.web.controller;

import com.lambo.smartpay.core.persistence.entity.MenuCategory;
import com.lambo.smartpay.core.persistence.entity.MenuItem;
import com.lambo.smartpay.core.persistence.entity.Permission;
import com.lambo.smartpay.core.persistence.entity.User;
import com.lambo.smartpay.core.service.UserService;
import com.lambo.smartpay.manage.config.SecurityUser;
import com.lambo.smartpay.manage.web.vo.navigation.Menu;
import com.lambo.smartpay.manage.web.vo.navigation.SubMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by swang on 3/11/2015.
 */
@Controller
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private static UserService userService;

    public static SecurityUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User loginUser = userService.findByUsername(username);
            return new SecurityUser(loginUser);
        }
        return null;
    }

    public static List<Menu> getMenu() {
        SecurityUser securityUser = getCurrentUser();
        if (securityUser == null) {
            return null;
        }
        HashMap<String, Menu> menuMap = new HashMap<>();
        for (Permission permission : securityUser.getPermissions()) {
            MenuItem menuItem = permission.getMenuItem();
            if (menuItem == null) {
                continue;
            }
            MenuCategory menuCategory = menuItem.getMenuCategory();
            if (menuCategory == null) {
                continue;
            }
            if (!menuMap.containsKey(menuCategory.getName())) {
                menuMap.put(menuCategory.getName(), new Menu(menuCategory));
            }
            SubMenu subMenu = new SubMenu(menuItem);
            if (menuMap.get(menuCategory.getName()).getSubMenus().contains(subMenu)) {
                continue;
            }
            menuMap.get(menuCategory.getName()).getSubMenus().add(subMenu);
        }

        List<Menu> menus = new ArrayList<>(menuMap.values());
        Collections.sort(menus);
        for (Menu menu : menus) {
            Collections.sort(menu.getSubMenus());
        }
        return menus;
    }

    @Autowired
    public void setUserService(UserService userService) {
        UserResource.userService = userService;
    }
}
