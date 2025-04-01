package com.cbox.kioskservice.memberTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.admin.domain.MemberRole;
import com.cbox.kioskservice.api.admin.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepoTest {
    

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember(){

        for(int i=0; i<10; i++){

            Member member = Member.builder()
                .id("user"+i+"@aaa.com")
                .pw(passwordEncoder.encode("1111"))
                .build();
            member.addRole(MemberRole.ADMIN);

            if(i >= 5){
                member.addRole(MemberRole.MANAGER);
            }

            if(i == 8){
                member.addRole(MemberRole.USER);
            }
            memberRepository.save(member);
        }
        
    }

    @Test
    public void testRead(){
        String id = "user1@aaa.com";

        Member member = memberRepository.getWithRoles(id);

        log.info("___________________");
        log.info(member);
    }


}
