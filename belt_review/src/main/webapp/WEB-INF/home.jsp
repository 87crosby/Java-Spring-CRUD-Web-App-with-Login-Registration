<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Home</title>
</head>
<body>
    <div class="container">
        <h1>Hello ${user.username}</h1>
        <a href="/logout">Logout</a>
        <table class="table table-dark">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Meal Name</th>
                    <th scope="col">Uploaded By</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items='${allMeals}' var='meal'>
                    <tr>
                        <th scope="row">${meal.id}</th>
                        <td><a href="/meal/info/${meal.id}">${meal.name}</a></td>
                        <td>${meal.uploader.username}</td>
                        <td><a href="/meal/${meal.id}/edit">Edit</a> || <a href="/delete/${meal.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <hr>
        <a href="/meals/new">Add a new meal</a>
    </div>
</body>
</html>