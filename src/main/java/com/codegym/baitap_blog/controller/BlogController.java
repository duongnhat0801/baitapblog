package com.codegym.baitap_blog.controller;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Value("${upload.path}")
    private String fileUpload;
    @Autowired
    private IBlogService blogService;

    @GetMapping("")
    public String home(Model model) {
        Iterable<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        return "home";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        Iterable<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        return "list";
    }

    @GetMapping("/create")
    public String showFormCreate(Model model) {
        model.addAttribute("blogFromCreateDto", new BlogFormCreateDto());
        return "create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute BlogFormCreateDto blogFromCreateDto,
                       RedirectAttributes redirect) {
        MultipartFile multipartFile = blogFromCreateDto.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        try {
            System.out.println( fileUpload + fileName);
            FileCopyUtils.copy(blogFromCreateDto.getAvatar().getBytes(), new File(fileUpload + fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Blog blog = new Blog(blogFromCreateDto.getId(), blogFromCreateDto.getTitle(), blogFromCreateDto.getContent(), blogFromCreateDto.getAuthor(), fileName);
        redirect.addFlashAttribute("noti", "Thêm mới thành công!");
        blogService.save(blog);
        return "redirect:/blog";
    }

    @GetMapping("{id}/content")
    public String showContent(Model model,
                              @PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if(blog.equals(null)) {
            return "redirect:/blog";
        }
        model.addAttribute("blog",blog);
        return "content";
    }

    @GetMapping("{id}/delete")
    public String showFormDelete(Model model,
                                 @PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if(blog.equals(null)) {
            return "redirect:/blog/list";
        }
        model.addAttribute("blog",blog);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Blog blog,
                         RedirectAttributes redirect) {
        blogService.delete(blog.getId());
        redirect.addFlashAttribute("noti", "Xóa thành công!");
        return "redirect:/blog/list";
    }

    @GetMapping("{id}/update")
    public String showFormUpdate(Model model,
                                 @PathVariable Long id) {
        Blog blog = blogService.findById(id);
        if(blog.equals(null)) {
            return "redirect:/blog/list";
        }
        model.addAttribute("blog",blog);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BlogFormUpdateDto blogFormUpdateDto,
                         RedirectAttributes redirect) {
        MultipartFile multipartFile = blogFormUpdateDto.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        try {
            System.out.println( fileUpload + fileName);
            FileCopyUtils.copy(blogFormUpdateDto.getAvatar().getBytes(), new File(fileUpload + fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Blog blog = new Blog(blogFormUpdateDto.getId(), blogFormUpdateDto.getTitle(), blogFormUpdateDto.getContent(), blogFormUpdateDto.getAuthor(), fileName);
        redirect.addFlashAttribute("noti", "Cập nhật thành công!");
        blogService.save(blog);
        return "redirect:/blog/list";
    }
}
