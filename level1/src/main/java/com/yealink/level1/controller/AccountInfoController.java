package com.yealink.level1.controller;

import com.yealink.level1.bean.Account;
import com.yealink.level1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;

    @PostMapping("/add")
    public int addAccount(@RequestBody Account account){
        return accountInfoService.add(account);
    }



    @DeleteMapping("/delete")
    public void deleteAccount(@RequestParam(value = "username",required = true)String username){
        accountInfoService.delete(username);
    }

    @PostMapping("/update")
    public int updateAccount(@RequestBody Account account,
                             @RequestParam(value = "username",required = true)String username){
        return accountInfoService.update(account,username);
    }

    @PostMapping("/bindStaff")
    public int bindStaff(@RequestParam(value = "username",required = true) String username,
                         @RequestParam(value = "mobile",required = true) String mobile){
        return accountInfoService.bindAccountStaff(username,mobile);
    }

    @PostMapping("/bindEnterprise")
    public int bindEnterprise(@RequestParam(value = "username",required = true)String username,
                              @RequestParam(value = "name",required = true)String name){
        return accountInfoService.bindAccountEnterprise(username,name);
    }

    @GetMapping("/findByUsername")
    public Account findIdByUsername(@RequestParam(value = "username",required = true)String username){
        return accountInfoService.findAccountByUsername(username);
    }

    @GetMapping("/findByMobile")
    public List<Account> findAccountByStaffId(@RequestParam(value = "mobile",required = true) String mobile){
        return accountInfoService.findAccountByMobile(mobile);
    }

    @GetMapping("/findByEnterpriseName")
    public List<Account> findByEnterpriseName(@RequestParam(value = "name",required = true)String name){
        return accountInfoService.findAccountByEnterpriseName(name);
    }


}
