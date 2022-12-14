package com.youngjun.bank.web.controller;

import com.youngjun.bank.domain.entity.User;
import com.youngjun.bank.domain.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        return "/auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();

        try {
            User user = authService.createUser(name);
            session.setAttribute("LOGIN", user.getUserName());
        }
        catch (Exception e) {
            // 이미 사용자가 존재하는 경우
            Optional<User> userWithUserName = authService.getUserWithUserName(name);
            userWithUserName.ifPresent(user -> session.setAttribute("LOGIN", user.getUserName()));
        }
        return "redirect:/bank";
    }
}
