package com.apple.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // db에서 username을 가진 유저 정보를 찾아와서
        // return new User(유저아이디, 비번, 권한);

        var result = memberRepository.findByUsername(username);
        if (result.isEmpty())
            throw new UsernameNotFoundException("그런아이디없음");

        Member user = result.get();
        List<GrantedAuthority> 권한 = new ArrayList<>();
        권한.add(new SimpleGrantedAuthority("일반 유저"));
        CustomUser a = new CustomUser(user.getUsername(), user.getPassword(), 권한);
        a.displayName = user.getDisplayName();
        return a;
    }
}

