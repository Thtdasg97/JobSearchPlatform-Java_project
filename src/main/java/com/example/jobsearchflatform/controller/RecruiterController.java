package com.example.jobsearchflatform.controller;
import com.example.jobsearchflatform.entity.*;
import com.example.jobsearchflatform.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecruiterController {
    private RecruitmentService recruitmentService;
    private UserService userService;
    private CompanyService companyService;
    private CategoryService categoryService;
    private ApplyPostService applyPostService;
    private JavaMailSender mailSender;

    @Autowired
    public RecruiterController(RecruitmentService recruitmentService,
                               UserService userService,
                               CompanyService companyService, CategoryService categoryService, ApplyPostService applyPostService, JavaMailSender mailSender) {
        this.recruitmentService = recruitmentService;
        this.userService = userService;
        this.companyService = companyService;
        this.categoryService = categoryService;
        this.applyPostService = applyPostService;
        this.mailSender = mailSender;
    }

    @GetMapping("/showRecruiterLoginPage")
    public String showRecruiterLoginPage() {
        return "public/login-recruiter";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Clear the user's session or authentication information
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to the login page
        return "redirect:/showMyLoginPage?logout";
    }
    @GetMapping("/user/profile")
    public String showUserProfile(@RequestParam("userId") int userId,
                                  Model theModel) {
        // find user by id
        User theUser = userService.findUserById(userId);
        // add user to the Model
        theModel.addAttribute("user", theUser);

        // find company by id
        Company theCompany = companyService.findCompanyByUserId(userId);

        // add company to the Model
        theModel.addAttribute("company",theCompany);

        // return a html file
        return "public/profile";
    }

    @PostMapping("/user/update-profile")
    public String updateProfile(@ModelAttribute("user") User theUser,
                                Model theModel,
                                RedirectAttributes redirectAttributes) {

        User updatedUser = userService.updateUser(theUser);

        theModel.addAttribute("user", updatedUser);

        // Add a success message to the model
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

        return "redirect:/user/profile?userId=" + theUser.getId();
    }

    @PostMapping("/user/upload")
    @ResponseBody
    public String uploadUserImage(MultipartFile file,
                            @RequestParam("userId") int userId) throws IOException {
        // show message if error
        if(file.isEmpty()) {
            return "Error: File is empty";
        }

        // save file user upload
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imagePath = "/assets/images/" + fileName;
        userService.saveFile(file, imagePath);

        // find the user and set the new image
        User theUser = userService.findUserById(userId);
        if(theUser != null) {
            theUser.setImage(fileName);
            userService.saveUser(theUser);
            return imagePath;
        }
        return "redirect:/user/profile/" + userId;
    }

    @PostMapping("/user/update-company")
    public String updateCompany(@ModelAttribute("company") Company theCompany,
                                Model theModel,
                                @RequestParam("companyId") int companyId,
                                @ModelAttribute("user") User theUser,
                                RedirectAttributes redirectAttributes){

        Company updatedCompany = companyService.updateCompany(theCompany);

        theModel.addAttribute("company", updatedCompany);
        // Add a success message to the model
        redirectAttributes.addFlashAttribute("successMessage2",
                "Company Information updated successfully!");

        return "redirect:/user/profile?userId=" + theUser.getId();
    }
    @PostMapping("/user/upload-company")
    @ResponseBody
    public String uploadCompanyImage(MultipartFile file,
                                     @RequestParam("companyId") int companyId) throws IOException {

        // show message if error
        if(file.isEmpty()) {
            return "Error: File is empty";
        }

        // save file user upload
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imagePath = "/assets/images/" + fileName;
        companyService.saveFile(file, imagePath);

        // find the user and set the new image
        Company theCompany = companyService.findCompanyByUserId(companyId);
        if(theCompany != null) {
            theCompany.setLogo(fileName);
            companyService.saveCompany(theCompany);
            return imagePath;
        }
        return "redirect:/user/profile/";
    }

    @GetMapping("/user/profile/showWarningUpload")
    public String showWarningUpload() {
        return "public/upload-warning";
    }


    @GetMapping("/user/list-post")
    public String showRecruiterListPost(Model theModel,
                                        @RequestParam("userId") int userId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {

        // find company of user
        Company userCompany = companyService.findCompanyByUserId(userId);

        int companyId = userCompany.getId();

        int totalPages = recruitmentService.getTotalPages(size);

        if(page < 0 || page >= totalPages) {
            page = 0;
        }

        Page<Recruitment> recruitments = recruitmentService.getAllRecruitments(page,size, companyId);
        theModel.addAttribute("recruitments", recruitments);
        theModel.addAttribute("currentPage", page);
        theModel.addAttribute("totalPages", totalPages);

        // return list recruitment of company
        return "public/post-list";
    }
    @GetMapping("/recruitment/post")
    public String showRecruitmentForm(Model theModel) {

        Recruitment newRecruitment = new Recruitment();
        theModel.addAttribute("newRecruitment", newRecruitment);

        // Get the list of categories from the service
        List<Category> categories = categoryService.findAllCategories();
        theModel.addAttribute("categories", categories);

        return "public/post-job";
    }

    @PostMapping("/recruitment/add")
    public String addNewRecruitment(@ModelAttribute("newRecruitment") Recruitment newRecruitment,
                                    @RequestParam("userId") int userId,
                                    @RequestParam("category_id") int categoryId) {
        LocalDate created = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = created.format(formatter);
        newRecruitment.setCreatedAt(formattedDate);

        Company userCompany = companyService.findCompanyByUserId(userId);
        newRecruitment.setCompany(userCompany);

        Category userCategory = categoryService.findCategoryById(categoryId);
        newRecruitment.setCategory(userCategory);

        recruitmentService.saveRecruitment(newRecruitment);

        return "redirect:/user/list-post?userId=" + userId;

    }

    @GetMapping("/recruitment/edit-post/{recruitmentId}")
    public String showRecruitmentEditPost(Model theModel,
                                        @PathVariable("recruitmentId") int theId) {
        // find recruitment by id
        Recruitment theRecruitment = recruitmentService.findRecruitmentById(theId);

        // add to model attribute
        theModel.addAttribute("recruitment", theRecruitment);

        List<Category> categories = categoryService.findAllCategories();
        theModel.addAttribute("categories", categories);
        // return the detail form
        return "public/edit-job";
    }

    // method edit recruitment
    @PostMapping("/recruitment/edit")
    public String updatedRecruitment(Model theModel,
                                     @ModelAttribute("updatedRecruitment") Recruitment updateRecruitment,
                                     @RequestParam("category_id") int categoryId,
                                     @RequestParam("userId") int userId) {

        Category theCategory = categoryService.findCategoryById(categoryId);
        updateRecruitment.setCategory(theCategory);

        Recruitment updatedRecruitment = recruitmentService.updatedRecruitment(updateRecruitment);
        theModel.addAttribute("updatedRecruitment", updatedRecruitment);

        return "redirect:/user/list-post?userId=" + userId;
    }

    // method delete recruitment
    @PostMapping("/recruitment/delete")
    public String deleteRecruitment(Model theModel,
                                    @RequestParam("userId") int userId,
                                    @RequestParam("recruitmentId") int recruitmentId) {
        recruitmentService.deleteRecruitmentById(recruitmentId);
        return "redirect:/user/list-post?userId=" + userId;

    }

    // Apply Post Service
    @GetMapping("/user/get-list-user")
    public String showListApplyPost(Model theModel,
                                    @RequestParam("userId") int userId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {

        Company theCompany = companyService.findCompanyByUserId(userId);
        List<Recruitment> recruitments = theCompany.getRecruitments();
        List<ApplyPost> allApplyPosts = new ArrayList<>();

        for(Recruitment recruitment : recruitments) {
            List<ApplyPost> applyPosts = recruitment.getApplyPosts();
            allApplyPosts.addAll(applyPosts);
        }
        int totalApplyPosts = allApplyPosts.size();
        int totalPages = (int) Math.ceil((double) totalApplyPosts / size);

        if (page < 0 || page >= totalPages) {
            page = 0;
        }
        // Calculate the starting and ending index of apply posts for the current page
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalApplyPosts);

        List<ApplyPost> applyPostsForPage = allApplyPosts.subList(startIndex, endIndex);

        theModel.addAttribute("applyPosts", applyPostsForPage);
        theModel.addAttribute("currentPage", page);
        theModel.addAttribute("totalPages", totalPages);

        return "public/list-user";
    }

    // method get recruitment detail
    @GetMapping("/recruitment/detail/{recruitmentId}")
    public String showRecruitmentDetail(Model theModel,
                                        @PathVariable("recruitmentId") int recruitmentId) {
//        @RequestParam("userId") int userId
        // find recruitment by id
        Recruitment theRecruitment = recruitmentService.findRecruitmentById(recruitmentId);

        // add to model attribute
        theModel.addAttribute("recruitment", theRecruitment);

        List<ApplyPost> applyPosts = theRecruitment.getApplyPosts();
        theModel.addAttribute("applyPosts", applyPosts);

        Company theCompany = theRecruitment.getCompany();
//        List<Recruitment> listRelated = theCompany.getRecruitments();
        Category category = theRecruitment.getCategory();
        int categoryId = category.getId();
        List<Recruitment> listRelated = recruitmentService.findRecruitmentByCategoryId(categoryId);
        theModel.addAttribute("listRelated", listRelated);
        theModel.addAttribute("company", theCompany);

        return "public/detail-post";
    }

    // method confirm account when send email
    @PostMapping("/user/confirm-account")
    public String confirmAccount(@ModelAttribute("user") User theUser) {
        String email = theUser.getEmail();
        User user = userService.findUserByEmail(email);
        String fullName = user.getFullName();

        String mailSubject = "Please verify your email address";
        String mailContent = "Hello, " + fullName + " this is the passcode to confirm your email " + email + " address\n"
                + "The Passcode is: 19865\n"
                + "Once your account is verified, you can use all function of Recruiter/ HR.\n"
                            + "Have a good day!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("huythanh6897@gmail.com");
        message.setTo(email);
        message.setSubject(mailSubject);
        message.setText(mailContent);

        mailSender.send(message);

        return "public/message";
    }

    @GetMapping("/showConfirmPasscodePage")
    public String showPasscodeForm() {
        return "public/message"; // Return the name of the Thymeleaf template for the form page
    }

    // method confirm passcode
    @PostMapping("/user/confirm-passcode")
    public String confirmPasscode(@RequestParam("passcode") String passcode,
                                  @RequestParam("email") String email,
                                  RedirectAttributes redirectAttributes) {

        User user = userService.findUserByEmail(email);

        String correctPasscode = "19865";
        if(passcode.equals(correctPasscode)) {
            redirectAttributes.addFlashAttribute("correctPasscode", "Your email is verified, Thanks you!");
            user.setStatus(1);
            userService.saveUser(user);
        } else {
            redirectAttributes.addFlashAttribute("incorrectPasscode", "Your passcode is wrong, please check again");
        }
        return "redirect:/showConfirmPasscodePage";

    }

    // method save company
    @PostMapping("/save-company/save")
    public ResponseEntity<String> toggleCompanySaveStatus(@RequestParam("idCo") int coId,
                                                          @RequestParam("userId") int userId) {
        Company company = companyService.findCompanyById(coId);
        User user = userService.findUserById(userId);

        if (company == null || user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid company or user");
        }

        if (checkedUser(user, coId)) {
            // Company already saved, so remove it
            company.removeUser(user);
            companyService.saveCompany(company);
            return ResponseEntity.ok("unsaved"); // Return 'unsaved' status to the client
        } else {
            // Company not saved, so save it
            company.addUser(user);
            companyService.saveCompany(company);
            return ResponseEntity.ok("saved"); // Return 'saved' status to the client
        }
    }


    // method checking user already have in the list or not
    public boolean checkedUser(User theUser, int recId) {
        Company company = companyService.findCompanyById(recId);
        List<User> users = company.getUserList();

        for(User checkUser : users) {
            if (checkUser.getEmail().equals(theUser.getEmail())) {
                return true;
            }
        } return false;
    }


}
