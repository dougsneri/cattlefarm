package br.com.igorrodrigues.cattlefarm.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.DAOs.WeightAndDateDao;
import br.com.igorrodrigues.cattlefarm.Validation.BovineValidation;
import br.com.igorrodrigues.cattlefarm.models.Animal;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.WeightAndDate;
import br.com.igorrodrigues.cattlefarm.models.Sex;

@Controller
@RequestMapping("/flock")
public class CattleController {

	@Autowired
	private AnimalDao bovineDao;

	@Autowired
	private WeightAndDateDao weightAndDateDao;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(new BovineValidation());
	}

	@GetMapping("/bovineForm")
	public ModelAndView bovineForm(Bovine bovine) {
		ModelAndView modelAndView = new ModelAndView("flock/bovineForm");
		modelAndView.addObject("type", BovineType.values());
		modelAndView.addObject("sex", Sex.values());
		return modelAndView;
	}

	@PostMapping
	public ModelAndView saveBovine(@Valid Bovine bovine, BindingResult result, RedirectAttributes redirectAttribute) {

		if (result.hasErrors()) {
			return bovineForm(bovine);
		}
		bovine.setWeightArrobaFree();
		WeightAndDate weightAndDate = new WeightAndDate(LocalDate.now(), bovine.getWeight(), bovine);
		bovineDao.saveBovine(bovine);
		weightAndDateDao.save(weightAndDate);
		redirectAttribute.addFlashAttribute("sucesso", "Animal Cadastrado com sucesso");
		return new ModelAndView("redirect:/flock/bovineForm");
	}

	@GetMapping("/listaBovinos")
	public ModelAndView listBovines() {
		ModelAndView modelAndView = new ModelAndView("flock/listaAnimais");
		List<Bovine> bovines = bovineDao.listAllBovines();
		BigDecimal totalWeight = Bovine.setWeightListTotal(bovines);
		Animal.setAgeOfList(bovines);
		modelAndView.addObject("pesoTotal", totalWeight);
		modelAndView.addObject("animais", bovines);
		modelAndView.addObject("type", BovineType.values());
		modelAndView.addObject("sex", Sex.values());

		return modelAndView;
	}

	@GetMapping("/listaBovinosFiltrada")
	public ModelAndView searchBovine(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "sex", required = false) Sex sex,
			@RequestParam(value = "type", required = false) BovineType type,
			@RequestParam(value = "nick", required = false) String nick,
			@RequestParam(value = "arrobaValue", required = false) BigDecimal value) {

		ModelAndView modelAndView = new ModelAndView("flock/listaAnimais");
		List<Bovine> filteredBovines = bovineDao.listarBovinos(id, sex, type, nick);
		Animal.setAgeOfList(filteredBovines);
		BigDecimal totalWeight = Bovine.setWeightListTotal(filteredBovines);
		BigDecimal valueListTotal = new BigDecimal(0);
		
		if (value != null) {
			for (Bovine bovine : filteredBovines) {
				bovine.setValue(value);
			}
			valueListTotal = Bovine.setValueListTotal(filteredBovines);
		}
		
		Animal.setAgeOfList(filteredBovines);
		modelAndView.addObject("animais", filteredBovines);
		modelAndView.addObject("pesoTotal", totalWeight);
		modelAndView.addObject("valueTotal", valueListTotal);
		modelAndView.addObject("type", BovineType.values());
		modelAndView.addObject("sex", Sex.values());
		return modelAndView;

	}

	@GetMapping("/{id}/form")
	public ModelAndView update(@PathVariable Integer id, Model model) {
		Bovine bovine = bovineDao.find(id);
		model.addAttribute("bovine", bovine);
		return bovineForm(bovine);
	}

	@GetMapping("/BovinoRemovido")
	public ModelAndView removeBovine(@RequestParam Integer id, RedirectAttributes redirectAttribute) {
		bovineDao.remove(id);
		redirectAttribute.addFlashAttribute("removido", "Animal Removido com sucesso");
		return new ModelAndView("redirect:/flock/listaBovinos");
	}
	
	@GetMapping("/PesoEDataRemovido")
	public ModelAndView removeWeightAndDate(@RequestParam Integer id, RedirectAttributes redirectAttribute) {
		WeightAndDate weightAndDate = weightAndDateDao.find(id);
		Integer bovineId = weightAndDate.getBovine().getId();
		weightAndDateDao.remove(id);
		redirectAttribute.addFlashAttribute("removido", "Data removida com sucesso");
		return new ModelAndView("redirect:/flock/" + bovineId + "/form");
	}
	
}
