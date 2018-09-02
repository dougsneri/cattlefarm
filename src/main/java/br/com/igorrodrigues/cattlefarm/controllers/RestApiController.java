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
import br.com.igorrodrigues.cattlefarm.DAOs.WeightAndDateDao;
import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;
import br.com.igorrodrigues.cattlefarm.models.flock.Sex;
import br.com.igorrodrigues.cattlefarm.models.flock.WeightAndDate;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	AnimalDao animalDao;
	@Autowired
	WeightAndDateDao weightAndDateDao;

	@GetMapping(value = "/listaBovinos", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> listAllBovinesXml() {
		List<Bovine> listBovines = animalDao.listAllBovines();

		listBovines.forEach(b -> b.setAge());
		return listBovines;
	}

	@GetMapping(value = "/listaBovinos/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Bovine returnBovine(@PathVariable Integer id) {
		Bovine bovine = animalDao.find(id);
		bovine.setAge();
		return bovine;
	}

	@GetMapping(value = "/listaBovinosFiltrada", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> searchBovine(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "sex", required = false) Sex sex,
			@RequestParam(value = "type", required = false) BovineType type,
			@RequestParam(value = "nick", required = false) String nick,
			@RequestParam(value = "arrobaValue", required = false) BigDecimal arrobavalue) {

		List<Bovine> filteredBovines = animalDao.listarBovinos(id, sex, type, nick);
		filteredBovines.forEach(b -> b.setAge());
		
		if (arrobavalue != null) {
			filteredBovines.forEach(b -> b.setValue(arrobavalue));
			return filteredBovines;
		}
		return filteredBovines;
	}

	@PostMapping(value = "/listaBovinos/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Bovine> add(@RequestBody Bovine bovine) {
		WeightAndDate weightAndDate = new WeightAndDate(LocalDate.now(), bovine.getWeight(), bovine);
		animalDao.saveBovine(bovine);
		weightAndDateDao.save(weightAndDate);
		int id = bovine.getId();
		URI location = URI.create("/listaBovinos/" + id);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/listaBovinos/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void removeBovine(@PathVariable Integer id) {
		animalDao.remove(id);
	}
}
