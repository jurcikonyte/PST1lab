<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<p>Add new Preke:</p>
<form:form method="post" modelAttribute="preke">
	
	<form:input path="kodas" type="hidden" required="required" />
	<form:errors path="kodas" />

	<form:label path="pavadinimas">Pavadinimas</form:label>
	<form:input path="pavadinimas" type="text" required="required" />
	<form:errors path="pavadinimas" />

	<form:label path="salis">Salis</form:label>
	<form:input path="salis" type="text" required="required" />
	<form:errors path="salis" />

	<form:label path="kainaVnt">Kaina Vnt</form:label>
	<form:input path="kainaVnt" type="text" required="required" />
	<form:errors path="kainaVnt" />

	<button type="submit">OK</button>
</form:form>
</div>
<%@ include file="common/footer.jspf"%>