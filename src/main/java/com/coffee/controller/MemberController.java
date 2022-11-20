package com.coffee.controller;

import com.coffee.dto.MemberFormDto;
import com.coffee.dto.MemberListDto;
import com.coffee.entity.Member;
import com.coffee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    /** 회원 목록
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/listmember")
    public String listmember(Model model) {


        List<MemberListDto> memberlist=  memberService.findAllDESC();



        model.addAttribute("member", memberlist);

        return "/member/memberList";
    }



}