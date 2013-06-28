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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprdata.service;

import com.sprdata.init.exception.EmployeeNotFound;
import com.sprdata.model.Employee;
import java.util.List;

/**
 *
 * @author José Luis Villaverde jlvbalsa@gmail.com
 */
public interface EmployeeService {
    
        public Employee create(Employee employee);
	public Employee delete(int id) throws EmployeeNotFound;
	public List<Employee> findAll();
	public Employee update(Employee employee) throws EmployeeNotFound;
	public Employee findById(int id);
}
