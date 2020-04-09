package guru.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.ImageConfigurationHelper;

@Controller
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;
	private final ImageConfigurationHelper conf;
	
	public ImageController(ImageService imageService, RecipeService recipeService, ImageConfigurationHelper conf) {
		this.imageService = imageService;
		this.recipeService = recipeService;
		this.conf = conf;
	}
	
	@GetMapping("recipes/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		 model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
		 return conf.BASE_PATH+conf.UPLOAD_FORM;
	}
	
	@PostMapping("recipes/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(new Long(id), file);
		Map<String,String> params = new HashMap<String,String>();
		params.put(conf.RECIPE_ID, id);
		return conf.getDynamicView(conf.PATH_RECIPE_SHOW, params, true);
	}
	
	@GetMapping("recipes/{id}/recipeimage")
	public void getRecipeImage(@PathVariable String id, HttpServletResponse response) throws IOException {
		Byte[] bytes = imageService.getRecipeImage(new Long(id));
		byte[] byteArray = new byte[bytes.length];
		int i = 0;
		for (Byte wrappedByte : bytes) {
			byteArray[i++] = wrappedByte;
		}
		response.setContentType("image/jpg");
		InputStream is = new ByteArrayInputStream(byteArray);
		IOUtils.copy(is, response.getOutputStream());
	}
	
   
	
}
