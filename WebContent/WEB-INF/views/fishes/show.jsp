<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${fish != null}">
                <h2>お魚 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>名前</th>
                            <td><c:out value="${fish.user.name}" /></td>
                        </tr>
                        <tr>
                            <th>魚の種類</th>
                            <td>
                                <pre><c:out value="${fish.name}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${fish.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${fish.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_user.id == fish.user.id}">
                    <p><a href="<c:url value="/fishes/edit?id=${fish.id}" />">この魚を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
               <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/fishes/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>