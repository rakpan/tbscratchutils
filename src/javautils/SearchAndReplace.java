package javautils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class SearchAndReplace {

	public static void main(String[] args) throws Exception{
		
		File dir = new File("C:/rearch/eclipse/workspace/productsearch/src/main/java/com/tollbrothers/archonline/db/repository");
		
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		for(File f : filelist) {
			
			FileUtils.writeStringToFile(f, 
					StringUtils.replace(IOUtils.toString(FileUtils.openInputStream(f), "UTF-8"), 
					"CrudRepository", 
					"PagingAndSortingRepository")
					
					,"UTF-8");
			
			
		}

	}

}
