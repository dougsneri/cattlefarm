package br.com.igorrodrigues.cattlefarm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.models.Bovine;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	AnimalDao animalDao;

	@GetMapping("/listaBovinos")
	public List<Bovine> listAllBovines() {
		return animalDao.listarTodosBovinos();
	}

}
