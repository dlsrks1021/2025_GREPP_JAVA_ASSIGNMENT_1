package framework;

import model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Session {
    private Account account;

    public boolean isUserLoggedIn() {
        return account != null;
    }

    public void signout() {
        account = null;
    }
}
