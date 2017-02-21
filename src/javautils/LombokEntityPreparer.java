package javautils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;

public class LombokEntityPreparer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		JavaProjectBuilder builder = new JavaProjectBuilder();
		builder.addSource(new File("C:/rearch/eclipse/workspace/productsearch/src/main/java/com/tb/ao/domain/entity/CommunityModelElevationEntity.java"));
		
		JavaClass class1 = (JavaClass) builder.getClasses().toArray()[0];
		//class1.getFields().forEach(f ->  System.out.println(f.getAnnotations()));
		String fname = class1.getName();
		String data = "";
		String classAnnotations = stringifyAnnotations(class1.getAnnotations());
		
        VelocityEngine ve = new VelocityEngine();
        ve.init();		
        Template t = ve.getTemplate( "./src/javautils/lombokEntity.vm" );
        
        VelocityContext context = new VelocityContext();
        context.put("classAnnotations", classAnnotations);        
        context.put("fname", fname);
        
        context.put("data", data);

        
        
        
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        File file = new File("C:/rearch/temp/"+  fname + ".java");
        
        FileUtils.writeStringToFile(file, writer.toString(), "UTF-8");        		

	}
	
	public static String stringifyAnnotations(List<JavaAnnotation> list) {
		StringBuffer returnVals = new StringBuffer();
		list.forEach(
				i -> (
						returnVals.append(stripEmpty(i.toString()))
						)
				);
		
		return returnVals.toString();
	}
	
	public static String stripEmpty(String val) {
		return StringUtils.removeEnd(val, "()") + "\n";
	}

}
