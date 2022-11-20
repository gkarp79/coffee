package com.coffee.repository;


import com.coffee.dto.ItemSearchDto;
import com.coffee.dto.MainItemDto;
import com.coffee.dto.MemberListDto;
import com.coffee.entity.Item;
import com.coffee.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>,
        QuerydslPredicateExecutor<Member>{

    Member findByEmail(String email);

    @Query("SELECT m FROM Member m ORDER BY m.id DESC ")
    List<Member> findAllDESC();


}