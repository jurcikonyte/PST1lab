<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
<H1>List of skambuciai:</H1>
<!--
<p>${skambuciai}</p>
-->
<table border="1">
<caption>skambuciai</caption>
<thead>
<tr>
<th>Atsiliepta</th>
<th>Laikas</th>
<th>DarbuotojoId</th>
<th>Trukme.</th>
<th>Update</th>
<th>Delete</th>
</tr>
</thead>
<tbody>
<c:forEach items="${skambuciai}" var="skambutis">
<tr>
<td>${skambutis.atsiliepta}</td>
<td>${skambutis.laikas}</td>
<td>${skambutis.darbuotojoId}</td>
<td>${skambutis.trukme}</td>
<td><a type="button" href="/update-skambutis?id=${skambutis.id}">UPDATE</a></td>
<td><a type="button" href="/delete-skambutis?id=${skambutis.id}">DELETE</a></td>
</tr>
</c:forEach>

</tbody>
</table>
<div>
<a href="add-skambutis">ADD skambutis</a>
</div>
</div>
<%@ include file="common/footer.jspf"%>