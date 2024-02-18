package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUid(String uid);

    @Query("SELECT m FROM Member m WHERE m.id != :excludedId")
    List<Member> findAllExcludingMemberById(@Param("excludedId") Long excludedId);
}
