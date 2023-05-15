package com.hellzzangAdmin.valid.auth;

import com.hellzzangAdmin.controller.AdminMgController;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * packageName    : com.hellzzangAdmin.valid.auth
 * fileName       : PasswordMatchesValidator
 * author         : 김재성
 * date           : 2023-05-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-05-12        김재성       최초 생성
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    /**
    * @methodName : isValid
    * @date : 2023-05-12 오후 6:16
    * @author : 김재성
    * @Description: 패스워드, 패스워드 확인
    **/
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AdminMgController.SaveAdminUser saveAdminUser = (AdminMgController.SaveAdminUser) obj;
        return saveAdminUser.getPassword().equals(saveAdminUser.getConfirmPassword());
    }
}
