package com.cbox.kioskservice.api.admin.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.admin.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/member/")
@Log4j2
@CrossOrigin
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("register")
    public String showRegisterForm() {
        return null; 
    }


        // 회원 가입 처리
    @PostMapping("register")
    public void registerMember(MemberDTO memberDTO) {
        memberService.register(memberDTO); 

    }

    @GetMapping("{id}")
    public MemberDTO get(@PathVariable("id") String id) {

        return memberService.getOne(id);
    }

    @PostMapping("modifiy")
    public Map<String, String> modify(MemberDTO memberDTO){

        memberService.modify(memberDTO);

        return Map.of("result", memberDTO.getId());
    }

    @DeleteMapping("{id}")
    public Map<String, String> delete(@PathVariable("id") String id){

        return Map.of("result", id);
    }
    

    
    
}
