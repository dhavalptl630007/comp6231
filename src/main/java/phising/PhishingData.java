package phising;

import java.util.LinkedList;

public class PhishingData {

	static LinkedList<String> listOfPhishing = new LinkedList<String>();
	
	static String link1 = "https://oursafedownloads.com/financereviews/FBG/";
	static String link2 = "https://tinyurl.com/qwqt9yr";
	static String link3 = "http://upgrade-100056.weebly.com/login.htm";
	static String link4 = "https://upgrade-100056.weebly.com/login.html";
	static String link5 = "https://mailcheckeracountattyahoo.weebly.com/";
	static String link6 = "http://users-web.16mb.com/facebook/";
	static String link7 = "https://www.amazon.com/";
	static String link8 = "https://www.facebook.com/";
	static String link9 = "https://www.thenorthface.com/fr_ca/homepage.html";
	static String link10 = "https://www.phishtank.com/";
	
	
	public static LinkedList<String> getData() {
		
		listOfPhishing.add(link1);
		listOfPhishing.add(link2);
		listOfPhishing.add(link3);
		listOfPhishing.add(link4);
		listOfPhishing.add(link5);
		listOfPhishing.add(link6);
		listOfPhishing.add(link7);
		listOfPhishing.add(link8);
		listOfPhishing.add(link9);
		listOfPhishing.add(link10);
		return listOfPhishing;
	}
	
}
