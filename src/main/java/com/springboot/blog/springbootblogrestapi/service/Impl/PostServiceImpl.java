package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.exception.UnauthorizedException;
import com.springboot.blog.springbootblogrestapi.mapper.Mapper;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepositroy;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PostServiceImpl implements PostService {


    private PostRepositroy postRepositroy;
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category=categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(()->new ResourceNotFoundException("category","id",postDto.getCategoryId()));

        Post post= Mapper.MAPPER.mapToPost(postDto);
        post.setCategory(category);
        Post newPost=postRepositroy.save(post);
        System.out.println(newPost);
        return Mapper.MAPPER.mapToPostDto(newPost);
    }

    @Override
    public PostResponse getAllPOst(int pageNo, int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);

        Page<Post> postPage= postRepositroy.findAll(pageable);
        List<Post> postList = postPage.getContent();

        List<PostDto> postDtoList= postList.stream().map(post -> Mapper.MAPPER.mapToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepositroy.findById(id).orElseThrow(()->  new ResourceNotFoundException("post","id",id));
        PostDto postDto= Mapper.MAPPER.mapToPostDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post= postRepositroy.findById(id).orElseThrow(()->  new ResourceNotFoundException("post","id",id));

        Category category=categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(()->new ResourceNotFoundException("category","id",postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setCategory(category);

        Post updatedPost= postRepositroy.save(post);
        return Mapper.MAPPER.mapToPostDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post= postRepositroy.findById(id).orElseThrow(()->  new ResourceNotFoundException("post","id",id));
        postRepositroy.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategoryId(Long categoryId) {
        List<Post> postList=postRepositroy.findByCategoryId(categoryId);

        return postList.stream().map((post -> Mapper.MAPPER.mapToPostDto(post))).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostOfMyblog() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        List<Post> postList= postRepositroy.findByCreatedBy(username);
        System.out.println(postList);
        return postList.stream().map((post -> Mapper.MAPPER.mapToPostDto(post))).collect(Collectors.toList());
    }

    @Override
    public void deletePostByIdOfSelf(Long id) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        Post post=postRepositroy.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        if(!username.equals(post.getCreatedBy()))
                throw  new UnauthorizedException(HttpStatus.UNAUTHORIZED,"Invalid Access");

        postRepositroy.delete(post);
    }
}
