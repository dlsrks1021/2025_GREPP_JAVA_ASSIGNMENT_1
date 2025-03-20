package account;

import lombok.RequiredArgsConstructor;
import cli.CLI;
import spring.annotation.Controller;
import spring.annotation.GetMapping;
import spring.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts/signup")
    public void signup() {
        String name = CLI.getUserInput("회원 가입을 시작합니다.\n계정을 입력해주세요 > ");
        String email = CLI.getUserInput("이메일을 입력해주세요 > ");
        String password = CLI.getUserInput("비밀번호를 설정해주세요 > ");
        Account account = new Account(name, email, password);
        Account savedAccount = accountService.signup(account);
        if (savedAccount != null) {
            CLI.printPrompt(savedAccount.getAccountName() + "님 회원 가입을 환영합니다!");
        }
    }

    @GetMapping("/accounts/signin")
    public void signin() {
        if (CLI.getSession().isUserLoggedIn()) {
            throw new AccountException("이미 로그인 중입니다.");
        }

        String name = CLI.getUserInput("계정 > ");
        String password = CLI.getUserInput("비밀번호 > ");
        Account loginAccount = accountService.login(name, password);
        CLI.getSession().setAccount(loginAccount);
        CLI.printPrompt(loginAccount.getAccountName() + "님 환영합니다.");
    }

    @GetMapping("/accounts/signout")
    public void signout() {
        if (!CLI.getSession().isUserLoggedIn()) {
            throw new AccountException("로그인 되어 있지 않습니다.");
        }

        CLI.getSession().signout();
    }

    @GetMapping("/accounts/detail")
    public void detail(@RequestParam("accountId") long accountId) {
        CLI.printPrompt(accountService.readAccount(accountId));
    }

    @GetMapping("/accounts/edit")
    public void edit(@RequestParam("accountId") long accountId) {
        Account account = accountService.getAccount(accountId);
        CLI.printPrompt(account.getAccountName() + " 계정을 수정합니다.");

        String password = CLI.getUserInput("비밀번호 > ");
        String email = CLI.getUserInput("이메일 > ");

        Account updatedAccount = accountService.updateAccount(accountId, password, email);
        System.out.println(updatedAccount.getAccountName() + " 계정이 수정되었습니다.");
    }

    @GetMapping("/accounts/remove")
    public void remove(@RequestParam("accountId") long accountId) {
        Account deletedAccount = accountService.deleteAccount(accountId);
        if (CLI.getSession().isUserLoggedIn() &&
                deletedAccount.getAccountId() == CLI.getSession().getAccount().getAccountId()) {
            CLI.getSession().signout();
            CLI.printPrompt("로그아웃 되었습니다.");
        }
        CLI.printPrompt(deletedAccount.getAccountName() + " 계정이 삭제되었습니다.");
    }

}
