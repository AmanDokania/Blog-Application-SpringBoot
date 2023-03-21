package com.springboot.blog.springbootblogrestapi.payload;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
public class PostDto {

    private Long id;

    // not null amd not empty
    // should have at least 2 character
    @NotEmpty
    @Size(min = 2,message = "title should have at least 2 character")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "description should have at least 10 character")
    private String description;

    private String image;
    private String profileImage;

    private String createdBy ;

    private String createDate ;

    private String lastModifiedBy ;

    private String lastModifiedDate ;


    private Set<CommentDto> comments;

    private Long categoryId;

}
