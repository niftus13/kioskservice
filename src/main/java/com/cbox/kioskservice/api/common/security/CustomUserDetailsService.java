package com.cbox.kioskservice.api.common.security;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.admin.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// CustomuserDetailsService


@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        
        log.info("......loadUserByUsername......");
        log.info(username);
        log.info("......loadUserByUsername......");
        Member member = memberRepository.getWithRoles(username);



        if (member == null) {
            throw new UsernameNotFoundException("Not Found");
            
        }

        MemberDTO memberDTO = new MemberDTO(
            member.getId(),
            member.getPw(),
            member.isSosial(),
            member.isMdelFlag(),
            member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
        log.info(memberDTO);
        
        return memberDTO;
    }
    
}
