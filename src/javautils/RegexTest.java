package javautils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
		
		
		String table = "com.tollbrothers.archonline.db.entity.base.CommunityLotEntityPK.java";
		
    	Pattern pattern = Pattern.compile("(([a-zA-Z]+)[.])+");
    	Matcher matcher = pattern.matcher(table);
    	if (matcher.find())
    	{
    		System.out.println(matcher.groupCount());
    		System.out.println(matcher.group(0));
    		System.out.println(matcher.group(1));
    		System.out.println(matcher.group(2));
    		System.out.println(matcher.groupCount());
    	}  
	}

}

