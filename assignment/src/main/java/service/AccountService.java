package service;

import exception.AccountException;
import lombok.RequiredArgsConstructor;
import model.Account;
import repository.AccountRepository;
import framework.annotation.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account signup(String name, String email, String password) {
        return accountRepository.save(name, email, password);
    }

    public Account login(String name, String password) {
        return accountRepository.findByName(name)
                .filter(a -> a.getAccountPassword().equals(password))
                .orElseThrow(() -> new AccountException("로그인에 실패했습니다."));
    }

    public Account getAccount(long accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new AccountException(accountId + "번 계정이 존재하지 않습니다.");
        }
        return account;
    }

    public Account updateAccount(long accountId, String password, String email) {
        return accountRepository.update(accountId, password, email);
    }

    public Account deleteAccount(long accountId) {
        Account deletedAccount = accountRepository.delete(accountId);
        if (deletedAccount == null) {
            throw new AccountException(accountId + "번 계정이 존재하지 않습니다.");
        }
        return deletedAccount;
    }
}
