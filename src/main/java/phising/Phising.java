package phising;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Phising {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("Phising").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> textFile = sc.textFile("C:/Users/Admin/Desktop/netflix.txt");
		
		String url="http://netflix.hotmaster.dns-cloud.net/clienteseguro/site/index.html";
		int having_IP_Address = checkIp(url);
		int URL_Length = checkLen(url);
		int Shortining_Service = checkTiny(url);
		int having_At_Symbol = url.contains("@")? 1 : -1 ;
		int double_slash_redirecting = hasRedirect(url); 
		int Prefix_Suffix = url.contains("-")? 1 : -1 ;
		int having_Sub_Domain = checkSubDomain(url);
		int SSLfinal_State = checkSSLFinalState();
		int Domain_registeration_length = checkDomainRegisterationLength();
		int Favicon = checkFavicon();
		int port = checkPort();
		int HTTPS_token = checkHttpsToken(url);
		int Request_URL = checkRequestURL();
		int URL_of_Anchor = checkURLAnchor(textFile);
		int Links_in_tags = checkLinksInTags();
		int SFH = checkSFH();
		int Submitting_to_email = checkSubmittingToEmail(textFile);
		int Abnormal_URL = checkAbnormalURL();	
	    int Redirect = checkRedirect(textFile);	    
	    int on_mouseover = checkOnMouseOver(textFile);
	    int RightClick = checkRightClickDisabled(textFile);   
	    int popUpWidnow = checkPopUpWidnow();    
	    int Iframe = checkIframe(textFile);  
	    int age_of_domain = checkAgeOfDomain();
	    int DNSRecord = checkDNSRecord();   
	    int web_traffic = checkWebTraffic();    
	    int Page_Rank = checkPageRank();    
	    int Google_Index = checkGoogleIndex();    
	    int Links_pointing_to_page = checkLinksPointingToPage();    
	    int Statistical_report = checkStatisticalReport(); 
	    System.out.println(having_IP_Address+ "," + URL_Length + ","+Shortining_Service+","+ having_At_Symbol+","+ double_slash_redirecting+","+
	    		 Prefix_Suffix+","+having_Sub_Domain +"," +SSLfinal_State+","+Domain_registeration_length+","+Favicon+","+port+","+HTTPS_token+","+Request_URL+","+URL_of_Anchor+","+Links_in_tags+","+
	    		 SFH+","+Submitting_to_email+","+Abnormal_URL+","+Redirect+","+on_mouseover+","+RightClick+","+popUpWidnow+","+Iframe+","+age_of_domain+","+DNSRecord+","+web_traffic+","+Page_Rank+","+Google_Index+","+
	    		 Links_pointing_to_page+","+ Statistical_report); 
	    
	}
	

	private static int checkStatisticalReport() {
		
		return 1;
	}


	private static int checkLinksPointingToPage() {
	
		return 1;
	}


	private static int checkGoogleIndex() {
	
		return 1;
	}


	private static int checkPageRank() {
	
		return 1;
	}


	private static int checkWebTraffic() {
	
		return 1;
	}


	private static int checkDNSRecord() {

		return 1;
	}


	private static int checkAgeOfDomain() {
		
		return 1;
	}


	private static int checkPopUpWidnow() {

		return 1;
	}


	private static int checkAbnormalURL() {
		return 1;
	}


	private static int checkSFH() {
		return 1;
	}


	private static int checkLinksInTags() {
		return 1;
	}


	private static int checkRequestURL() {
		
		return 1;
	}


	private static int checkPort() {
		
		return 1;
	}


	private static int checkFavicon() {
		return 1;
	}


	private static int checkDomainRegisterationLength() {
	
		return 1;
	}


	private static int checkSSLFinalState() {
		return 1;
	}


	private static int checkURLAnchor(JavaRDD<String> textFile) {
		
		double numberOfAnchor = filterAndCount(textFile,"<a href");
		double hasAAnchor = filterAndCount(textFile,"<a href=\"#\" ");
		double hasContentAnchor = filterAndCount(textFile,"<a href=\"#content\" ");
		double hasSkipAnchor = filterAndCount(textFile,"<a href=\"#skip\" ");
		double hasJavascriptMethod = filterAndCount(textFile,"JavaScript ::void(0)");
		
		if(numberOfAnchor!=0) {
			
			double perURLAnchor = (hasAAnchor/numberOfAnchor)*100 + (hasContentAnchor/numberOfAnchor)*100
					              + (hasSkipAnchor/numberOfAnchor)*100 
				                  + (hasJavascriptMethod/numberOfAnchor)*100 ;
			
			if(perURLAnchor <31) {
				return 1;
			}
			else if (perURLAnchor >=31 && perURLAnchor >=67) {
				return 0;
			}
			else {
				return -1;}
			}

		return 1;
		}
		
	


	private static int checkIframe(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"iframe")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int checkRightClickDisabled(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"onMouseOver")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int checkOnMouseOver(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"event.button==2")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int checkRedirect(JavaRDD<String> textFile) {

		if(filterAndCount(textFile,"window.location")>0 ||filterAndCount(textFile,"<meta http-equiv=\"refresh\" ") >0 ) {
			return -1;
			}
			else
			{
				return 1;	
			}
	}


	private static int checkSubmittingToEmail(JavaRDD<String> textFile) {
		
		if(filterAndCount(textFile,"mail()")>0 ||filterAndCount(textFile,"mailto") >0 ) {
		return -1;
		}
		else
		{
			return 1;	
		}
	}

	private static int checkHttpsToken(String url) {
		String [] parts = url.split("//");
		if(parts.length >=2) {
			if(parts[1].contains("https")) {
				return -1;
			}
		}
		return 1;
	}


	private static int checkSubDomain(String url) {
		for(String s : url.split("/")) {
			if(s.split(".").length>4) {
				return -1;
			}
		}
		return 1 	;
	}


	private static int hasRedirect(String url) {
		String findStr = "hello";
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = url.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findStr.length();
		    }
		}
		if(count > 1) {
			return -1;
		}
		return 1;
	}


	private static int checkTiny(String url) {
		if(url.contains("bit.ly")) {
			return 0;
		}
		return 1;
	}


	private static int checkLen(String url) {
		if(url.length()< 54) {
			return 1;
		}else if(url.length()>=54 && url.length()<=75) {
			return 0;
		}
		return -1;
	}
	
	private static int checkIp(String url) {
		String []urlParts= url.split("/");
		StringBuilder sb =new StringBuilder();
		sb.append(urlParts[0]);
		sb.append(urlParts[1]);
		sb.append(urlParts[2]);
		String subStringOfUrl = sb.toString();
		for(String str : subStringOfUrl.split("/")) {
			String []checkIp = str.split(".");
			if(checkIp.length == 4) {
				for(String ipParts : checkIp) {
					if(ipParts.matches("\\d+"));
					return -1;
				}
			}
		}
		return 1;
		
	}

   private static long filterAndCount(JavaRDD<String> textFile, String pattern) {
	
	 return  textFile.filter(line->line.contains(pattern)).count();
	   
   }
}
