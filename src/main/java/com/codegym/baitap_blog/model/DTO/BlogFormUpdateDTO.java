package com.codegym.baitap_blog.model.DTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class BlogFormUpdateDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private MultipartFile avatar;
}
