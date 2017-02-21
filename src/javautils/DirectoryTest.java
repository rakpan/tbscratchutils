package javautils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class DirectoryTest {
	
	public static void main(String args[]) throws Exception{

		File dir = new File("C:/rearch/intellij-workspace/ArchOnline/client/src/js/components/common/services");
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		filelist.forEach(f -> {
			String text =
                    "require('./components/common/services/" + f.getName() + "');";
			System.out.println(text);
		});

		
	}

}
