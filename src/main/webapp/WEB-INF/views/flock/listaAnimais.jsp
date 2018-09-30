<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:pageTemplate>
	<div class="container">
		<h2>Lista de Animais</h2>
		<h5>Filtro</h5>
		<form action="${s:mvcUrl('CC#searchBovine').build()}" method="get">

			<div class="col-sm-3 my-1">
				<label class="sr-only" for="inlineFormInputName">ID:</label> <input
					name="id" class="form-control form-control-sm" placeholder="id" />
			</div>

			<div class="col-sm-3 my-1">
				<label class="sr-only" for="inlineFormInputName">Nick:</label> <input
					name="nick" class="form-control form-control-sm"
					placeholder="apelido" />
			</div>

			<label for="sex">Sexo:</label>
			<c:forEach var="sexo" items="${sex}">

				<div class="custom-control custom-radio">
					<input type="radio" id="${sexo}" name="sex" value="${sexo}"
						class="custom-control-input"> <label
						class="custom-control-label" for="${sexo}">${sexo}</label>
				</div>

			</c:forEach>

			<label for="type">Tipo:</label>
			<c:forEach var="tipo" items="${type}">

				<div class="custom-control custom-radio">
					<input type="radio" id="${tipo}" name="type" value="${tipo}"
						class="custom-control-input"> <label
						class="custom-control-label" for="${tipo}">${tipo}</label>
				</div>

			</c:forEach>

			<div class="col-sm-3 my-1">
				<label class="sr-only" for="inlineFormInputName">Valor da
					Arroba:</label> <input name="arrobaValue"
					class="form-control form-control-sm"
					placeholder="Valor da Arroba R$/@" />
			</div>

			<div class="col-auto my-1">
				<button type="submit" class="btn btn-secondary btn-sm">Filtrar</button>
			</div>
		</form>
		<p>${removido}</p>
		<table class="table table-hover">
			<thead>
				<tr>
					<th scope="col">Animal ID</th>
					<th scope="col">Apelido</th>
					<th scope="col">Sexo</th>
					<th scope="col">Idade</th>
					<th scope="col">Tipo</th>
					<th scope="col">Peso(kg)</th>
					<th scope="col">Peso(@) Líquido</th>
					<th scope="col">Valor</th>
				</tr>
			</thead>
			<c:forEach var="animal" items="${animais}">
				<tbody>
					<tr>
						<th scope="row"><a
							href="${s:mvcUrl('CC#update').arg(0,animal.id).build()}">${animal.id}</a></th>
						<td>${animal.nick}</td>
						<td>${animal.sex}</td>
						<td>${animal.age.getYears()} Anos e ${animal.age.getMonths()} Meses</td>
						<td>${animal.type}
						<td>${animal.weight}</td>
						<td>${animal.weightArrobaFree}</td>
						<td>${animal.value}</td>
					</tr>
				</tbody>
			</c:forEach>
			<tbody>
				<tr>
					<th scope="row">Total de Peso(@):</th>
					<td>${pesoTotal}</td>
					<th scope="row">Valor Total (R$):</th>
					<td>${valueTotal}</td>
				</tr>
			</tbody>
		</table>
	</div>
</tags:pageTemplate>