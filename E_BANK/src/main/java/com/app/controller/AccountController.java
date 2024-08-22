package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.account.Account;
import com.app.exceptions.InvalidTpinException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.service.AccountSevice;
import com.app.service.CustomerService;

@RestController
@RequestMapping("/bank")
@CrossOrigin(origins = "http://localhost:3000/")
public class AccountController {
	
	@Autowired
	private AccountSevice accService;
	
	@Autowired
	private CustomerService customerService;
	

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallaccountsactiavted")
	public ResponseEntity<?> getAllActivatedAccounts()
	{
		List<Account> activatedAccounts =accService.getAllActivatedAccount();
		if(activatedAccounts.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(activatedAccounts);
	}
	
	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallaccountsdeactivated")
	public ResponseEntity<?> getAllDeactivatedAccounts()
	{
		List<Account> deactivatedAccounts =accService.getAllDeactivatedAccount();
		if(deactivatedAccounts.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(deactivatedAccounts);
	}
	
	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallaccountssuspended")
	public ResponseEntity<?> getAllSuspendedAccounts()
	{
		List<Account> suspendedAccounts =accService.getAllSuspendedAccount();
		if(suspendedAccounts.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(suspendedAccounts);
	}
	
	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/updateStatusA/{accId}")
	public ResponseEntity<?> changeToActivate(@PathVariable("accId")String accId)
	{
		return ResponseEntity.ok(accService.changeStatusOfActivatedAccount(accId));
	}
	
	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/updateStatusS/{accId}")
	public ResponseEntity<?> changeToActivateSuspended(@PathVariable("accId")String accId)
	{
		return ResponseEntity.ok(accService.changeStatusOfSuspendedAccount(accId));
	}  
	
	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/updateStatusD/{accId}")
	public ResponseEntity<?> changeToDeactivate(@PathVariable("accId")String accId)
	{
		return ResponseEntity.ok(accService.changeStatusOfDeactivatedAccount(accId));
	}
	
//	@GetMapping("/user/balance/{accId}")
//	public ResponseEntity<?> getBalance(@PathVariable("accId")String accId)
//	{
//		return ResponseEntity.ok(accService.checkBalance(accId));
//				
//	}
	
//	@PostMapping("/user/verify-tpin/{customerId}")
//	 public ResponseEntity<String> verifyTpin(@PathVariable Long customerId, @RequestBody String tpin) {
//	        try {
//	            customerService.verifyCustomerTpin(customerId, tpin);
//	            return new ResponseEntity<>("TPIN verified successfully", HttpStatus.OK);
//	        } catch (ResourceNotFoundException e) {
//	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//	        } catch (InvalidTpinException e) {
//	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//	        }
//	    }
	

}
