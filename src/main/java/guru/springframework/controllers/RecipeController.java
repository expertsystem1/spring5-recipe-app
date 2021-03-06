package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.RecipeConfigurationHelper;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/recipes")
@Slf4j
public class RecipeController {
	
	private final RecipeService service;
    private final RecipeConfigurationHelper conf;

	public RecipeController(RecipeService service, RecipeConfigurationHelper conf) {
		this.service = service;
		this.conf = conf;
	}
	
	@RequestMapping({"/","","index","index.html"})
	public String getList(Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_ITEM_LIST,service.findAll());
		return conf.getView(conf.INDEX_PAGE,false);
	}
	
	@RequestMapping("/{id}/show")
	public String showItemById(@PathVariable String id, Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_ITEM,service.findById(new Long(id)));
		return conf.getView(conf.SHOW,false);
	}
	
	@RequestMapping("/{id}/update")
	public String updateItem(@PathVariable String id, Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_ITEM,service.findCommandById(new Long(id)));
		return conf.getView(conf.FORM_NAME,false);
	}
	
	@RequestMapping("/{id}/delete")
	public String deleteItem(@PathVariable String id) {
		service.deleteById(new Long(id));
		return conf.getView("", true);
	}
	
	@RequestMapping("/new")
	public String newItem(Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_ITEM, new RecipeCommand());
		return conf.getView(conf.FORM_NAME,false);
	}
	
	@PostMapping({"/",""})
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = service.saveCommand(command);
		return conf.getView(savedCommand.getId()+"/"+conf.SHOW+"/", true);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception",exception);
		modelAndView.setViewName("404error");
		return modelAndView;
	}
			
}
