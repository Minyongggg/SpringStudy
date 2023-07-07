package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import jpabook.jpashop.api.dto.ResultDto;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.member.dto.CreateMemberRequestDto;
import jpabook.jpashop.repository.member.dto.CreateMemberResponseDto;
import jpabook.jpashop.repository.member.dto.MemberDto;
import jpabook.jpashop.repository.member.dto.UpdateMemberRequestDto;
import jpabook.jpashop.repository.member.dto.UpdateMemberResponseDto;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller @ResponseBody
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponseDto saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponseDto(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponseDto saveMemberV2(@RequestBody @Valid CreateMemberRequestDto dto) {
        Member member = new Member();
        member.setName(dto.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponseDto(id);
    }

    @PutMapping("/api/v2/members/{memberId}")
    public UpdateMemberResponseDto updateMemberV2(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateMemberRequestDto dto) {

        memberService.update(memberId, dto.getName());
        Member findMember = memberService.findOne(memberId);
        return new UpdateMemberResponseDto(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public ResultDto<List<MemberDto>> memberV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());

        return new ResultDto<>(collect.size(), collect);

    }












}
