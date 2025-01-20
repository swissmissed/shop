package com.apple.shop.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    String register(Authentication auth) {
        if (auth.isAuthenticated())
            return "redirect:/list";
        return "register.html";
    }

    @PostMapping("/member")
    String addMember(@RequestParam Map<String, Object> member) {

        Member newMember = new Member();
        newMember.setUsername(member.get("username").toString());
        newMember.setPassword(passwordEncoder.encode(member.get("password").toString()));
        newMember.setDisplayName(member.get("displayName").toString());
        memberRepository.save(newMember);
        System.out.println(newMember.toString());

        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {

        if (auth.isAuthenticated())
            return "mypage.html";

        return "mypage.html";
    }

}
