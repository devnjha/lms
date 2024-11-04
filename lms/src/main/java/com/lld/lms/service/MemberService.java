package com.lld.lms.service;

import com.lld.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lld.lms.model.Member;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member update(Long id, Member memberDetails) {
        Member member = findById(id);
        if (member != null) {
            member.setName(memberDetails.getName());
            member.setEmail(memberDetails.getEmail());
            member.setMembershipDate(memberDetails.getMembershipDate());
            return memberRepository.save(member);
        }
        return null;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
