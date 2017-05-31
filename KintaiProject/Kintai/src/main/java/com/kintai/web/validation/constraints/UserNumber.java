package com.kintai.web.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.kintai.web.validation.UserNumberValidator;
 
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={UserNumberValidator.class})
public @interface UserNumber {
      String message() default "該当する社員番号がありません。";
 
      Class<?>[] groups() default {};
 
      Class<? extends Payload>[] payload() default {};
 
      @Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER})
      @Retention(RetentionPolicy.RUNTIME)
      @Documented
      public static @interface List
      {
        UserNumber[] value();
      }
}