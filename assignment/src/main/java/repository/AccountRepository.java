package repository;

import model.Account;
import framework.annotation.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccountRepository {

    private Long sequence = 0L;
    private Map<Long, Account> accountMap = new HashMap<>();

    public Account save(Account account) {
        account.setAccountId(++sequence);
        return accountMap.put(account.getAccountId(), account);
    }

    public Optional<Account> findByName(String name) {
        return accountMap.values().stream()
                .filter(a -> a.getAccountName().equals(name))
                .findFirst();
    }

    public Account findById(Long id) {
        return accountMap.get(id);
    }

    public Account update(long accountId, String password, String email) {
        Account account = accountMap.get(accountId);
        account.setAccountPassword(password);
        account.setAccountEmail(email);
        account.setAccountModifyTime(Timestamp.valueOf(LocalDateTime.now()));
        return account;
    }

    public Account delete(long accountId) {
        return accountMap.remove(accountId);
    }
}
