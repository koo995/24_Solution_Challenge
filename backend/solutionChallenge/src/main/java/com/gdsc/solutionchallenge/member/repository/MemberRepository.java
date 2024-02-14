package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
