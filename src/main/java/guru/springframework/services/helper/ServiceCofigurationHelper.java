package guru.springframework.services.helper;

import java.util.Map;

public class ServiceCofigurationHelper {

	public String getBasePath() {
		return "";
	}

	public  String getView(String view, Boolean isRedirect) {
		String fullView = this.getBasePath()+view;
		if(isRedirect) {
		    fullView = getRedirectPrefix(fullView);
		}
		return fullView;
	}

	public String getDynamicView(String path, Map<String,String> placehodersMap, Boolean isRedirect) {
		for(String placeholder : placehodersMap.keySet()) {
			path = path.replace(placeholder, placehodersMap.get(placeholder));
		}
		if(isRedirect)
			path = getRedirectPrefix(path);
		else
			path = addMissingSlash(path); 
		return path;
	}

	private String getRedirectPrefix(String path) {
		if(path != null) {
			String redirect = path.startsWith("/") ? "redirect:" : "redirect:/";
			path = redirect + path;
			return path;
		}
		return "";
	}
	
	private String addMissingSlash(String path) {
		if (!path.startsWith("/")) {
			path = "/"+path; 
		}
		return path;
	}

}
