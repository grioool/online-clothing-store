<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app_tag" uri="/WEB-INF/tld/customTags.tld" %>
<!DOCTYPE html>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="title.orders"/></title>
    <c:import url="../page/header.jsp"/>
</head>
<body>
<c:import url="../page/navbar.jsp"/>

<c:if test="${not requestScope.containsKey('pagination_context')}">
    <p class="text-center justify-content-center display-1 mb-4"><fmt:message key="title.emptyOrders"/></p>
</c:if>

<c:if test="${requestScope.containsKey('pagination_context')}">
    <p class="display-2 text-center"><fmt:message key="title.orders"/></p>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="label.name"/></th>
            <th scope="col"><fmt:message key="label.phoneNumber"/></th>
            <th scope="col"><fmt:message key="label.address"/></th>
            <th scope="col"><fmt:message key="label.orderProducts"/></th>
            <th scope="col"><fmt:message key="label.cost"/></th>
            <th scope="col"><fmt:message key="label.status"/></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.pagination_context.objectList}" var="order">
            <tr>
                <th scope="row">
                    <div class="mt-1">${order.id}</div>
                </th>
                <td>
                    <div class="mt-1">${order.user.firstName} ${order.user.lastName}</div>
                </td>
                <td>
                    <div class="mt-1">${order.user.phoneNumber}</div>
                </td>
                <td>
                    <div class="mt-1">${order.deliveryAddress}</div>
                </td>
                <td>
                    <c:forEach items="${order.products.entrySet()}" var="product">
                        <div class="mt-1">
                            <p style="width:50%; float: left">${product.getKey().name}</p>
                            <p class="text-end" style="width:50%; float: right"> X${product.getValue()}</p>
                        </div>
                    </c:forEach>
                    <c:if test="${empty order.products.entrySet()}">
                        <p class="text-center text-danger"><fmt:message key="label.deleteProduct"/></p>
                    </c:if>
                </td>
                <td>
                    <div class="mt-1">${order.cost}$</div>
                </td>

                <td>
                    <c:if test="${order.orderStatus.name() eq 'ACTIVE'}">
                        <select id="select-${order.id}" class="form-select" aria-label="Default select example"
                                name="select">
                            <option value="ACTIVE" <c:if test="${order.orderStatus.name() eq 'ACTIVE'}">selected</c:if>>
                                <fmt:message key="label.active"/>
                            </option>
                            <option value="CANCELLED"
                                    <c:if test="${order.orderStatus.name() eq 'CANCELLED'}">selected</c:if>>
                                <fmt:message key="label.cancelled"/>
                            </option>
                            <option value="COMPLETED"
                                    <c:if test="${order.orderStatus.name() eq 'COMPLETED'}">selected</c:if>>
                                <fmt:message key="label.completed"/>
                            </option>
                            <option value="UNACCEPTED"
                                    <c:if test="${order.orderStatus.name() eq 'UNACCEPTED'}">selected</c:if>>
                                <fmt:message key="label.unaccepted"/>
                            </option>
                        </select>
                    </c:if>

                    <c:if test="${order.orderStatus.name() ne 'ACTIVE'}">
                        <c:if test="${order.orderStatus.name() eq 'CANCELLED'}">
                            <fmt:message key="label.cancelled"/>
                        </c:if>
                        <c:if test="${order.orderStatus.name() eq 'COMPLETED'}">
                            <fmt:message key="label.completed"/>
                        </c:if>
                        <c:if test="${order.orderStatus.name() eq 'UNACCEPTED'}">
                            <fmt:message key="label.unaccepted"/>
                        </c:if>
                    </c:if>
                </td>
                <c:if test="${order.orderStatus.name() eq 'ACTIVE'}">
                    <td>
                        <button onclick="save(${order.id})" class="btn btn-dark"><fmt:message
                                key="button.save"/></button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="d-flex justify-content-center m-4">
        <ul class="pagination">
            <app_tag:pagination pages="${requestScope.pagination_context.totalPages}"
                                page="${requestScope.pagination_context.page}"
                                url='/cafe?command=to_orders&page='/>
        </ul>
    </div>
</c:if>
<script>
    async function save(id) {
        let data = new FormData();
        data.append('id', id);
        data.append('select', document.getElementById('select-' + id).value);
        data.append('command', 'update_order');

        jQuery.ajax({
            url: '<c:url value="/controller"/>',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            success: successSave
        });

        function successSave(data) {
            alert('<fmt:message key="info.saveSuccess"/>');

            let parse = JSON.parse(data);

            let redirectCommand = parse.redirect_command;
            if (redirectCommand != null) {
                window.location.href = '<c:url value="/controller"/>' + "?command="
                    + redirectCommand + '&type_id=${param.type_id}' + '&page=1';
            }
        }
    }
</script>

<c:import url="../page/footer.jsp"/>
</body>
</html>
