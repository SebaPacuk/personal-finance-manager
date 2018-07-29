package com.pfm.account;

import static com.pfm.config.MessagesProvider.ACCOUNT_WITH_PROVIDED_NAME_ALREADY_EXISTS;
import static com.pfm.config.MessagesProvider.EMPTY_ACCOUNT_BALANCE;
import static com.pfm.config.MessagesProvider.EMPTY_ACCOUNT_NAME;
import static com.pfm.config.MessagesProvider.getMessage;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountValidator {

  private AccountService accountService;

  private List<String> validate(Account account) {
    List<String> validationResults = new ArrayList<>();

    if (account.getName() == null || account.getName().trim().equals("")) {
      validationResults.add(getMessage(EMPTY_ACCOUNT_NAME));
    }

    if (account.getBalance() == null) {
      validationResults.add(getMessage(EMPTY_ACCOUNT_BALANCE));
    }

    return validationResults;
  }

  public List<String> validateAccountForAdd(Account account) {
    List<String> validationResults = validate(account);
    if (account.getName() != null && accountService.isAccountNameAlreadyUsed(account.getName())) {
      validationResults.add(getMessage(ACCOUNT_WITH_PROVIDED_NAME_ALREADY_EXISTS));
    }
    return validationResults;
  }

  public List<String> validateAccountForUpdate(Account account) {
    return validate(account);
  }

}