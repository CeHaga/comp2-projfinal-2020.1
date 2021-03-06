package covid.controller.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import covid.controller.data.DataManager;
import covid.enums.StatusCaso;
import covid.models.Medicao;
import covid.models.Pais;

/**
 * 
 * Classe para execu��o de comandos no momento de inicializa��o do servlet
 * 
 * @author Andr� Gaeta
 *
 */

@WebListener
public class ServletStartupListener implements javax.servlet.ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent context) {
		deserializeProjectPath(context);
		DataManager.getDataManager().setDataMap(deserializeData(context));
		DataManager.getDataManager().setMapCountries(deserializeCountryData(context));
	}
	
	/**
	 * Deserializa as informa��es sobre o caminho/path do projeto no computador do usu�rio
	 * @param context contexto(servlet)
	 * @return string com o caminho/path do projeto
	 */
	public String deserializeProjectPath(ServletContextEvent context) {
		InputStream inputStream = context.getServletContext().getResourceAsStream("/WEB-INF/DATA/PROJECT_PATH.txt");
		
		Scanner scanner = new Scanner(inputStream);
		String path = scanner.nextLine();
		scanner.close();
		if(path.isBlank()) {
			System.out.println("Configura��es de exporta��o incorretas. Inicie a main do programa para configurar a API.");
			return null;
		}
		
		DataManager.getDataManager().setProjectPath(path);
		return path;
	}
	/**
	 * Deserializa os dados de medi��es salvos em cache
	 * @param context contexto(servlet)
	 * @return Hashmap com todas as informa��es deserializadas
	 */
	public HashMap<StatusCaso, HashMap<LocalDate, HashMap<String, Medicao>>> deserializeData(ServletContextEvent context) {

		File file = new File(DataManager.getDataManager().projectPath + "/WebContent/WEB-INF/DATA/SERIALIZED_DATA.ser");
	    try (FileInputStream inputStream = new FileInputStream(file); 
	    		ObjectInputStream ois = new ObjectInputStream(inputStream)){
	    	return (HashMap<StatusCaso, HashMap<LocalDate, HashMap<String, Medicao>>>) ois.readObject();
	    } 
	    catch (IOException | ClassNotFoundException e){
	       	System.out.println("Exception when reading obj");
	    	e.printStackTrace();
	        return null;
	    }
	}
	/**
	 * Deserializa todas as informa��es dos pa�ses salvas em cache
	 * @param context contexto(servlet)
	 * @return  Hashmap com as informa��es dos pa�ses
	 */
	public HashMap<String, Pais> deserializeCountryData(ServletContextEvent context) {
		File file = new File(DataManager.getDataManager().projectPath + "/WebContent/WEB-INF/DATA/SERIALIZED_DATA.ser");
	    try (FileInputStream inputStream = new FileInputStream(file); 
	    		ObjectInputStream ois = new ObjectInputStream(inputStream)){
	    	return (HashMap<String, Pais>) ois.readObject();
	    } 
	    catch (IOException | ClassNotFoundException e){
	       	System.out.println("Exception when reading obj");
	    	e.printStackTrace();
	        return null;
	    }
	}
}
