package br.com.igorrodrigues.cattlefarm.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.DAOs.PesoEDataDao;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.PesoEData;
import br.com.igorrodrigues.cattlefarm.models.Sex;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	AnimalDao animalDao;
	@Autowired
	PesoEDataDao pesoEDataDao;

	@GetMapping(value = "/listaBovinos", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> listAllBovinesXml() {
		List<Bovine> lsitaBovinos = animalDao.listarTodosBovinos();

		for (Bovine bovine : lsitaBovinos) {
			bovine.setAge();
		}
		return lsitaBovinos;
	}

	@GetMapping(value = "/listaBovinos/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Bovine retornaBovino(@PathVariable Integer id) {
		Bovine bovine = animalDao.find(id);
		bovine.setAge();
		return bovine;
	}

	@GetMapping(value = "/listaBovinosFiltrada", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> buscarBovino(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "sex", required = false) Sex sex,
			@RequestParam(value = "type", required = false) BovineType type,
			@RequestParam(value = "nick", required = false) String nick,
			@RequestParam(value = "arrobaValue", required = false) BigDecimal arrobavalue) {

		List<Bovine> bovinosFiltrados = animalDao.listarBovinos(id, sex, type, nick);
		Bovine.setAgeOfList(bovinosFiltrados);
		if (arrobavalue != null) {
			for (Bovine bovine : bovinosFiltrados) {
				bovine.setValue(arrobavalue);
			}
			return bovinosFiltrados;
		}
		return bovinosFiltrados;
	}

	@PostMapping(value = "/listaBovinos/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Bovine> adiciona(@RequestBody Bovine bovine) {
		PesoEData pesoEData = new PesoEData(LocalDate.now(), bovine.getWeight(), bovine);
		animalDao.saveBovine(bovine);
		pesoEDataDao.save(pesoEData);
		int id = bovine.getId();
		URI location = URI.create("/listaBovinos/" + id);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/listaBovinos/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void removeBovino(@PathVariable Integer id) {
		animalDao.remove(id);
	}
}
