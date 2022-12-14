
package data;

import static com.coti.tools.Rutas.*;
import static com.coti.tools.OpMat.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;



public class Model implements Serializable {
    
    private List <Figura> figuras = new ArrayList<>();

    /**
     * @return the figuras
     */
    public List <Figura> getFiguras() {
        return figuras;
    }
    public int getLongitud(){
        return figuras.size();
    }
    /**
     * @param figuras the figuras to set
     */
    public void setFiguras(List <Figura> figuras) {
        this.figuras = figuras;
    }
    
    
    public boolean checkSource(){
        Path src = pathToFileInFolderOnDesktop("datos_figuras","figuras.bin"); 
        if (src.toFile().exists()) {
            try {
                FileInputStream fis = new FileInputStream(src.toFile());
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                figuras = (List<Figura>) ois.readObject();
                return true;
            } catch(IOException | ClassNotFoundException ex) {
                return false;
            }
        } else {
           return false;
        }
    }
    
    public int[] importCSV(){
        
        String[][] dataTable;
        int cont=0,creados=0;
        int[] resultados = new int[2];
        final String delimiter = "\t";
        if(!figuras.isEmpty() ){ figuras.clear(); }
        Path path = pathToFileInFolderOnDesktop("datos_figuras","figuras.csv");
        if(path.toFile().exists()){
            try{
                dataTable = importFromDisk(path.toFile(),delimiter);
                for(String[] s : dataTable){
                    cont++;
                    Figura f = Figura.factory(s);
                    if(f != null){
                        creados++;
                        figuras.add(f);
                    }
                }      
                resultados[0] = cont;
                resultados[1] = creados;
                return resultados;
            }
            catch(Exception e){
                resultados[0] = -2;
                return resultados;
            }   
        }
        resultados[0] = -1;
        return resultados;
    }
    public int exportCSV(){
        final String delimiter = "\t";
        Path path = pathToFileInFolderOnDesktop("datos_figuras","figuras.csv");    
        if(!path.toFile().exists()) { return -1;}
        String[][] matrixToExport = new String[figuras.size()][7];
        int i = 0;
        if(figuras == null){
            return -2;
        }
        else{
            try{
                for (String [] s: matrixToExport){
                    if (i == figuras.size()) { break; }
                        s[0] = figuras.get(i).getIdentificador();
                        s[1] = Double.toString(figuras.get(i).getAltura());
                        s[2] = figuras.get(i).getMaterial();
                        s[3] = Integer.toString(figuras.get(i).getCantidad());
                        s[4] = Integer.toString(figuras.get(i).getAnio());
                        s[5] = figuras.get(i).getFoto();
                        s[6] = figuras.get(i).getFabricante();
                        i++;
                 }
                exportToDisk(matrixToExport,path.toFile(),delimiter);
            }
            catch(Exception e){
                return -3;
            }
        }
        
        return 0;
    }
    
    public int exportHTML(){
        
        Path path = pathToFileInFolderOnDesktop("datos_figuras","figuras.html"); 
        StringBuilder sb = new StringBuilder();        
        try (PrintWriter pw = new PrintWriter(path.toFile())) {
                sb.append("<!DOCTYPE html><html><head><style>#art_galery {  font-family: Arial, Helvetica, sans-serif;  border-collapse: collapse;  width: 100%;}#art_galery td, #art_galery th {  border: 1px solid #ddd;  padding: 8px;}#art_galery tr:nth-child(even){background-color: #f2f2f2;}#art_galery tr:hover {background-color: #ddd;}#art_galery th {  padding-top: 12px;  padding-bottom: 12px;  text-align: left;  background-color: #11E7CD;  color: white;}</style></head><body><h2>Galeria de Arte</h2><table style='width:100%' id='art_galery'><tr><th>Identificador</th><th>Altura</th><th>Material</th><th>Cantidad</th><th>Año</th><th>Foto</th><th>Fabricante</th></tr>");
                for (int i = 0; i < figuras.size(); i++) {
                    sb.append("<tr><td>").append(figuras.get(i).getIdentificador()).append("</td><td>").append(figuras.get(i).getAltura()).append("</td><td>").append(figuras.get(i).getMaterial()).append("</td><td>").append(figuras.get(i).getCantidad()).append("</td><td>").append(figuras.get(i).getAnio()).append("</td><td>").append(figuras.get(i).getFoto()).append("</td><td>").append(figuras.get(i).getFabricante()).append("</td></tr>");         
                }
                sb.append("</table>");
                pw.print(sb.toString());
            }
        catch(Exception e) {
            return -1;
        }
        
        return 0;
    }
    
    private HashMap loadHash(){
        
        HashMap<String, Figura> figurasMapa = new HashMap<>();
        for(int i = 0; i< this.figuras.size();i++){
            figurasMapa.put(this.figuras.get(i).getIdentificador(),this.figuras.get(i));
        }
        return figurasMapa;
    }
    
    
    public int check_id(String idNuevo){  
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(idNuevo)){
            return 1;
        }
        return 0;
    }
    
    public String[] checkFigure(String id){
        String [] resultados = new String[7];
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(id)){
            Figura f = (Figura) figurasMapa.get(id);
            resultados[0] = f.getIdentificador();
            resultados[1] = String.valueOf(f.getAltura());
            resultados[2] = f.getMaterial();
            resultados[3] = String.valueOf(f.getCantidad());
            resultados[4] = String.valueOf(f.getAnio());
            resultados[5] = f.getFoto();
            resultados[6] = f.getFabricante();
            return resultados;
        }
        return null;
    }
    public int storeFigure(String id,double altura,String mat,int cantidad,int anio,String foto,String fabricante){
        try{
            Figura fignueva = new Figura(id,altura,mat,cantidad,anio,foto,fabricante);
            figuras.add(fignueva);
        }
        catch(Exception e) { 
            return 1; 
        }
        return 0;
    }
    public int changeHeight(String[] figura,Double alturaNueva){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setAltura(alturaNueva);
            return 0;
        }
        return 1;
    }
    public int changeMaterial(String[] figura,String materialNuevo){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setMaterial(materialNuevo);
            return 0;
        }
        return 1;
    }
    public int changeQuantity(String[] figura,int cantidad){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setCantidad(cantidad);
            return 0;
        } 
        return 1;
    }
    public int changeYear(String [] figura,int anio){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setAnio(anio);
            return 0;
        }
        return 1;
    }
    public int changePhoto(String[] figura,String foto){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setFoto(foto);
            return 0;
        }
        return 1;
    }
    public int changeFab(String[] figura,String fab){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(figura[0])){
            Figura f = (Figura) figurasMapa.get(figura[0]);
            f.setFabricante(fab);
            return 0;
        }
        return 1;
    }
    public int deleteFigure(String[] tmp){
        var figurasMapa = loadHash();
        if(figurasMapa.containsKey(tmp[0])){
            Figura f = (Figura) figurasMapa.get(tmp[0]);
            figuras.remove(f);
            return 0;
        }
        return 1;
    }
    
    public String[][] sortByIdentifier(){
        var comparer = Comparator.comparing(Figura::getIdentificador);
        Collections.sort(figuras,comparer);
        String[][] dataTable = new String[figuras.size()][7];
        int i = 0;
        for(String[] s: dataTable){
            s[0] = figuras.get(i).getIdentificador();
            s[1] = String.valueOf(figuras.get(i).getAltura());
            s[2] = figuras.get(i).getMaterial();
            s[3] = String.valueOf(figuras.get(i).getCantidad());
            s[4] = String.valueOf(figuras.get(i).getAnio());
            s[5] = figuras.get(i).getFoto();
            s[6] = figuras.get(i).getFabricante();
            i++;
            if(i == figuras.size()) { break;}
        }
        return dataTable;
    }
    
    public String[][] sortByYearAndIdentifier(){
        var c1 = Comparator.comparing(Figura::getAnio).reversed();
        var c2 = c1.thenComparing(Figura::getIdentificador);
        Collections.sort(figuras,c2);
        int i = 0;
        String [][] dataTable = new String[figuras.size()][7];
        for(String[] s: dataTable){
            s[0] = figuras.get(i).getIdentificador();
            s[1] = String.valueOf(figuras.get(i).getAltura());
            s[2] = figuras.get(i).getMaterial();
            s[3] = String.valueOf(figuras.get(i).getCantidad());
            s[4] = String.valueOf(figuras.get(i).getAnio());
            s[5] = figuras.get(i).getFoto();
            s[6] = figuras.get(i).getFabricante();
            i++;
            if(i == figuras.size()) { break;}
        }
        return dataTable;
    }
    
    public String[][] sortByFabAndYear(){
        var c1 = Comparator.comparing(Figura::getFabricante);
        var c2 = c1.thenComparing(Figura::getAnio);
        Collections.sort(figuras,c2);
        int i=0;
        String [][] dataTable = new String[figuras.size()][7];
        for(String[] s: dataTable){
            s[0] = figuras.get(i).getIdentificador();
            s[1] = String.valueOf(figuras.get(i).getAltura());
            s[2] = figuras.get(i).getMaterial();
            s[3] = String.valueOf(figuras.get(i).getCantidad());
            s[4] = String.valueOf(figuras.get(i).getAnio());
            s[5] = figuras.get(i).getFoto();
            s[6] = figuras.get(i).getFabricante().toUpperCase();
            i++;
            if(i == figuras.size()) { break;}
        }
        return dataTable;
    }
    
    public int volcarDatosBinario() throws FileNotFoundException, IOException {   
        try{
            Path binario = pathToFileInFolderOnDesktop("datos_figuras","figuras.bin");
            FileOutputStream fos = new FileOutputStream(binario.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(figuras);
                oos.close();
            }
            catch(Exception e){ return -2;}
            return 0;
        }
        catch(FileNotFoundException e){ return -1;}
    }
}      
