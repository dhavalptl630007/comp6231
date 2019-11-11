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
		
		long count = textFile.filter(line->line.contains("<a href=\"#\"")).count();
		
		List<String> list = textFile.collect();
		for (String string : list) {
			//System.out.println(string);
		}
		
		System.out.println(count);
		
	}

}
