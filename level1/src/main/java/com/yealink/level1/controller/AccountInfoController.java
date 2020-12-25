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

    @GetMapping("/findById")
    public Account findAccountById(@RequestParam(value = "id",required = true)String id){
        return accountInfoService.findAccountById(id);
    }

    @GetMapping("/findByStaffId")
    public List<Account> findAccountByStaffId(@RequestParam(value = "staffId",required = true) String staffId){
        return accountInfoService.findAccountByStaffId(staffId);
    }

    @PostMapping("/update")
    public int updateAccount(@RequestBody Account account){
        return accountInfoService.update(account);
    }

    @DeleteMapping("/delete")
    public void deleteAccount(@RequestParam(value = "id",required = true)String id){
        accountInfoService.delete(id);
    }

    @GetMapping("/findIdByUsername")
    public String findIdByUsername(@RequestParam(value = "username",required = true)String username){
        return accountInfoService.findIdByUsername(username);
    }

    @GetMapping("/findByEnterpriseName")
    public List<Account> findByEnterpriseName(@RequestParam(value = "name",required = true)String name){
        return accountInfoService.findAccountByEnterpriseName(name);
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
}
