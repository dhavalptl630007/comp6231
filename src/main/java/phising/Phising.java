package phising;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Phising {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("Phising").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> textFile = sc.textFile("C:/Users/Admin/Desktop/html.txt");
		
		
		
		int URL_of_Anchor = hasURLAnchor(textFile);
		int Submitting_to_email = hasSubmittingToEmail(textFile);
	    int Redirect = hasRedirect(textFile);
	    int on_mouseover = hasOnMouseOver(textFile);
	    int RightClick = hasRightClickDisabled(textFile);
	    int Iframe = hasIframe(textFile);
	    
		List<String> list = textFile.collect();
		for (String string : list) {
			//System.out.println(string);
		}
		
		System.out.println(URL_of_Anchor);
		
		String url="http://netflix.hotmaster.dns-cloud.net/clienteseguro/site/index.html";
		int outputHasIp = hasIp(url);
		int outputCheckLength = checkLen(url);
		int outputIsTiny = checkTiny(url);
		int outputHaveAt = url.contains("@")? 1 : -1 ;
		int outputHasRedirect = hasRedirect(url); 
		int outputHasDash = url.contains("-")? 1 : -1 ;
		int outputSubDomain = checkSubDomain(url);
		int outputHasHttps = hasHttps(url);
	}
	

	private static int hasURLAnchor(JavaRDD<String> textFile) {
		
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
		
	


	private static int hasIframe(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"iframe")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int hasRightClickDisabled(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"onMouseOver")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int hasOnMouseOver(JavaRDD<String> textFile) {
		if (filterAndCount(textFile,"event.button==2")>0 ) {
			return -1;
		} else {
			return 1;
		}
	}


	private static int hasRedirect(JavaRDD<String> textFile) {

		if(filterAndCount(textFile,"window.location")>0 ||filterAndCount(textFile,"<meta http-equiv=\"refresh\" ") >0 ) {
			return -1;
			}
			else
			{
				return 1;	
			}
	}


	private static int hasSubmittingToEmail(JavaRDD<String> textFile) {
		
		if(filterAndCount(textFile,"mail()")>0 ||filterAndCount(textFile,"mailto") >0 ) {
		return -1;
		}
		else
		{
			return 1;	
		}
	}

	private static int hasHttps(String url) {
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
	
	private static int hasIp(String url) {
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
