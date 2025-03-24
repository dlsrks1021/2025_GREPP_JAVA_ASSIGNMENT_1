package controller;

import exception.AccountException;
import framework.Session;
import service.AccountService;
import lombok.RequiredArgsConstructor;
import model.Account;
import framework.annotation.Controller;
import framework.annotation.GetMapping;
import framework.annotation.RequestParam;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final Scanner scanner;

    @GetMapping("/accounts/signup")
    public void signup() {
        System.out.println("회원 가입을 시작합니다.");
        System.out.print("계정을 입력해주세요 > ");
        String name = scanner.nextLine();
        System.out.print("이메일을 입력해주세요 > ");
        String email = scanner.nextLine();
        System.out.print("비밀번호를 설정해주세요 > ");
        String password = scanner.nextLine();

        Account savedAccount = accountService.signup(name, email, password);
        if (savedAccount != null) {
            System.out.println(savedAccount.getAccountName() + "님 회원 가입을 환영합니다!");
        }
    }

    @GetMapping("/accounts/signin")
    public void signin(Session session) {
        if (session.getAttribute("userId") != null) {
            throw new AccountException("이미 로그인 중입니다.");
        }

        System.out.print("계정 > ");
        String name = scanner.nextLine();
        System.out.print("비밀번호 > ");
        String password = scanner.nextLine();

        Account loginAccount = accountService.login(name, password);
        session.setAttribute("userId", loginAccount.getAccountId());
        session.setAttribute("userName", loginAccount.getAccountName());
        System.out.println(loginAccount.getAccountName() + "님 환영합니다.");
    }

    @GetMapping("/accounts/signout")
    public void signout(Session session) {
        if (session.getAttribute("userId") == null) {
            throw new AccountException("로그인 되어 있지 않습니다.");
        }

        session.invalidate();
        System.out.println("로그아웃 되었습니다.");
    }

    @GetMapping("/accounts/detail")
    public void detail(@RequestParam("accountId") long accountId) {
        System.out.println(accountService.getAccount(accountId).toString());
    }

    @GetMapping("/accounts/edit")
    public void edit(@RequestParam("accountId") long accountId) {
        Account account = accountService.getAccount(accountId);
        System.out.println(account.getAccountName() + " 계정을 수정합니다.");

        System.out.print("비밀번호 > ");
        String password = scanner.nextLine();
        System.out.print("이메일 > ");
        String email = scanner.nextLine();

        Account updatedAccount = accountService.updateAccount(accountId, password, email);
        System.out.println(updatedAccount.getAccountName() + " 계정이 수정되었습니다.");
    }

    @GetMapping("/accounts/remove")
    public void remove(@RequestParam("accountId") long accountId, Session session) {
        Account deletedAccount = accountService.deleteAccount(accountId);
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null && deletedAccount.getAccountId() == userId) {
            session.invalidate();
            System.out.println("로그아웃 되었습니다.");
        }
        System.out.println(deletedAccount.getAccountName() + " 계정이 삭제되었습니다.");
    }

}
