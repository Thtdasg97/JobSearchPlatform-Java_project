package com.example.springasm02.controller;
import com.example.springasm02.entity.*;
import com.example.springasm02.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class HomeController {
    private CompanyService companyService;
    private RecruitmentService recruitmentService;
    private CategoryService categoryService;
    private UserService userService;
    private ApplyPostService applyPostService;

    @Autowired
    public HomeController(CompanyService companyService, RecruitmentService recruitmentService,
                          CategoryService categoryService, UserService userService, ApplyPostService applyPostService) {
        this.companyService = companyService;
        this.recruitmentService = recruitmentService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.applyPostService = applyPostService;
    }

    @GetMapping("/showMyHomePage")
    public String showMyHomePage(Model theModel) {
        // find all company
        List<Company> companyList = companyService.findAllCompanies();

        // put company - recruitment of that company to hash map Key : value
        Map<Company, Integer> companyRecruitmentMap = new HashMap<>();
        for (Company company : companyList) {
            int totalRecruitment = 0;
            if (company.getRecruitments() != null) {
                for (Recruitment recruitment : company.getRecruitments()) {
                    totalRecruitment += recruitment.getQuantity();
                }
            }
            companyRecruitmentMap.put(company, totalRecruitment);
        }

        // create new array and put key to that array
        List<Company> top3Companies = new ArrayList<>(companyRecruitmentMap.keySet());

        // get the value of each key, compare and sort by descending order
        Collections.sort(top3Companies, (c1, c2) -> companyRecruitmentMap.get(c2) - companyRecruitmentMap.get(c1));

        // get top 3 of companies
        List<Company> top3CompaniesWithHighestRecruitment = top3Companies.subList(0, Math.min(3, top3Companies.size()));
        theModel.addAttribute("companies",top3CompaniesWithHighestRecruitment);

        // get top 3 recruitments have the highest salary
        List<Recruitment> recruitments = recruitmentService.getTop3HighestSalaryRecruitments();
        theModel.addAttribute("recruitments", recruitments);

        // add list of category
        List<Category> categories = categoryService.findTop4CategoriesByNumberChoose();

        theModel.addAttribute("categories", categories);

        // add list of candidate
        List<User> users = userService.findAllUsers();

        int numberCandidate = 0;
        for(User user : users) {
            if (user.getId() == 2) {
                numberCandidate++;
            }
        }
        theModel.addAttribute("numberCandidate", numberCandidate);

        int numberCompany = companyList.size();
        theModel.addAttribute("numberCompany",numberCompany);

        List<Recruitment> allRecruitment = recruitmentService.findAllRecruitment();
        int numberRecruitment = allRecruitment.size();
        theModel.addAttribute("numberRecruitment",numberRecruitment);

        return "public/home";
    }

    @GetMapping("/recruitment/search")
    public String searchRecruitment(@RequestParam("keySearch") String keySearch, Model theModel) {
        // perform the search logic for recruitment based on the keySearch parameter
        List<Recruitment> searchResults = recruitmentService.searchRecruitmentByKey(keySearch);

        // add the search results to the model
        theModel.addAttribute("searchResults", searchResults);

        return "public/result-search";
    }

    @GetMapping("/companyName/search")
    public String searchRecruitmentByCompanyName(@RequestParam("keySearch") String keySearch, Model theModel) {
        // perform the search logic for recruitment based on the keySearch parameter
        List<Recruitment> searchResults = recruitmentService.searchRecruitmentByCompanyName(keySearch);

        // add the search results to the model
        theModel.addAttribute("searchResults", searchResults);

        return "public/result-search-company-name";
    }

    @GetMapping("/recruitment/searchAddress")
    public String searchRecruitmentByAddress(@RequestParam("keySearch") String keySearch, Model theModel) {
        // perform the search logic for recruitment based on the keySearch parameter
        List<Recruitment> searchResults = recruitmentService.searchRecruitmentByAddress(keySearch);

        // add the search results to the model
        theModel.addAttribute("searchResults", searchResults);

        return "public/result-search-address";
    }

    @GetMapping("/user/profileCandidate")
    public String showCandidateProfile(@RequestParam("userId") int userId,
                                       Model theModel) {
        // find user by id
        User theUser = userService.findUserById(userId);
        // add user to the Model
        theModel.addAttribute("user", theUser);

        // find company by id
        Company theCompany = companyService.findCompanyByUserId(userId);

        // add company to the Model
        theModel.addAttribute("company", theCompany);

        // get CV
        Cv cv = theUser.getCv();
        if (cv != null) {
            theModel.addAttribute("Cv", cv);
        }

        // return a html file
        return "public/profile-user";
    }

    @PostMapping("/user/update-profile-candidate")
    public String updateProfileCandidate(@ModelAttribute("user") User theUser,
                                         Model theModel,
                                         RedirectAttributes redirectAttributes) {

        User updatedUser = userService.updateUser(theUser);

        theModel.addAttribute("user", updatedUser);

        // Add a success message to the model
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

        return "redirect:/user/profileCandidate?userId=" + updatedUser.getId();
    }

    @PostMapping("/candidate/upload")
    @ResponseBody
    public String uploadUserImage(MultipartFile file,
                                  @RequestParam("userId") int userId,
                                  RedirectAttributes redirectAttributes) throws IOException {
        // show message if error
        if (file.isEmpty()) {
            return "Error: File is empty";
        }

        // save file user upload
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imagePath = "/assets/images/" + fileName;
        userService.saveFile(file, imagePath);

        // find the user and set the new image
        User theUser = userService.findUserById(userId);
        if (theUser != null) {
            theUser.setImage(fileName);
            userService.saveUser(theUser);
            return imagePath;
        }

        return "redirect:/user/profileCandidate?userId=" + userId;
    }

    @PostMapping("/user/uploadCv")
    @ResponseBody
    public String uploadUserCv(MultipartFile file,
                               @RequestParam("userId") int userId) throws IOException {

        if (file.isEmpty()) {
            return "Error: File is empty";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        userService.saveCv(file, fileName);

        User theUser = userService.findUserById(userId);

        if (theUser != null) {
            Cv cv = new Cv();
            cv.setFileName(fileName);
            theUser.setCv(cv);
            userService.saveUser(theUser);
            return fileName;
        }
        return "redirect:/user/profileCandidate?userId=" + userId;
    }

    @PostMapping("/user/apply-job1")
    public ResponseEntity<String> applyJob(@RequestParam("idRe") int recruitmentId,
                           @RequestParam("userId") int userId,
                           @RequestParam(name = "nameCv", defaultValue = "") String nameCv,
                           @RequestParam("textvalue") String text) {
        // find recruitment by recruitmentId
        Recruitment appliedRecruitment = recruitmentService.findRecruitmentById(recruitmentId);

        int recId = appliedRecruitment.getId();

        // find User by user id
        User appliedUser = userService.findUserById(userId);

        if(checkedAppliedUser(appliedUser, recId)) {
            return ResponseEntity.ok("applied");
        } else {
            // create and set new apply post
            ApplyPost newApplyPost = new ApplyPost();
            newApplyPost.setUser(appliedUser);
            newApplyPost.setAddress(appliedUser.getAddress());
            newApplyPost.setEmail(appliedUser.getEmail());
            newApplyPost.setDescription(appliedUser.getDescription());
            newApplyPost.setStatus(0);
            newApplyPost.setDescription(text);
            newApplyPost.setRecruitment(appliedRecruitment);

            LocalDate created = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = created.format(formatter);
            newApplyPost.setCreatedAt(formattedDate);

            if("".equals(nameCv.trim())) {
                Cv currentCv = appliedUser.getCv();
                newApplyPost.setNameCv(currentCv.getFileName());
            } else {
                Cv newCv = new Cv(nameCv);
                newApplyPost.setNameCv(nameCv);
                appliedUser.setCv(newCv);
            }

            appliedUser.addApplyPost(newApplyPost);
            // save new apply post
            applyPostService.saveApplyPost(newApplyPost);
            return ResponseEntity.ok("not-apply");
        }
    }

    public boolean checkedAppliedUser(User theUser, int recId) {
        List<ApplyPost> applyPostList = theUser.getApplyPosts();

        for(ApplyPost applyPost : applyPostList) {
            if (applyPost.getRecruitment().getId() == recId) {
                return true;
            }
        }
        return false;
    }


    @PostMapping("/apply-job/uploadCv")
    @ResponseBody
    public String uploadCvInApplyJob(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return "Error: File is empty";
        }

        // Check if the file is a PDF
        if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
            return "Error: Only PDF files are allowed.";
        }

        return StringUtils.cleanPath(file.getOriginalFilename());
    }
    @PostMapping("/save-job/save")
    public ResponseEntity<String> saveRecruitment(@RequestParam("idRe") int recId,
                                          @RequestParam("userId") int userId,
                                          Model theModel) {
        User user = userService.findUserById(userId);
        Recruitment recruitment = recruitmentService.findRecruitmentById(recId);

        if (checkedRecruitment(user, recId)) {
            // Job already saved, so remove it
            recruitment.removeUser(user);
            recruitmentService.saveRecruitment(recruitment);
            return ResponseEntity.ok("saved"); // Return 'saved' status to the client
        } else {
            // Job not saved, so save it
            recruitment.addUser(user);
            recruitmentService.saveRecruitment(recruitment);
            return ResponseEntity.ok("unsaved"); // Return 'unsaved' status to the client
        }
    }


    // method checking user already have in the list or not
    public boolean checkedRecruitment(User theUser, int recId) {
        List<Recruitment> recruitments = theUser.getRecruitmentList();

        for(Recruitment checkedRecruitment : recruitments) {
            if (checkedRecruitment.getId() == recId) {
                return true;
            }
        }
        return false;
    }

    // get saved job list of user
    @GetMapping("/save-job/get-list")
    public String showSavedJobList(@RequestParam("userId") int userId,
                                   Model theModel) {
        User theUser = userService.findUserById(userId);
        List<Recruitment> savedRecruitmentList = theUser.getRecruitmentList();
        theModel.addAttribute("savedRecruitmentList", savedRecruitmentList);


        return "public/list-save-job";
    }

    // show following company
    @GetMapping("/user/get-list-company")
    public String showFollowingCompany(@RequestParam("userId") int userId,
                                       Model theModel) {
        User theUser = userService.findUserById(userId);
        List<Company> savedCompanyList = theUser.getCompanyList();
        theModel.addAttribute("savedCompanyList", savedCompanyList);


        return "public/list-follow-company";
    }

    // show applied recruitment of user
    @GetMapping("/user/company-post/{companyId}")
    public String listOfRecruitmentOfCompany(@PathVariable("companyId") int companyId,
                                             Model theModel) {
        Company theCompany = companyService.findCompanyById(companyId);
        List<Recruitment> recruitmentList = theCompany.getRecruitments();
        theModel.addAttribute("recruitmentList", recruitmentList);
        theModel.addAttribute("company", theCompany);

        return "public/post-company";
    }

    // show detail company
    @GetMapping("/user/detail-company/{companyId}")
    public String savedCompanyDetailInformation(@PathVariable("companyId") int companyId,
                                                Model theModel) {

        Company theCompany = companyService.findCompanyById(companyId);
        theModel.addAttribute("company", theCompany);

        return "public/detail-company";
    }

    // show list of recruitment of user
    @GetMapping("/user/get-list-apply")
    public String showListAppliedRecruitmentOfUser(Model theModel, @RequestParam("userId") int userId) {
        User theUser = userService.findUserById(userId);
        List<ApplyPost> applyPostList = theUser.getApplyPosts();

        List<Recruitment> appliedRecruitmentList = new ArrayList<>();

        for(ApplyPost applyPost : applyPostList) {
            Recruitment appliedRecruitment = applyPost.getRecruitment();

            appliedRecruitmentList.add(appliedRecruitment);
        }

        theModel.addAttribute("recruitmentList", appliedRecruitmentList);

        return "public/list-apply-job";
    }

    @GetMapping("/recruitment/category/{categoryId}")
    public String showRecruitmentsByCategory(@PathVariable("categoryId") int categoryId, Model model) {
        List<Recruitment> recruitmentList = recruitmentService.findAllRecruitment();
        List<Recruitment> recruitments = new ArrayList<>();
        Category category = categoryService.findCategoryById(categoryId);

        for(Recruitment recruitment : recruitmentList) {
            if(recruitment.getCategory().getId() == categoryId) {
                recruitments.add(recruitment);
            }
        }
         model.addAttribute("recruitments", recruitments);
        model.addAttribute("category",category);

        return "public/list-category-job";
    }

}
