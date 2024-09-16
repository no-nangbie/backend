package com.nonangbie.member.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof MemberDto.Post) {
            MemberDto.Post dto = (MemberDto.Post) obj;
            return dto.getPassword() != null && dto.getPassword().equals(dto.getConfirmPassword());
        } else if (obj instanceof MemberDto.Patch) {
            MemberDto.Patch dto = (MemberDto.Patch) obj;
            return dto.getPassword() != null && dto.getPassword().equals(dto.getConfirmPassword());
        }
        return false;
    }
}
