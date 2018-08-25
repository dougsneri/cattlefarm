<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:pageTemplate>
	<div class="container">
		<h2>Formulário de Cadastro: Bovino</h2>
		<p>${sucesso}</p>
		<form:form action="${s:mvcUrl('CC#saveBovine').build()}" method="post"
			modelAttribute="bovine">
			<div class="form-group row">
				<label for="id">Id:</label>
				<div class="col-sm-10">
					<form:input path="id" cssClass="form-control" id="id" />
					<form:errors path="id" cssClass="text-danger" />
				</div>
			</div>

			<div class="form-group row">
				<label for="id">Apelido:</label>
				<div class="col-sm-10">
					<form:input path="nick" cssClass="form-control" id="nick" />
				</div>
			</div>

			<div class="form-group row">
				<label for="birth">Nascimento:</label>
				<div class="col-sm-10">
					<form:input path="birth" cssClass="form-control"
						placeholder="10/10/2010" id="birth" />
					<form:errors path="birth" cssClass="text-danger" />
					<form:hidden path="age" />
				</div>
			</div>

			<div class="form-group row">
				<label for="weight">Peso:</label>
				<div class="col-sm-10">
					<form:input path="weight" cssClass="form-control"
						placeholder="Peso em Kg" id="weight" />
					<form:errors path="weight" cssClass="text-danger" />
				</div>
			</div>

			<label for="sex">Sexo:</label>
			<c:forEach items="${sex}" var="sexo">

				<div class="custom-control custom-radio">
					<form:radiobutton path="sex" cssClass="custom-control-input"
						value="${sexo}" id="${sexo}" />
					<label for="${sexo}" class="custom-control-label">${sexo}</label>
				</div>

			</c:forEach>
			<form:errors path="sex" cssClass="text-danger" />

			<label for="type">Tipo:</label>
			<c:forEach items="${type}" var="tipo">

				<div class="custom-control custom-radio">
					<form:radiobutton path="type" cssClass="custom-control-input"
						value="${tipo}" id="${tipo}" />
					<label for="${tipo}" class="custom-control-label">${tipo}</label>
				</div>

			</c:forEach>
			<form:errors path="type" cssClass="text-danger" />
			<c:choose>
				<c:when test="${bovine.type == 'VACA'}">
					<label for="countChild">Número de Crias:</label>
					<form:input path="CountChild" cssClass="form-control"
						placeholder="crias" id="CountChild" />
				</c:when>
				<c:when test="${bovine.type == null}">
					<label for="CountChild">Número de Crias:</label>
					<form:input path="countChild" cssClass="form-control"
						placeholder="crias" id="CountChild" />
				</c:when>
			</c:choose>
			<c:if test="${bovine.id == null }">
				<button type="submit" class="btn btn-primary">Cadastrar</button>
			</c:if>

			<c:if test="${bovine.id != null }">
				<button type="submit" class="btn btn-primary">Atualizar</button>
				<a href="${s:mvcUrl('CC#removeBovine').arg(0,bovine.id).build()}"><button
						type="button" class="btn btn-danger">Remover</button></a>
			</c:if>
		</form:form>

		<c:if test="${bovine.id != null }">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">Data Pesagem</th>
						<th scope="col">Peso</th>
						<th scope="col">Peso em @</th>
					</tr>
				</thead>
				<c:forEach var="dataPeso" items="${bovine.pesoEDataList}">
					<tbody>
						<tr>
							<td>${dataPeso.date}</td>
							<td>${dataPeso.weight}</td>
							<td>${dataPeso.weightArrobaFree}</td>
							<td><a href="${s:mvcUrl('CC#removeWeightAndDate').arg(0,dataPeso.id).build()}">Remover</a></td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</c:if>

	</div>
</tags:pageTemplate>