package inflearn.security.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import inflearn.security.practice.account.Account;
import inflearn.security.practice.account.AccountService;

/*
 * @Component public class AccountRunner implements ApplicationRunner{
 * 
 * @Autowired AccountService accountService;
 * 
 * @Override public void run(ApplicationArguments args) throws Exception {
 * Account yuseok = accountService.createAccount("yuseok", "1234");
 * System.out.println(yuseok.getUsername() + yuseok.getPassword());
 * 
 * }
 * 
 * }
 */
