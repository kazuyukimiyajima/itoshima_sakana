<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <c:if test="${flush != null}">
        <div id="flush_success">
            <c:out value="${flush}"></c:out>
        </div>
    </c:if>
    <h2>糸島の旬の魚たち！</h2>
    <h3>マイお魚　一覧</h3>
    <table id="fish_list">
        <tbody>
            <tr>
                <th class="fish_name">名前</th>
                <th class="fish_fishname">魚の名前</th>
                <th class="fish_action">操作</th>
            </tr>
            <c:forEach var="fish" items="${fishes}" varStatus="status">
                <tr class="row${status.count % 2}">
                    <td class="fish_name"><c:out value="${fish.user.name}" /></td>
                    <td class="fishname">${fish.name}</td>
                    <td class="fish_action"><a href="<c:url value='/fishes/show?id=${fish.id}' />">詳細を見る</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="pagination">
        (全 ${fishes_count} 件)<br />
        <c:forEach var="i" begin="1" end="${((fishes_count - 1) / 15) + 1}" step="1">
        <c:choose>
            <c:when test="${i == page}">
                <c:out value="${i}" />&nbsp;
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/fishes/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
            </c:otherwise>
        </c:choose>
        </c:forEach>
    </div>
    <p><a href="<c:url value='/fishes/new' />">魚の登録</a></p>

    </c:param>
</c:import>