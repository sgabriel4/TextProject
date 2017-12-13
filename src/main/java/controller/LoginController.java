package controller;

import soa.Informatikservice;
import soa.Informatikservice_Service;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Project;
import model.User;
import model.Package;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import persistence.dao.DocumentDao;
import persistence.dao.PackageDao;
import persistence.dao.ProjectDao;
import persistence.dao.UserDao;
import persistence.factory.DAOFactory;
import persistence.factory.TipoBD;

/**
 * Servlet principal de la aplicacion. Encargada de casi todas las posibles vias de entrada al sistema
 * @author Sergio
 */
public class LoginController extends HttpServlet {

    //Link a carpeta donde estan los jsp para vistas.
    private static final String carpetaVista = "/WEB-INF/view/";
    private static DAOFactory fabrica;
    
    ProjectController pjController;
    
    User user;

    Informatikservice_Service client;
    private boolean isMultipart;
    //CARPETA CON LOS ARCHIVOS POR SUBIR AL SERVIDOR SOAP. CORRESPONDE A LINUX
    private final String filePath = "C:\\textconverter/";
    //CARPETA CON LOS ARCHIVOS POR SUBIR AL SERVER SOAP. CORRESPONDE A WINDOWS (TESTING)
    
    //Cantidad maxima del archivo a subir (5MB)
    private final int maxFileSize = 5000 * 1024;
    private final int maxMemSize = 5000 * 1024;
    
    //Variables para subir archivo a SOAP
    private File file;
    private byte[] bytesFile;
    private FileInputStream fis;
    private BufferedInputStream inputStream;

    //Control de logueo
    boolean logueado;

    /**
     * "Constructor" del servlet. Cada vez que se inicia por primera vez, deja en nulo el usuario y el logueado como falso
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        LoginController.fabrica = DAOFactory.getFactory(TipoBD.MYSQL, this.getServletContext());
        pjController = null;
        user = null;
        logueado = false;
    }

    /**
     * Metodo correspondiente a metodo GET.
     * @param request peticion a HTTPServlet
     * @param response respuest a un jsp
     * @throws ServletException Si hay un error relacionado al servlet
     * @throws IOException Otro tipo de errores IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String ruta = request.getServletPath();
        RequestDispatcher dispatcher;
        
        //Variable de sesion actual.
        HttpSession session = request.getSession();
        
        //Segun la ruta por la que se llegue se hace lo siguiente
        switch (ruta) {
            //Login devuelve la vista login.jsp
            case "/login":
                dispatcher = request.getRequestDispatcher(carpetaVista + "login.jsp");
                dispatcher.forward(request, response);
                break;
                
            //register hace uso del metodo register el cual registra un nuevo usuario
            case "/register":
                register(request, response);
                break;
                
            /* getArchivo detecta si hay alguien logueado primero que todo. Luego decide si obtiene el archivo o documento clickeado para mostrar en la web. 
                gracias a la vista ver archivo.
            */
            case "/getArchivo":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    request.setAttribute("archivo", pjController.getTextDocument(request.getParameter("file")));
                    dispatcher = request.getRequestDispatcher("verArchivo.jsp");
                    dispatcher.forward(request, response);
                }
                break;
            //dashboard detecta si hay alguien logeuado. Si es asi entonces le envia a la vista dashboard.jsp todo lo relacionado con el usuario. (arreglo de proyectos)
            case "/dashboard":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    pjController.setDocuments(user);
                    request.setAttribute("user", user);
                    request.setAttribute("projects", user.getProjects());
                    dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard" + ".jsp");
                    dispatcher.forward(request, response);
                }
            default:
                break;
        }

    }

    /**
     * Metodo correspondiente al metodo POST
     *
     * @param request peticion de servlet
     * @param response respuesta de servlet
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String ruta = request.getServletPath();
        //Variable de sesion actual.
        HttpSession session = request.getSession();
        //Segun la ruta por la que se llegue se hace lo siguiente
        switch (ruta) {
            //login. Busca un usuario correspondiente a la sesion pedida. Usa el metodo searchUser
            case "/login":
                searchUser(request, response, session);
                break;
            //saveRegister. Agrega un nuevo usuario al sistema (BD). Hace uso del metodo addUser
            case "/saveRegister":
                addUser(request, response);
                break;
            //upload. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo uploadFile y obtiene el usuario logueado.
            case "/upload":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    uploadFile(request, response, session);
                }
                break;
            //newProject. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo addProject y obtiene el usuario logueado.
            case "/newProject":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    addProject(request, response);
                }
                break;
            //newProject. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo addPackage y obtiene el usuario logueado.
            case "/newPackage":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    addPackage(request, response);
                }
                break;
            //deleteProject. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo deleteProject y obtiene el usuario logueado.
            case "/deleteProject":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    deleteProject(request, response);
                }
                break;
            //deletePackage. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo deletePackage y obtiene el usuario logueado.
            case "/deletePackage":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    deletePackage(request, response);
                }
                break;
            //deleteDocument. Detecta si hay alguien logueado. Si es valido, entonces permite usar el metodo deleteDocument y obtiene el usuario logueado.
            case "/deleteDocument":
                if (detectLogin(request, response, session)) {
                    user = (User)session.getAttribute("usuario");
                    deleteDocument(request, response);
                }
                break;
            //logout. Termina la sesion actual
            case "/logout":
                loguot(request, response, session);
                break;
            default:
                break;
        }
    }

    /**
     * Metodo searchUser, permite buscar un usuario que se envia por post. Si se encuentra, setea la sesion con ese usuario y redirecciona al servlet dashboard.
     * @param request servlet request
     * @param response servlet response
     * @param session variable session
     * @throws ServletException
     * @throws IOException 
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao userDao = LoginController.fabrica.getUserDao();
        user = userDao.search(username, password); //Busca el usuario y lo guarda en la variable user
        if (user != null) { //Si fue encontrado entonces hace el trabajo descrito como metodo.
            session = request.getSession();
            session.setAttribute("usuario", user);
            logueado = true;
            pjController = new ProjectController(fabrica);
            response.sendRedirect("dashboard");
        } else { //Si no lo encuentra lo devuelve al login con un aviso de incorrecto
            request.setAttribute("login", "incorrecto");
            dispatcher = request.getRequestDispatcher(carpetaVista + "login.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Metodo que agrega un usuario a la base de datos. 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        UserDao userDao = LoginController.fabrica.getUserDao();
        User newUser = new User();
        newUser.setUsername(request.getParameter("username"));
        newUser.setPassword(request.getParameter("password"));
        if (!request.getParameter("username").isEmpty() && !request.getParameter("username").isEmpty()) {
            if (userDao.add(newUser)) {
                request.setAttribute("registroValido", "valido");
            } else {
                request.setAttribute("registroValido", "invalido");
            }
        } else {
            request.setAttribute("registroValido", "invalido");
        }
        String ruta = "login.jsp";
        dispatcher = request.getRequestDispatcher(carpetaVista + ruta);
        dispatcher.forward(request, response);
    }

    /**
     * Metodo que devuelve el registrar a la vista login (DEPRECATED)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        request.setAttribute("registrar", "registrar");
        dispatcher = request.getRequestDispatcher(carpetaVista + "login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Metodo que agrega un proyecto al sistema y a la base de datos. Obteniendo el id del usuario actual.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void addProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        ProjectDao projectDao = LoginController.fabrica.getProjectDao();
        Project project = new Project();
        project.setName(request.getParameter("project_name"));
        if (projectDao.add(project, user)) {
            request.setAttribute("creadoValido", "valido");
        } else {
            request.setAttribute("creadoValido", "invalido");
        }
        pjController.setDocuments(user);
        request.setAttribute("user", user);
        request.setAttribute("projects", user.getProjects());
        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Metodo que elimina un proyecto del sistema y de la base de datos. Obteniendo el id del proyecto a eliminar.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void deleteProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher;
        ProjectDao projectDao = LoginController.fabrica.getProjectDao();
        if(projectDao.delete(Integer.parseInt(request.getParameter("project_id1")))){
            request.setAttribute("proyectoEliminado", "valido");
        }else{
            request.setAttribute("proyectoEliminado", "invalido");
        }
        pjController.setDocuments(user);
        request.setAttribute("user", user);
        request.setAttribute("projects", user.getProjects());
        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Metodo que sube un archivo a este proyecto para enviarlo al SOAP. 
     * @param request
     * @param response
     * @param session
     * @throws ServletException
     * @throws IOException 
     */
    private void uploadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(""));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);
        String package_id = "";
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (fi.getSize() > 0) {
                    //Se obtiene un campo del form. Es necesario ya que esta encriptado el form
                    if (fi.isFormField()) {
                        package_id = fi.getString();
                    }
                    //Se obtiene el archivo seleccionado a subir
                    if (!fi.isFormField()) {
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();

                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();

                        // Write the file
                        if (fileName.lastIndexOf("\\") >= 0) {
                            file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                        } else {
                            file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }

                        //Se escribe el archivo al servidor
                        fi.write(file);
                        //Se extrae el texto usando el metodo extractText y dando el archivo como parametro
                        String text = extractText(getTypeFile(fileName), file);
                        //Se sube el documento con el texto obtenido al servidor.
                        pjController.uploadDocument(text, fileName, getTypeFile(fileName), Integer.parseInt(package_id));
                        //Se elimina el documento recien subido (ORIGINAL)
                        file.delete();
                        //Se setean los documentos al usuario (actualizacion de datos)
                        pjController.setDocuments(user);
                        //Se envian las variables a la vista dashboard
                        request.setAttribute("documentoSubido", "valido");
                        request.setAttribute("documentoSubidoName", fileName);
                        request.setAttribute("user", (User)session.getAttribute("usuario"));
                        request.setAttribute("projects",  ((User)session.getAttribute("usuario")).getProjects());
                        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
                        dispatcher.forward(request, response);
                    }
                } else {
                    //Si hubo un error al subir el documento.
                    request.setAttribute("documentoSubido", "invalido");
                    dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Metodo obtiene el tipo de archivo, solo obteniendo el string despues del ultimo punto...
     * @param fileName nombre completo del archivo
     * @return string con el tipo de archivo (extension)
     */
    private String getTypeFile(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
    }

    /**
     * Metodo que extrae el texto del documento enviado por parametro. Este metodo se conecta con el SOAP NPJ SERVICE que hace el trabajo de obtener el texto del documento
     * que uno le explicite. En este caso, se ve de que tipo es y es enviado al metodo correspondiende del SOAP.
     * @param fileType tipo de archivo (extension)
     * @param file archivo a enviar
     * @return texto extraido del archivo
     */
    private String extractText(String fileType, File file) {
        String textExtracted;
        //Se crea el objeto con el SOAP
        client = new Informatikservice_Service();
        //Se obtiene la conexion con el SOAP
        Informatikservice service = client.getInformatikservicePort();
        if (file != null) {
            //Si el documento es PDF. Se conecta al SOAP y se le envia ese documento
            if (fileType.equals("pdf")) {
                try {
                    fis = new FileInputStream(file);
                    inputStream = new BufferedInputStream(fis);
                    bytesFile = new byte[(int) file.length()];
                    inputStream.read(bytesFile);
                    //getTextPDF es el metodo a utilizar en este caso
                    textExtracted = service.getTextPDF(file.getName(), bytesFile);
                    inputStream.close();
                    return textExtracted;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Si el documento es un DOC, DOCX. Se conecta al SOAP y se le envia este documento
            } else if (fileType.equals("doc") || fileType.equals("docx")) {
                try {
                    fis = new FileInputStream(file);
                    inputStream = new BufferedInputStream(fis);
                    bytesFile = new byte[(int) file.length()];
                    inputStream.read(bytesFile);
                    //getTextDoc es el metodo a utilizar en este caso
                    textExtracted = service.getTextDoc(file.getName(), bytesFile);
                    return textExtracted;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Si el documento es un txt, o html. Se conecta al SOAP y se le envia este documento
            } else if(fileType.equals("txt") || fileType.equals("html")){
                try {
                    fis = new FileInputStream(file);
                    inputStream = new BufferedInputStream(fis);
                    bytesFile = new byte[(int) file.length()];
                    inputStream.read(bytesFile);
                    //getTextTXT es el metodo a utilizar en este caso
                    textExtracted = service.getTextTXT(file.getName(), bytesFile);
                    return textExtracted;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                return "ERROR DE ARCHIVO";
            }
        } else {
            System.out.println("NULO");
            return null;
        }
        return null;
    }

    /**
     * Metodo que invalida la sesion actual y redirecion a la pagina de login
     * @param request request Servlet
     * @param response servlet response
     * @param session
     * @throws ServletException
     * @throws IOException 
     */
    private void loguot(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        if (session.getAttribute("usuario") != null) {
            session.invalidate();
            logueado = false;
        }
        request.setAttribute("logueado", "desconectado");
        dispatcher = request.getRequestDispatcher(carpetaVista + "login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Metodo que detecta si hay un usuario logeuado o con sesion activa.
     * @param request request Servlet
     * @param response servlet response
     * @param session variable de sesion
     * @return true si hay sesion activa o falso si no lo hay
     * @throws ServletException
     * @throws IOException 
     */
    private boolean detectLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        if (session == null || !logueado || session.getAttribute("usuario") == null ) {
            RequestDispatcher dispatcher;
            request.setAttribute("logueado", "incorrecto");
            dispatcher = request.getRequestDispatcher(carpetaVista + "login.jsp");
            dispatcher.forward(request, response);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Metodo que agrega un paquete al sistema y a la BD. Crea un nuevo paquete a partir de los parametros del form
     * @param request request Servlet
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    private void addPackage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        PackageDao packageDao = (PackageDao) LoginController.fabrica.getPackageDao();
        Package packag = new Package();
        packag.setName(request.getParameter("package_name"));
        if (packageDao.add(packag, Integer.parseInt(request.getParameter("project_id")))) {
            request.setAttribute("packageCreado", "valido");
        } else {
            request.setAttribute("packageCreado", "invalido");
        }
        pjController.setDocuments(user);
        request.setAttribute("user", user);
        request.setAttribute("projects", user.getProjects());
        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Metodo elimina un paquete del sistema y de la BD. Obteniendo por request el id del paquete a eliminar.
     * @param request request Servlet
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    private void deletePackage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher;
        PackageDao packageDao = LoginController.fabrica.getPackageDao();
        if(packageDao.delete(Integer.parseInt(request.getParameter("package_id1")))){
            request.setAttribute("packageEliminado", "valido");
        }else{
            request.setAttribute("packageEliminado", "invalido");
        }
        pjController.setDocuments(user);
        request.setAttribute("user", user);
        request.setAttribute("projects", user.getProjects());
        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * Metodo elimina un documento del sistema, de la BD y del servidor. Obtienendo por request el id del documento a eliminar.
     * @param request request Servlet
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    private void deleteDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher;
        DocumentDao documentDao = LoginController.fabrica.getDocumentDao();
        if(documentDao.delete(Integer.parseInt(request.getParameter("document_id1")))){
            pjController.deleteDocument(Integer.parseInt(request.getParameter("document_id1")));
            request.setAttribute("documentEliminado", "valido");
        }else{
            request.setAttribute("documentEliminado", "invalido");
        }
        pjController.setDocuments(user);
        request.setAttribute("user", user);
        request.setAttribute("projects", user.getProjects());
        dispatcher = request.getRequestDispatcher(carpetaVista + "dashboard.jsp");
        dispatcher.forward(request, response);
    }

}
