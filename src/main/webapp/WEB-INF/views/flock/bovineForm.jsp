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
		<form:form action="${s:mvcUrl('CC#saveBovine').build() }" method="post"
			modelAttribute="bovine">
			<div class="form-group row">
				<label for="id">Id:</label>
				<div class="col-sm-10">
					<form:input path="id" cssClass="form-control" id="id"/>
					<form:errors path="id" cssClass="text-danger"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="id">Apelido:</label>
				<div class="col-sm-10">
					<form:input path="nick" cssClass="form-control" id="nick"/>
					<form:errors path="nick" cssClass="text-danger"/>
				</div>
			</div>

			<div class="form-group row">
				<label for="birth">Nascimento:</label>
				<div class="col-sm-10">
					<form:input path="birth" cssClass="form-control"
						placeholder="10/10/2010" id="birth"/>
						<form:errors path="birth" cssClass="text-danger"/>
						<form:hidden path="age"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="weight">Peso:</label>
				<div class="col-sm-10">
					<form:input path="weight" cssClass="form-control"
						placeholder="Peso em Kg" id="weight"/>
						<form:errors path="weight" cssClass="text-danger"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="sex">Sexo:</label>
				<div class="form-check form-check-inline">
					<c:forEach items="${sex}" var="sexo">
						<form:radiobutton path="sex" cssClass="form-control form-check-input" value="${sexo}" id="sex"/>
						<label for="sex" class="form-check-lable">${sexo}</label>
					</c:forEach>
					<form:errors path="sex" cssClass="text-danger"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="type">Tipo:</label>
				<div class="form-check form-check-inline">
					<c:forEach items="${type}" var="tipo">
						<form:radiobutton path="type" cssClass="form-control form-check-input" value="${tipo}" id="type" />
						<label for="type" class="form-check-lable">${tipo}</label>
					</c:forEach>
					<form:errors path="type" cssClass="text-danger"/>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="type">Status:</label>
				<div class="form-check form-check-inline">
					<c:forEach items="${status}" var="status">
						<form:radiobutton path="status" cssClass="form-control form-check-input" value="${status}" id="status" />
						<label for="type" class="form-check-lable">${status}</label>
					</c:forEach>
					<form:errors path="type" cssClass="text-danger"/>
				</div>
			</div>

			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>

	</div>
</tags:pageTemplate>