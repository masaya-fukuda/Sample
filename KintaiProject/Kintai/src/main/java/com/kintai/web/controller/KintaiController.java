package com.kintai.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintai.business.domain.Kintai;
import com.kintai.business.repository.KintaiRepository;
import com.kintai.business.service.KintaiService;
import com.kintai.web.form.ChangeDateForm;
import com.kintai.web.form.KintaiForm;
import com.kintai.web.security.LoginUserDetails;
import com.kintai.web.validation.GroupOrder;

@Controller
public class KintaiController {
	
	@Autowired
	private KintaiRepository kintaiRepository;
	
	@Autowired
	private KintaiService kintaiService;
	
    @RequestMapping(value = "/")
    public ModelAndView index(@ModelAttribute @RequestParam(value = "changeDate", required = false) String changeDate,
    						  @RequestParam(defaultValue = "") String keyword,
    						  RedirectAttributes redirectAttributes, 
    						  String dayOfWeek,
    						  ModelAndView mav) {
    	String showDate;
    	if (changeDate != null){
    		showDate = changeDate;
    	} else {
    		Calendar calendar = Calendar.getInstance();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    		showDate = sdf.format(calendar.getTime());
    		changeDate = showDate;
    	}
    	List<Kintai> kintais = kintaiRepository.findByDate(showDate);
    	List<Kintai> showKintais = kintais.stream()
    			.filter(line -> line.getUser().getDepartment().contains(keyword))
    			.collect(Collectors.toList());
    	showKintais = sortByTypes(showKintais);
    	dayOfWeek = getDayOfWeek(showDate);
    	mav.addObject("kintais",showKintais);
    	mav.addObject("changeDate", changeDate);
    	mav.addObject("dayOfWeek",dayOfWeek);
        mav.setViewName("kintai/index");
        return mav;
    }
    
    @RequestMapping(value = "/kintai/registration")
    ModelAndView registration(@ModelAttribute("kintaiForm") KintaiForm kintaiForm, ModelAndView mav) {
        mav.addObject("kintaiForm", kintaiForm);        
        mav.setViewName("kintai/registration");
        return mav;
    }
    
    @GetMapping("/kintai/change_date")
    public String changeDate(ChangeDateForm changeDateForm) {
        return "kintai/change_date";
    }
    
    @RequestMapping(value = "/kintai/detail/{id}", method = RequestMethod.GET)
    ModelAndView detail(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, ModelAndView mav) {
        Kintai kintai = kintaiRepository.findOne(id);
        mav.addObject("kintai", kintai);
        mav.setViewName("kintai/detail");
        mav.addObject("login_user", userDetails);
        return mav;
    }
    
    @RequestMapping(value = "/kintai/edit/{id}", method = RequestMethod.GET)
    ModelAndView edit(@PathVariable Long id, @ModelAttribute("kintaiForm") KintaiForm updateForm, ModelAndView mav) {
        Kintai kintai = kintaiRepository.findOne(id);
        mav.addObject("kintai", kintai);
        mav.addObject("kintaiForm", updateForm);
        mav.setViewName("kintai/edit");
        return mav;
    }
    
    @RequestMapping(value = "/kintai/delete/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") Long id, 
    						  @AuthenticationPrincipal LoginUserDetails loginUserDetails,
    						  ModelAndView mav) {
        Kintai kintai = kintaiRepository.findOne(id);
        if (!kintai.getUser().getUserId().equals(loginUserDetails.getUserId())) {
        	mav.addObject("kintai", kintai);
            mav.setViewName("kintai/detail");
            return mav;
        }
        kintaiRepository.delete(kintai);
        mav.setViewName("redirect:/");
        return mav;
    }

    @RequestMapping(value = "/kintai/confirm", method = RequestMethod.GET)
    public String confirm(@ModelAttribute("model")ModelMap modelMap,
    					  Model model) {
    	KintaiForm kintaiForm = (KintaiForm) modelMap.get("kintaiForm");
        model.addAttribute("kintaiForm", kintaiForm);
        return "kintai/confirm";
    }
    
    @RequestMapping("/kintai/confirm/back")
    public String kintaiBack(@ModelAttribute("kintaiForm") KintaiForm kintaiForm,
    		                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("kintaiForm", kintaiForm);
        return "redirect:/kintai/registration";
    }

    @ModelAttribute("types")
    public List<String> ckBoxList() {
        List<String> list = new ArrayList<>();
        list.add("遅刻");
        list.add("全休");
        list.add("午前休");
        list.add("午後休");
        list.add("早退");
        list.add("外出");
        list.add("直行");
        list.add("直帰");
        list.add("直行直帰");        
        return list;
    }
    
    @ModelAttribute("timelist")
    public List<String> ckRadioList() {
        List<String> list = new ArrayList<>();
        list.add("午前");
        list.add("午後");
        list.add("終日");
        return list;
    }

    
    @PostMapping("/kintai/registration")
    public String kintaiRegister(@Validated(GroupOrder.class)
                                 @ModelAttribute("kintaiForm") KintaiForm kintaiForm,    		               		 
                           		 BindingResult result,
                           		 RedirectAttributes redirectAttributes,
                           		 Model model){
        if (result.hasErrors()) {
            return "kintai/registration";
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("kintaiForm", kintaiForm);
        redirectAttributes.addFlashAttribute("model", modelMap);
        return "kintai/confirm";
    }
    
    @PostMapping("/kintai/confirm")
    public String kintaiConfirm(@ModelAttribute("kintaiForm") KintaiForm kintaiForm,    		               		 
                           	     String userId,
                           		 Model model){
        Kintai kintai = new Kintai();
        BeanUtils.copyProperties(kintaiForm, kintai);
        kintaiService.save(kintai,userId);
        return "redirect:/";
    }
    
    @PostMapping("/kintai/change_date")
    public String kintaiChangeDate(@Validated(GroupOrder.class)
                                   @ModelAttribute ChangeDateForm changeDateForm,
                                   BindingResult result,
    					   		   RedirectAttributes redirectAttributes,    					   		   
    					   		   Model model){
        if (result.hasErrors()) {
            return "/kintai/change_date";
        }
        String changeDate = changeDateForm.getChangeDate();
        redirectAttributes.addAttribute("changeDate", changeDate);
        return "redirect:/";
    }
    
    @PostMapping("/kintai/edit/{id}")
    public String kintaiUpdate(@PathVariable Long id,
    						   String userId,
    		                   @Validated(GroupOrder.class)
                               @ModelAttribute("kintaiForm") KintaiForm updateForm,
                           	   BindingResult result,
                           	   Model model){
    	Kintai kintai = kintaiRepository.findOne(id);
    	model.addAttribute("kintai", kintai);
        if (result.hasErrors()) {
            return "kintai/edit";
        }
        BeanUtils.copyProperties(updateForm, kintai);
        kintaiService.save(kintai,userId);
        return "redirect:/";
    }
    
    private String getDayOfWeek(String showDate){
    	String dayOfWeek = "";
    	Calendar cal = Calendar.getInstance();		
		int year =  Integer.parseInt(showDate.substring(0,4));
		int month = Integer.parseInt(showDate.substring(5,7));
		int day = Integer.parseInt(showDate.substring(8));
		cal.set(year, month-1, day);
		switch (cal.get(Calendar.DAY_OF_WEEK)) { 
	    case Calendar.SUNDAY:
	    	dayOfWeek = "(日)";
	        break;
	    case Calendar.MONDAY:
	    	dayOfWeek = "(月)";
	        break;
	    case Calendar.TUESDAY:
	    	dayOfWeek = "(火)";
	        break;
	    case Calendar.WEDNESDAY:
	    	dayOfWeek = "(水)";	    	
	        break;
	    case Calendar.THURSDAY:
	    	dayOfWeek = "(木)";
	        break;
	    case Calendar.FRIDAY:
	    	dayOfWeek = "(金)";
	        break;
	    case Calendar.SATURDAY:
	    	dayOfWeek = "(土)";
	        break;
		}
    	return dayOfWeek;
    }
    
    private List<Kintai> sortByTypes(List<Kintai> showKintais){
    	List<Kintai> sortList = new ArrayList<>();
      	List<Kintai> sortList01 = new ArrayList<>();
       	List<Kintai> sortList02 = new ArrayList<>();    		
       	List<Kintai> sortList03 = new ArrayList<>();    		
       	List<Kintai> sortList04 = new ArrayList<>();    		
       	List<Kintai> sortList05 = new ArrayList<>();    		
       	List<Kintai> sortList06 = new ArrayList<>();    		
       	List<Kintai> sortList07 = new ArrayList<>();    		        	
       	List<Kintai> sortList08 = new ArrayList<>();    		
       	List<Kintai> sortList09 = new ArrayList<>();    	        	    	
    	for (Kintai showKintai :showKintais){
    		switch(showKintai.getType()){
    			case "遅刻":
    				sortList01.add(showKintai);
    				break;
    			case "全休":
    				sortList02.add(showKintai);
    				break;
    			case "午前休":
    				sortList03.add(showKintai);
    				break;
    			case "午後休":
    				sortList04.add(showKintai);
    				break;
    			case "早退":
    				sortList05.add(showKintai);
    				break;
    			case "外出":
    				sortList06.add(showKintai);
    				break;
    			case "直行":
    				sortList07.add(showKintai);
    				break;
    			case "直帰":
    				sortList08.add(showKintai);
    				break;
    			case "直行直帰":
    				sortList09.add(showKintai);
    				break;
    		} 	
    	}
    	sortList.addAll(sortList01);
    	sortList.addAll(sortList02);
    	sortList.addAll(sortList03);
    	sortList.addAll(sortList04);
    	sortList.addAll(sortList05);
    	sortList.addAll(sortList06);
    	sortList.addAll(sortList07);
    	sortList.addAll(sortList08);
    	sortList.addAll(sortList09);   
    	return sortList;
    }
}
