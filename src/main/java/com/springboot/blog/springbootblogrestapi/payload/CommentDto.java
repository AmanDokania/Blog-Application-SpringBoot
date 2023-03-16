package com.springboot.blog.springbootblogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class CommentDto {
    private Long id;
    // name should not be null or empty
//    @NotEmpty(message = "name can't be blank")
//    private String name;
//    @NotEmpty
//    @Email
//    private String email;

    private String createdBy ;

    private Date createDate ;

    private String lastModifiedBy ;

    private String lastModifiedDate ;
    @NotEmpty
    @Size(min = 10,message = "body should contain at least 10 character")
    private String body;

}
