package javautils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class RepositoryTestCreator {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        VelocityEngine ve = new VelocityEngine();
        ve.init();		
        Template t = ve.getTemplate( "./src/javautils/repository.vm" );
        
        String entity = "ModelNote";
        String fname="I" + entity + "RepositoryTest";
        
        VelocityContext context = new VelocityContext();     
        context.put("fname", fname);
        context.put("entity", entity);        
        
        
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        File file = new File("C:/rearch/temp/"+  fname + ".java");
        
        FileUtils.writeStringToFile(file, writer.toString(), "UTF-8");          
        
        
	}

}
