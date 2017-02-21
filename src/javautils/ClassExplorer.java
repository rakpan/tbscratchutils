package javautils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

/*
 * Program reads a folder, opens all java classes and strips of the method declaration in it after adding Lombok's @Data annotation.
 */
public class ClassExplorer {
	
	StringBuffer sb;

	public static void main(String[] args) throws Exception {
		ClassExplorer ce = new ClassExplorer();
		
		File dir = new File("C:/rearch/eclipse/workspace/productsearch/src/main/java/com/tb/ao/domain/entity");
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		
		
		
		
		filelist.forEach(f -> ce.printClassStructure(f));


	}
	
	public void loopThruClasses() {
		
	}
	
	public void printClassStructure(File file)  {
		JavaProjectBuilder builder = new JavaProjectBuilder();
		try {
			builder.addSource(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		JavaClass class1 = (JavaClass) builder.getClasses().toArray()[0];
		
		p(class1.getPackage()+";");
		p("");
		printComments();
		p("");
		p("import javax.persistence.*;");
		p("import java.io.Serializable;");
		p("import lombok.Data;");
		p("");
		p("");
		p("@Data");
		p("@SuppressWarnings(\"serial\")");
		printAnnotations(class1.getAnnotations());
		p("public class " + class1.getName() + " implements Serializable {");
		p("");
		p("");
		printColumns(class1.getFields());
		p("");
		p("}");
		
		File ofile = new File("C:/rearch/temp/"  + class1.getName() + ".java");
		try {
			FileUtils.writeStringToFile(ofile, sb.toString(), "UTF-8");
			sb = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
	}
	
	public void printAnnotations(List<JavaAnnotation> list) {
		list.forEach(v -> p(stripBraces(v)));
	}
	
	public String stripBraces(JavaAnnotation ja) {
		return StringUtils.removeEnd(ja.toString(), "()");
	}
	
	public void p(Object o) {
		//System.out.println(o);
		if(sb==null) {
			sb = new StringBuffer();
		}
		sb.append(o.toString() + "\n");
	}
	
	public void printComments() {
		p("/**");
		p("* Created by Rakesh Panati");
		p("*/");
	}
	
	public void printColumns(List<JavaField> list) {
		list.forEach(p -> printColumn(p));
	}
	
	public void printColumn(JavaField jf) {
		p(jf.getCodeBlock());
	}

}
