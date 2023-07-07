package jpabook.jpashop.repository.member.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequestDto {

    @NotEmpty
    private String name;
}