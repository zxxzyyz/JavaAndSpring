package jpa.querydsl.repository;

import jpa.querydsl.entity.MemberSearchCond;
import jpa.querydsl.entity.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCond cond);
    Page<MemberTeamDto> searchSimple(MemberSearchCond cond, Pageable pageable);
    Page<MemberTeamDto> searchComplex(MemberSearchCond cond, Pageable pageable);
}
