package guru.springframework.services.helper;

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
		
}
