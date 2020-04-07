package guru.springframework.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	@Override
	public void saveImageFile(Long recipeId, MultipartFile imageFile) {
		
		log.debug("Received a file");
		
	}

}
