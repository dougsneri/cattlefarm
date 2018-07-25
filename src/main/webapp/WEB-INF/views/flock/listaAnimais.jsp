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
		<form action="${s:mvcUrl('CC#buscarBovino').build() }" method="post">
		
			<div class="col-sm-3 my-1">
				<label class="sr-only" for="inlineFormInputName">ID:</label> <input
					name="id" class="form-control form-control-sm" placeholder="id" />
			</div>
			
			<div class="col-sm-3 my-1">
				<label class="sr-only" for="inlineFormInputName">Nick:</label> <input
					name="nick" class="form-control form-control-sm" placeholder="apelido" />
			</div>

			<div class="col-auto my-1">
				<button type="submit" class="btn btn-secondary btn-sm">Filtrar</button>
			</div>
		</form>


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
				</tr>
			</thead>
			<c:forEach var="animal" items="${animais}">
				<tbody>
					<tr>
						<th scope="row">${animal.id}</th>
						<td>${animal.nick}</td>
						<td>${animal.sex}</td>
						<td>${animal.age}</td>
						<td>${animal.type}
						<td>${animal.weight}</td>
						<td>${animal.weightArrobaFree}</td>
					</tr>
				</tbody>
			</c:forEach>
			<tbody>
				<th scope="row">Total de Peso(@):</th>
				<td>${pesoTotal}</td>
			</tbody>
		</table>
	</div>
</tags:pageTemplate>