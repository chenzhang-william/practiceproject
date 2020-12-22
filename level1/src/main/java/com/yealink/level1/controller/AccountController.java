package com.yealink.level1.controller;

import com.yealink.level1.bean.Account;
import com.yealink.level1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public int add(@RequestBody Account account){
        return accountService.add(account);
    }

    @GetMapping("/find/{id}")
    public Account findAccountById(@PathVariable("id")String id){
        return accountService.findAccountById(id);
    }

    @GetMapping("/find1/{staff_id}")
    public List<Account> findAccountByStaffId(@PathVariable("staff_id") String staffId){
        return accountService.findAccountByStaffId(staffId);
    }
}
