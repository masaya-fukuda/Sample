package com.kintai.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintai.business.domain.User;
import com.kintai.business.repository.UserRepository;
import com.kintai.business.service.UserService;
import com.kintai.web.form.UserForm;
import com.kintai.web.security.LoginUserDetails;
import com.kintai.web.validation.GroupOrder;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping("/login")
    public String loginForm(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        if (loginUserDetails != null) {
            return "redirect:/";
        }
        return "user/loginForm";
    }
    	
    @RequestMapping(value = "/user/registration")
    ModelAndView registration(@ModelAttribute("userForm") UserForm userForm, ModelAndView mav) {
        mav.addObject("userForm", userForm);        
        mav.setViewName("user/registration");
        return mav;
    }
    
    @RequestMapping(value = "/user/detail/{userId}", method = RequestMethod.GET)
    ModelAndView detail(@PathVariable String userId, @AuthenticationPrincipal UserDetails userDetails, ModelAndView mav) {
        User user = userRepository.findOne(userId);
        mav.addObject("user", user);
        mav.setViewName("user/detail");
        mav.addObject("login_user", userDetails);
        return mav;
    }    
    
    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.GET)
    ModelAndView edit(@PathVariable String userId, @ModelAttribute("userForm") UserForm updateForm, ModelAndView mav) {
        User user = userRepository.findOne(userId);
        mav.addObject("user", user);
        mav.addObject("userForm", updateForm);
        mav.setViewName("user/edit");
        return mav;
    }
    
    @RequestMapping(value = "/user/confirm", method = RequestMethod.GET)
    public String confirm(@ModelAttribute("model")ModelMap modelMap,
    					  Model model) {
    	UserForm userForm = (UserForm) modelMap.get("userForm");
        model.addAttribute("userForm", userForm);
        return "user/confirm";
    }
    
    @RequestMapping("/user/confirm/back")
    public String userBack(@ModelAttribute("userForm") UserForm userForm,
    		                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userForm", userForm);
        return "redirect:/user/registration";
    }
    
    @PostMapping("/user/registration")
    public String userRegister(@Validated(GroupOrder.class)
                                 @ModelAttribute("userForm") UserForm userForm,    		               		 
                           		 BindingResult result,
                           		 RedirectAttributes redirectAttributes,
                           		 Model model,
                           		@AuthenticationPrincipal LoginUserDetails loginUserDetails){
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            result.rejectValue("password", "error.passwordConfirmation", "パスワードが一致しません。");
        }
        if (result.hasErrors()) {
            return "user/registration";        
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("userForm", userForm);
        redirectAttributes.addFlashAttribute("model", modelMap);
        return "user/confirm";        
    }
    
    @PostMapping("/user/confirm")
    public String userConfirm(@ModelAttribute("userForm") UserForm userForm,    		               		 
                           	     String userId,
                           		 Model model){
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
        return "redirect:/";
    }    
    
    @PostMapping("/user/edit/{userId}")
    public String userUpdate(@PathVariable String userId,
    		                   @Validated(GroupOrder.class)
                               @ModelAttribute("userForm") UserForm updateForm,
                           	   BindingResult result,
                           	   Model model){
    	User user = userRepository.findOne(userId);
    	model.addAttribute("user", user);
        if (!updateForm.getPassword().equals(updateForm.getConfirmPassword())) {
            result.rejectValue("password", "error.passwordConfirmation", "パスワードが一致しません。");
        }
        if (result.hasErrors()) {
            return "user/edit";
        }
        BeanUtils.copyProperties(updateForm, user);
        userService.save(user);
        return "redirect:/";
    }    
    
    @ModelAttribute(name = "login_user")
    public UserDetails setLoginUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
      return loginUserDetails;
    }    

}
