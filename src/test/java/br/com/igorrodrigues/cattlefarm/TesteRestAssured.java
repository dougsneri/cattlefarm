package br.com.igorrodrigues.cattlefarm;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.igorrodrigues.cattlefarm.models.Bovine;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TesteRestAssured {

	@LocalServerPort
	int port;

	@Test
	public void deveRetornarListaBovinos() {

		XmlPath xmlPath = given().port(port).header("accept", "application/xml").get("/cattlefarm/api/listaBovinos")
				.andReturn().xmlPath();
		List<Bovine> list = xmlPath.getList("List.item", Bovine.class);

		Integer id1 = 1;
		Integer id2 = 5;

		assertEquals(id1, list.get(0).getId());
		assertEquals(id2, list.get(1).getId());
	}

	@Test
	public void deveRetornarBovino() {
		JsonPath path = given().port(port).header("accept", "application/json").get("/cattlefarm/api/listaBovinos/1")
				.andReturn().jsonPath();
		Bovine bovine1 = path.getObject("bovine", Bovine.class);

		Integer id1 = 1;

		assertEquals(id1, bovine1.getId());
	}

	@Test
	public void retornarListaFiltradaBovinos() {
		JsonPath path = given().port(port).param("id", 1).header("accept", "application/json")
				.get("/cattlefarm/api/listaBovinosFiltrada").andReturn().jsonPath();

		List<Bovine> lista = path.getList("List", Bovine.class);

		Integer id = 1;

		assertEquals(id, lista.get(0).getId());
	}
}
