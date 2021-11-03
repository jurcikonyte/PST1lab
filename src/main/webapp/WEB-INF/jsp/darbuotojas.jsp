<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<p>Add new Darbuotojas:</p>
<form:form method="post" modelAttribute="darbuotojas">

	<form:input path="id" type="hidden" required="required" />
	<form:errors path="id" />

	<form:label path="vardas">Vardas</form:label>
	<form:input path="vardas" type="text" required="required" />
	<form:errors path="vardas" />

	<form:label path="miestas">Miestas</form:label>
	<form:input path="miestas" type="text" required="required" />
	<form:errors path="miestas" />

	<button type="submit">OK</button>
</form:form>
</div>
<%@ include file="common/footer.jspf"%>