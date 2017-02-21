package javautils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class InifinispanXML {

	public static void main(String[] args) {
		
		File dir = new File("C:/rearch/intellij-workspace/ArchOnline/src/main/java/com/tb/ao/domain/entity");
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		for(File f:filelist) {
			String entityName=StringUtils.stripEnd(f.getName(),".java");
			
			if(!StringUtils.endsWith(entityName,"PK")) {
				System.out.println("<namedCache name=\"com.tb.ao.domain.entity." + entityName + "\"><eviction maxEntries=\"100\" strategy=\"LRU\" /><expiration lifespan=\"60000\" maxIdle=\"10000\"/><jmxStatistics enabled=\"true\"/></namedCache>");
			}
		}
		

	}

}
