package br.com.igorrodrigues.cattlefarm.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.Validation.BovineValidation;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;
import br.com.igorrodrigues.cattlefarm.models.StatusAnimal;

@Controller
@RequestMapping("/flock")
public class CattleController {

	@Autowired
	private AnimalDao bovineDao;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new BovineValidation());
	}

	@GetMapping("/bovineForm")
	public ModelAndView bovineForm(Bovine bovine) {
		ModelAndView modelAndView = new ModelAndView("flock/bovineForm");
		modelAndView.addObject("type", BovineType.values());
		modelAndView.addObject("sex", Sex.values());
		modelAndView.addObject("status", StatusAnimal.values());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView saveBovine(@Valid Bovine bovine, BindingResult result, RedirectAttributes redirectAttribute) {

		if (result.hasErrors()) {
			return bovineForm(bovine);
		}
		bovine.setWeightArrobaFree();
		bovineDao.save(bovine);
		redirectAttribute.addFlashAttribute("sucesso", "Animal Cadastrado com sucesso");
		return new ModelAndView("redirect:/flock/bovineForm");
	}

	@GetMapping("/listaBovinos")
	public ModelAndView listarBovinos() {
		ModelAndView modelAndView = new ModelAndView("flock/listaAnimais");
		BigDecimal pesoTotal = new BigDecimal(0);
		List<Bovine> animais = bovineDao.listarTodosBovinos();
		for (Bovine bovine : animais) {
			bovine.setAge();
			pesoTotal = pesoTotal.add(bovine.getWeightArrobaFree()).setScale(2, RoundingMode.HALF_EVEN);
		}
		modelAndView.addObject("pesoTotal", pesoTotal);
		modelAndView.addObject("animais", animais);

		System.out.println(animais);
		return modelAndView;
	}

}