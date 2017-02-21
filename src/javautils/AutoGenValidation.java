package javautils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

public class AutoGenValidation {

	
	public static void main(String[] args) {
		
		File dir = new File("C:/rearch/eclipse/workspace/productsearch/src/main/java/com/tb/ao/domain/entity");
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		filelist.forEach(f -> validateEntity(f));		

	}
	
	public static void validateEntity(File file) {
		
		JavaProjectBuilder builder = new JavaProjectBuilder();
		try {
			builder.addSource(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		JavaClass class1 = (JavaClass) builder.getClasses().toArray()[0];
		
		boolean atDataFound = false;
		boolean idClassFound = false;
		for(JavaAnnotation ja: class1.getAnnotations()) {
			
			if(!atDataFound && checkForLombokData(ja)) {
				atDataFound = true;
				continue;
			}
			
			if(!idClassFound && checkForIdClass(ja)) {
				idClassFound = true;
				continue;
			}			
		}
				
		
		int countIDs = 0;
		for(JavaField jf : class1.getFields()) {
			if(checkIfIdField(jf)) {
				countIDs++;
			}
			
		}
		
		if(countIDs > 1 && !idClassFound) {
			System.out.println(class1.getName() + " --> " + idClassFound);
		}
		
			
		
		
	}
	
	public static boolean checkForLombokData(JavaAnnotation ja) {
		return ja.getType().getCanonicalName().equals("lombok.Data") ? true:false;
	}	
	
	public static boolean checkForIdClass(JavaAnnotation ja) {
		return ja.getType().getName().equals("IdClass") ? true:false;
	}
	
	public static boolean checkIfIdField(JavaField jf) {
		boolean isIDField = false;
		
		for(JavaAnnotation ja : jf.getAnnotations()) {
			if(ja.getType().getName().equals("Id")) {
				isIDField = true;
				break;
			}
		}
		
		return isIDField;
	}

}
