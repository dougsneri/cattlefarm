package br.com.igorrodrigues.cattlefarm.controllers;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;
import br.com.igorrodrigues.cattlefarm.models.flock.Sex;
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
	private Bovine bovine;
	
	@BeforeClass
	public static void configRestAssured() {

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

	@Before
	public void criaBovinosOnDB() {
		String data = "20/05/2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		bovine = new Bovine();
		bovine.setId(1);
		bovine.setBirth(date);
		bovine.setWeight(1500.0);
		bovine.setValue(new BigDecimal(130));
		bovine.setWeightArrobaFree();
		bovine.setType(BovineType.BEZERRO);
		bovine.setSex(Sex.FEMALE);
		bovine.setAge();
		bovine.setNick("TESTES4");
	}

	@Test
	public void deveAdicionarBovino() {

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
	public void deveRetornarBovino() {
		JsonPath path = given().port(port).header("accept", "application/json")
				.get("/cattlefarm/api/listaBovinos/" + bovine.getId()).andReturn().jsonPath();
		Bovine bovine1 = path.getObject("bovine", Bovine.class);
		
		assertEquals(bovine.getId(), bovine1.getId());
	}
	
	@Test
	public void deveRetornarListaBovinos() {
		deveAdicionarBovino();

		XmlPath xmlPath = given().port(port).header("accept", "application/xml").get("/cattlefarm/api/listaBovinos")
				.andReturn().xmlPath();
		List<Bovine> lista = xmlPath.getList("List.item", Bovine.class);

		assertFalse(lista.isEmpty());
	}


	@Test
	public void retornarListaFiltradaBovinos() {
		JsonPath path = given().port(port).param("id", bovine.getId()).header("accept", "application/json")
				.get("/cattlefarm/api/listaBovinosFiltrada").andReturn().jsonPath();

		List<Bovine> lista = path.getList("List", Bovine.class);

		assertEquals(bovine.getId(), lista.get(0).getId());
	}


	@Test
	public void deveDeletarBovino() {

		String data = "20/05/2010";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		Bovine bovine2 = new Bovine();
		bovine2.setId(2571);
		bovine2.setBirth(date);
		bovine2.setWeight(1500.0);
		bovine2.setValue(new BigDecimal(130));
		bovine2.setWeightArrobaFree();
		bovine2.setType(BovineType.BEZERRO);
		bovine2.setSex(Sex.FEMALE);
		bovine2.setAge();
		bovine2.setNick("TESTES5");
		int id = bovine2.getId();

		given().port(port).header("accept", "application/json").contentType("application/json").body(bovine2).when()
				.post("/cattlefarm/api/listaBovinos/" + id);

		expect().statusCode(200).when().delete("/cattlefarm/api/listaBovinos/" + id);

	}
	
}
