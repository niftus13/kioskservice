package com.cbox.kioskservice.api.admin.service;

import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {


        MemberDTO getOne(String id);

        void remove(String id);

        void modify(MemberDTO memberDTO);

        void register(MemberDTO memberDTO);

    
}
