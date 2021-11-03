<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
<H1>List of darbuotojai:</H1>

<!--
<p>${darbuotojai}</p>
-->

<table border="1">
<thead>
<tr>
<th>Id</th><th>Vardas</th><th>Update</th><th>Delete</th>
</tr>
</thead>
<tbody>
<c:forEach items="${darbuotojai}" var="darbuotojas">
<tr>
<td>${darbuotojas.id}</td>
<td>${darbuotojas.vardas}</td>
<td>${darbuotojas.miestas}</td>
<td><a type="button" href="/update-darbuotojas/${darbuotojas.id}">UPDATE</a></td>
<td><a type="button" href="/delete-darbuotojas/${darbuotojas.id}">DELETE</a></td>
</tr>
</c:forEach>

</tbody>
</table>

<div>
<a href="add-darbuotojas">ADD darbuotojas</a>
</div>
</div>
<%@ include file="common/footer.jspf"%>