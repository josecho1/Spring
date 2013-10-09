/*
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2013 José Luis Villaverde Balsa.

This file is part of SpringDataMeu.

SpringDataMeu is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SpringDataMeu is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar. If not, see <http://www.gnu.org/licenses/>.
*/
package com.sprdata.controller;

import com.sprdata.init.exception.EmployeeNotFound;
import com.sprdata.model.Employee;
import com.sprdata.repository.EmployeeRepository;
import com.sprdata.service.EmployeeService;
import com.sprdata.validation.EmployeeValidator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author José Luis Villaverde jlvbalsa@gmail.com
 */
@Controller
@RequestMapping(value="/employee")
public class EmployeeController {
    
    
    @Autowired EmployeeRepository employeeRepository;
    
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeValidator employeeValidator;
    
    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.setValidator(employeeValidator);
    }
    
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public ModelAndView newEmployeePage() {
        ModelAndView mav = new ModelAndView("employee-new", "employee", new Employee());
	return mav;
    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView createNewEmployee(@ModelAttribute @Valid Employee employee,
                BindingResult result, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors())
            return new ModelAndView("employee-new");
		
	ModelAndView mav = new ModelAndView();
	String message = "New employee "+employee.getName()+" was successfully created.";
		
	employeeService.create(employee);
	mav.setViewName("redirect:/index.html");
				
	redirectAttributes.addFlashAttribute("message", message);	
        return mav;		
    }
    
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public ModelAndView employeeListPage() {
        ModelAndView mav = new ModelAndView("employee-list");
	List<Employee> employeeList = employeeService.findAll();
	mav.addObject("employeeList", employeeList);
	return mav;
    }    
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editEmployeepPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("employee-edit");
	Employee employee = employeeService.findById(id);
	mav.addObject("employee", employee);
	return mav;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
    public ModelAndView editEmployee(@ModelAttribute @Valid Employee employee,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws EmployeeNotFound {
		
        if (result.hasErrors())
            return new ModelAndView("employee-edit");
		
	ModelAndView mav = new ModelAndView("redirect:/index.html");
	String message = "Employee was successfully updated.";

	employeeService.update(employee);
		
	redirectAttributes.addFlashAttribute("message", message);	
	return mav;
	}
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteEmployee(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws EmployeeNotFound {
		
        ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
	Employee employee = employeeService.delete(id);
	String message = "The employee "+employee.getName()+" was successfully deleted.";
		
	redirectAttributes.addFlashAttribute("message", message);
	return mav;
    }
    
}
