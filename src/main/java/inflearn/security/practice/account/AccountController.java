package inflearn.security.practice.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/create")
	public Account create() {
		Account yuseok = accountService.createAccount("yuseok", "1234");
		return yuseok;
	}

}
