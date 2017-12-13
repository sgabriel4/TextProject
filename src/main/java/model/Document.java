package model;

/**
 * Clase Document referencia al documento o archivo a obtener texto
 * @author Sergio
 */

public class Document {

    
    private int id;
    private String showName;
    private String fileName;
    private String textContent;
    private String type;

    public Document() {
    }

    public Document(String showName, String fileName, String type) {
        this.showName = showName;
        this.fileName = fileName;
        this.type = type;
    }

    public Document(String showName, String fileName, String type, String textContent) {
        this.showName = showName;
        this.fileName = fileName;
        this.textContent = textContent;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
