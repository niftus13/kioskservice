package com.cbox.kioskservice.api.admin.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.Data;

@Data
public class MemberDTO extends User{

    private String id;

    private String pw;
    
    private boolean social;

    private boolean mdelFlag;

    private List<String> roleNames = new ArrayList<>();

    public MemberDTO (String id, String pw, boolean social, boolean mdelFlag, List<String> roleNames){
        
        super(
            id,
            pw,
            roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList())
        );
        
        this.id = id;
        this.pw = pw;
        this.social = social;
        this.mdelFlag = mdelFlag;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims(){
        
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("pw", pw);
        dataMap.put("social", social);
        dataMap.put("mdelFlag", mdelFlag);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }
    
}
