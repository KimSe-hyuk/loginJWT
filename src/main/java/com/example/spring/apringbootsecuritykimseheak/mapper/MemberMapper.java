package com.example.spring.apringbootsecuritykimseheak.mapper;

import com.example.spring.apringbootsecuritykimseheak.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member findByUserId(String userid);

    void signUp(Member member);
}
