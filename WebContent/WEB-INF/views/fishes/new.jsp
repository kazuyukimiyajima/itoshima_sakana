<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name = "content">
        <h2>新規　魚　登録ページ</h2>

        <form method="POST" action="<c:url value='/fishes/create' />">
            <c:import url="_form.jsp" />
         </form>
    </c:param>
</c:import>