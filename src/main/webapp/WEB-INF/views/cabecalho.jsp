<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>

<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">

<title>Cattle Farm</title>
</head>
<body>
	<div class="container-fluid">
		<ul class="nav nav-pills">
			<li class="nav-item"><a class="nav-link active"
				href="${s:mvcUrl('HC#index').build()}">FarmControl</a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
				role="button" aria-haspopup="true" aria-expanded="false">Rebanho</a>
				<div class="dropdown-menu">
					<a class="dropdown-item"
						href="${s:mvcUrl('CC#bovineForm').build()}">Cadastrar Bovino</a> <a
						class="dropdown-item" href="${s:mvcUrl('CC#listBovines').build()}">Listar
						Bovinos</a> <a class="dropdown-item" href="#">Something else here</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="#">Separated link</a>
				</div></li>
			<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
			<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
			</li>
		</ul>
	</div>