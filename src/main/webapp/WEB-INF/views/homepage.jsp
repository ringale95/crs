<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>CRS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="module" src="${pageContext.request.contextPath}/js/index.js"></script>
</head>

<body>
    <cm:header/>

    <div class="container mt-5">
        <h2>Report Status</h2>


        <table class="table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Commit</th>
                    <th>Status</th>
                    <th>URL</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="result" items="${results}">
                    <tr>
                        <td>${result.prTitle}</td>
                        <td>${result.author}</td>
                        <td>${result.commit}</td>
                        <td style="color: ${result.status ? 'green' : 'red'}; text-align: center;">
                            ${result.status ? 'PASSED' : 'FAILED'}
                        </td>
                        <td>
                            <a type="submit" class="btn btn-secondary" href="/crs/${result.id}">URL</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>

</html>
