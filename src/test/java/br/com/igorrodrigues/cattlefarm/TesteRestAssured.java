package br.com.igorrodrigues.cattlefarm;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TesteRestAssured {

	@LocalServerPort
	int port;

	@Before
	public void configRestAssured() {

		RestAssured.config = RestAssuredConfig.config()
				.objectMapperConfig(new ObjectMapperConfig(new Jackson2Mapper(new Jackson2ObjectMapperFactory() {

					@Override
					public ObjectMapper create(Class cls, String charset) {
						ObjectMapper mapper = new ObjectMapper();
						mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
						mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						mapper.setDateFormat(df);
						mapper.registerModule(new JSR310Module());
						mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
						return mapper;
					}
				})));
	}

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

	@Test
	public void deveAdicionarBovino() {

		String data = "20/05/2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		Bovine bovine = new Bovine();
		bovine.setId(2571);
		bovine.setBirth(date);
		bovine.setWeight(1500.0);
		bovine.setValue(new BigDecimal(130));
		bovine.setWeightArrobaFree();
		bovine.setType(BovineType.BEZERRO);
		bovine.setSex(Sex.FEMALE);
		bovine.setAge();
		bovine.setNick("TESTES4");
		int id = bovine.getId();

		given().port(port).header("accept", "application/json").contentType("application/json").body(bovine).when()
				.post("/cattlefarm/api/listaBovinos/" + id);

		JsonPath path = given().port(port).header("accept", "application/json")
				.get("/cattlefarm/api/listaBovinos/" + id).andReturn().jsonPath();

		Bovine bovine1 = path.getObject("bovine", Bovine.class);

		Integer id1 = id;

		assertEquals(id1, bovine1.getId());

	}

	@Test
	public void deveDeletarBovino() {

		String data = "20/05/2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		Bovine bovine = new Bovine();
		bovine.setId(2571);
		bovine.setBirth(date);
		bovine.setWeight(1500.0);
		bovine.setValue(new BigDecimal(130));
		bovine.setWeightArrobaFree();
		bovine.setType(BovineType.BEZERRO);
		bovine.setSex(Sex.FEMALE);
		bovine.setAge();
		bovine.setNick("TESTES4");
		int id = bovine.getId();

		given().port(port).header("accept", "application/json").contentType("application/json").body(bovine).when()
				.post("/cattlefarm/api/listaBovinos/" + id);

//		expect().statusCode(200).when().delete("/cattlefarm/api/listaBovinos/" + id);

		int statusCode = given().port(port).when().delete("/cattlefarm/api/listaBovinos/" + id).andReturn()
				.statusCode();

		assertEquals(200, statusCode);
	}

}
