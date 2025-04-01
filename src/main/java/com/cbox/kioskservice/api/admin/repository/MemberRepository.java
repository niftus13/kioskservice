package com.cbox.kioskservice.api.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbox.kioskservice.api.admin.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    
    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.id = :id")
    Member getWithRoles(@Param("id") String id);

    @Query("select m.id from Member m where m.id = :id")
    Page<Object[]> listid(@Param("id") String id, Pageable pageable);

    
    @Query("select m from Member m where m.mdelFlag = false and m.id = :id")
    Member selectOne(@Param("id") String id);
    



}
