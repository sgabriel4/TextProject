package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Document;
import model.Project;
import model.User;
import model.Package;
import persistence.dao.DocumentDao;
import persistence.dao.PackageDao;
import persistence.dao.ProjectDao;
import persistence.factory.DAOFactory;

/**
 * Controlador para proyectos de usuario
 * @author Sergio
 */
public class ProjectController {

    private ProjectDao projectDao;
    private PackageDao packageDao;
    private DocumentDao documentDao;
    
    ArrayList<Project> projects;

    /**
     * Consutrctor que instancia los DAO a utilizar.
     * @param fabrica 
     */
    public ProjectController(DAOFactory fabrica) {
        projectDao = fabrica.getProjectDao();
        packageDao = fabrica.getPackageDao();
        documentDao = fabrica.getDocumentDao();

    }

    /**
     * Metodo que setea los documentos obtenidos de una BD al Usuario ingresado
     * @param user  USuario a setear documentos
     */
    public void setDocuments(User user) {
        projects = projectDao.list(user);
        for (int i = 0; i < projects.size(); i++) {
            projects.get(i).setPackages(packageDao.list(projects.get(i)));
            for (int j = 0; j < projects.get(i).getPackages().size(); j++) {
                projects.get(i).getPackages().get(j).setDocuments(documentDao.list(projects.get(i).getPackages().get(j)));
            }
        }
        user.setProjects(projects);
    }

    /**
     * Metodo que obtiene el texto del documento en el servidor. Lo obtiene desde el metodo extractTextFile y lo setea en el objeto al encontrarlo en el usuario
     * @param documentFileName nombre del documento a buscar el texto
     * @return texto del documento pedido
     */
    public String getTextDocument(String documentFileName) {
        for (int i = 0; i < projects.size(); i++) {
            for (int j = 0; j < projects.get(i).getPackages().size(); j++) {
                for (int k = 0; k < projects.get(i).getPackages().get(j).getDocuments().size(); k++) {
                    if (projects.get(i).getPackages().get(j).getDocuments().get(k).getFileName().equals(documentFileName)) {
                        if (projects.get(i).getPackages().get(j).getDocuments().get(k).getTextContent() == null) {
                            try {
                                projects.get(i).getPackages().get(j).getDocuments().get(k).
                                        setTextContent(extractTextFile(projects.get(i).getPackages().get(j).getDocuments().get(k).getFileName()));
                            } catch (IOException ex) {
                                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        return projects.get(i).getPackages().get(j).getDocuments().get(k).getTextContent();
                    }
                }
            }
        }
        return "error";
    }

    /**
     * Metodo extrae el texto del archivo que esta en el servidor.
     * @param archivo nombre del archivo a extraer el texto
     * @return texto del archivo
     * @throws FileNotFoundException
     * @throws IOException 
     */
    String extractTextFile(String archivo) throws FileNotFoundException, IOException {
        String text, textFull = "";
        FileReader f;
        //Se prueba si es un linux o windows (TESTING)
        if (System.getenv("SystemDrive") == null) {
            f = new FileReader("/home/tomcat/files/" + archivo);
        } else {
            f = new FileReader("C:\\textconverter/" + archivo);
        }
        //Se obtiene el texto y se formatea para mostrar.
        BufferedReader b = new BufferedReader(f);
        while ((text = b.readLine()) != null) {
            textFull = textFull + text;
            textFull = textFull + "<br>";
        }
        b.close();
        return textFull;
    }

    /**
     * Metodo agrega un paquete a la base de datos (DEPRECATED)
     * @param pack paquete, id paquete
     * @param project_id
     * @return true si se guardo.
     */
    public boolean addPackage(Package pack, int project_id) {
        return packageDao.add(pack, project_id);
    }

    /**
     * Metodo para subir un documento al SERVIDOR DE PROYECTOS. Este archivo tiene solo el texto extraido.
     * @param text texto extraido desde SOAP
     * @param nameFile nombre del archivo con el que se mostrara en la vista.
     * @param fileType tipo del archivo
     * @param package_id id del paquete a asociar
     * @return true si se guarda correctamente.
     */
    public boolean uploadDocument(String text, String nameFile, String fileType, int package_id) {
        FileWriter writer;
        Document document;
        String nameFileArchivo="archivo";
        String tempName="archivo";
        int num = 1;
        try {
            if (System.getenv("SystemDrive") == null) {
                //Si es linux
                File file = new File("/home/tomcat/files/"+nameFileArchivo);
                //Comprueba si el archivo ya existe, se le va agregando un index para que este no se remplace
                while (file.exists()) {
                    tempName = nameFileArchivo+(num++);
                    file = new File("/home/tomcat/files/"+tempName);
                }
                writer = new FileWriter("/home/tomcat/files/" + tempName, true);
            } else {
                //Si es windows (TESTING)
                File file = new File("C:\\textconverter/"+nameFileArchivo);
                //Comprueba si el archivo ya existe, se le va agregando un index para que este no se remplace
                while (file.exists()) {
                    tempName = nameFileArchivo+(num++);
                    file = new File("C:\\textconverter/"+tempName);
                }
                writer = new FileWriter("C:\\textconverter/" + tempName, true);
            }
            //Se guarda el archivo y se guarda en el sistema y BD
            writer.write(text);
            document = new Document();
            document.setShowName(nameFile);
            document.setTextContent(text);
            document.setFileName(tempName);
            document.setType(fileType);
            writer.close();
            return documentDao.add(document, package_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Meodo que elimina el documento con la ID obtenida. Lo elimina de la BD y del sistema PROYECTOS (archivo con texto)
     * @param id 
     */
    public void deleteDocument(int id){
        for (int i = 0; i < projects.size(); i++) {
            for (int j = 0; j < projects.get(i).getPackages().size(); j++) {
                for (int k = 0; k < projects.get(i).getPackages().get(j).getDocuments().size(); k++) {
                    if(projects.get(i).getPackages().get(j).getDocuments().get(k).getId() == id){
                        String fileName=projects.get(i).getPackages().get(j).getDocuments().get(k).getFileName();
                        File file = new File("/home/tomcat/files/"+fileName);
                        file.delete();
                    }
                }
            }
        }
    }
}
