package guru.springframework.services.helper;

import java.util.Map;

public class ServiceCofigurationHelper {
	
	public String getBasePath() {
		return "";
	}
	
	public  String getView(String view, Boolean isRedirect) {
		String fullView = this.getBasePath()+view;
		if(isRedirect)
		   fullView = "redirect:/" + fullView;
		return fullView;
	}
	
	public String getDynamicView(String path, Map<String,String> placehodersMap) {
		for(String placeholder : placehodersMap.keySet()) {
			path = path.replace(placeholder, placehodersMap.get(placeholder));
		}
		return path;
	}
		
}
