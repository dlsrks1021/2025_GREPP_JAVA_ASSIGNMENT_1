package account;

import lombok.RequiredArgsConstructor;
import spring.annotation.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account signup(Account account) {
        return accountRepository.save(account);
    }

    public Account login(String name, String password) {
        return accountRepository.findByName(name)
                .filter(a -> a.getAccountPassword().equals(password))
                .orElseThrow(() -> new AccountException("로그인에 실패했습니다."));
    }

    public String readAccount(long accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new AccountException(accountId + "번 계정이 존재하지 않습니다.");
        }

        return account.toString();
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
