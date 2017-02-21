package javautils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

public class RepositoryGenerator {
	
	public static void main(String args[]) throws Exception{

		File dir = new File("C:/rearch/eclipse/workspace/productsearch/src/main/java/com/tb/ao/domain/entity");
		ArrayList<File> filelist = new ArrayList<File>(FileUtils.listFiles(dir, null, true));
		
		filelist.forEach(f -> generateRepository(f));

		
	}
	
	public static void generateRepository(File file) {
		JavaProjectBuilder builder = new JavaProjectBuilder();
		try {
			builder.addSource(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.exit(0);
		}
		JavaClass class1 = (JavaClass) builder.getClasses().toArray()[0];
		
		String entityName = class1.getName();
		if(!StringUtils.endsWith(entityName, "PK")) {
		String tableName = StringUtils.removeEnd(entityName, "Entity");
		String repoName = "I" + tableName + "Repository";

		String idClass = getIdClass(class1);
		System.out.println("--------");
		System.out.println(entityName+","+idClass+","+tableName+","+repoName);
		
		try {
			createRepo(entityName,idClass,tableName,repoName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exiting at " + idClass);
			System.exit(0);
		}
		
		}
	}
	
	public static String getIdClass(JavaClass class1) {
		boolean idclassExists = false;
		for(JavaAnnotation ja : class1.getAnnotations()) {
			if(ja.getType().getCanonicalName().equalsIgnoreCase("IdClass")) {
				idclassExists = true;
				return StringUtils.removeEnd(
						ja.getProperty("value").getParameterValue().toString()
						,".class");
			}
		}
		
		if(!idclassExists) {
			for(JavaField jf : class1.getFields()) {
				for(JavaAnnotation ja : jf.getAnnotations()) {
					if(ja.getType().getCanonicalName().equalsIgnoreCase("Id")
							|| 
							ja.getType().getCanonicalName().equalsIgnoreCase("javax.persistence.Id")) {
						return jf.getType().getName();
					}
				}
			}
		}
		
		return null;
	}
	

	
	public static void createRepo(String entityName,
			String idClass,
			String tableName,
			String repoName) throws Exception{
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        //ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
       // ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();		
        /*  next, get the Template  */
        Template t = ve.getTemplate( "./src/javautils/repositoryInterface.vm" );
        /*  create a context and add data */
        

        String idClassPack = (StringUtils.endsWith(idClass, "PK"))?
        		"import com.tb.aol.domain.entity." + idClass +";" : "";

        	
        	
        VelocityContext context = new VelocityContext();
        context.put("idclasspack", idClassPack);
        context.put("idclass", idClass);
        
        context.put("table", tableName);
        context.put("fname", repoName);
        context.put("entity", entityName);
        
        
        
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        File file = new File("C:/rearch/temp/"+repoName + ".java");
        
        FileUtils.writeStringToFile(file, writer.toString(), "UTF-8");
        
        		
	}

}
