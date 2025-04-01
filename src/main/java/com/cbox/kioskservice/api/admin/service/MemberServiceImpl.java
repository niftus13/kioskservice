package com.cbox.kioskservice.api.admin.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.admin.repository.MemberRepository;
import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;
    

    @Override
    public MemberDTO getOne(String id) {
        
        Optional<Member> result = memberRepository.findById(id);

        Member member = result.orElseThrow(() -> new NoSuchElementException("Member not found with id: " + id));

        MemberDTO dto = modelMapper.map(member,MemberDTO.class);

        return dto;
    }

    @Override
    public void remove(String id) {
       
        Member member = memberRepository.selectOne(id);

        member.changemdelFlag(false);

        memberRepository.save(member);
    }

    @Override
    public void modify(MemberDTO memberDTO) {

        Optional<Member> result = memberRepository.findById(memberDTO.getId());

        Member member = result.orElseThrow();

        member.changePw(memberDTO.getPw());

        memberRepository.save(member);
       
    }

    @Override
    public void register(MemberDTO memberDTO) {

        Member newMember = modelMapper.map(memberDTO, Member.class);

        memberRepository.save(newMember);
        
    }
    
}
