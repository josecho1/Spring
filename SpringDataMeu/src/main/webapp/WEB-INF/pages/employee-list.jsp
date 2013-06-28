<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Employee List page</title>
</head>
<body>
    <h1>Employee List page</h1>
    <table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
        <thead>
            <tr>
                <th width="25px">Id</th>
                <th width="150px">Name</th>
                <th width="28px">Salaries</th>
                <th width="40px">Start Date</th>
                <th width="70px">Address</th>
                <th width="28px">Department</th>
                <th width="28px">Manager</th>
                <th width="50px">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="employee" items="${employeeList}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.salary}</td>
                    <td>${employee.startDate}</td>
                    <td>${employee.address}</td>
                    <td>${employee.dept.name}</td>
                    <td>${employee.manager.name}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/employee/edit/${employee.id}.html">Edit</a><br/>
                        <a href="${pageContext.request.contextPath}/employee/delete/${employee.id}.html">Delete</a><br/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/">Home page</a>
</body>
</html>
