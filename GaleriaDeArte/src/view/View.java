package view;
import static com.coti.tools.Esdia.*;
import static com.coti.tools.OpMat.printToScreen3;
import controller.Controller;
import java.io.IOException;
/**
 *
 * @author Pablo
 */
public class View {
    Controller c = new Controller();
    
    public void runMenu(String menu) throws IOException{
        boolean salir = false;
        do{
            String option = readString_ne(menu).toLowerCase();
            switch(option){
                case "1" -> this.archives();
                case "2" -> this.galeryManagement();
                case "3" -> this.lists();
                case "4" -> salir= yesOrNo("Desea realmente salir de la aplicación? : ");
            }
        }
        while(!salir);
        
        if(c.volcarDatosBinario()!=0){
            System.out.printf("%nNo se ha podido guardar los datos.%n");
        }
        else{
            System.out.printf("%nVolcando datos a figuras.bin...%n");
        }
    }
    public void archives(){
        boolean salir = false;
        do{   
            String option = readString_ne("%n%na. Importar CSV%nb. Exportar CSV%nc. Exportar HTML%nd. Atrás%n ==> Elija opcion: ").toLowerCase();
            switch(option){
                case "a" -> this.importCSV();          
                case "b" -> this.exportCSV();
                case "c" -> this.exportHTML();          
                case "d" -> salir=true;
            }
        }
        while(!salir);
    }
    public void galeryManagement(){
        boolean salir = false;
        do{
            String option = readString_ne("%n%na. Añadir una figura al inventario.%nb. Consultar datos de una figura.%nc. Modificar datos de una figura.%nd. Eliminar una figura del inventario.%ne. Atras.%n Elija opcion ==> ").toLowerCase();
            switch(option){
                case "a" -> this.addFigure();
                case "b" -> this.searchFigure();
                case "c" -> this.modifyFigure();
                case "d" -> this.deleteFigure();
                case "e" -> salir=true;
            }
        }
        while(!salir);
    }
    public void lists(){
        boolean salir = false;
        do{
            String option = readString_ne("%na. Listado por identificador.%nb. Listado por año e identificador.%nc. Listado por fabricante y año.%nd. Atras%n Elija opcion ==> ").toLowerCase();
            switch(option){
                case "a" -> this.sortByIdentifier();
                case "b" -> this.sortByYearAndIdentifier();
                case "c" -> this.sortByFabAndYear();
                case "d" -> salir=true;
            }
        }
        while(!salir);
    }
    
    public void sortByIdentifier(){
        String[][] data = c.sortByIdentifier();
        try{
            printToScreen3(data);
            
        }
        catch(Exception e){
            System.out.printf("%nNo se pudo sacar el listado.%n");
        }
    }
    
    public void sortByYearAndIdentifier(){
        
        String[][] data = c.sortByYearAndIdentifier();
        try{
            printToScreen3(data);
        }
        catch(Exception e){
            System.err.printf("%nNo se pudo mostrar la tabla.%n");
        }
    }
    
    public void sortByFabAndYear(){
        String[][] data = c.sortByFabAndYear();
        try{
            printToScreen3(data);
        }
        catch(Exception e){
            System.out.println("%nNo se pudo sacar el listado.%n");
        }
    }
    
    public void addFigure(){
        String tmpid,tmpmat,tmpfoto,tmpfab;
        int tmpcant,tmpanio;
        int checkid;
        double tmpalt;
        do{
            tmpid = readString_ne("Introduzca el identificador de la figura: ");
            checkid = c.check_id(tmpid);
            if(checkid == 1) {
                System.out.printf("%nIdentificador no valido, por favor introduzca uno valido.%n%n");
            }
        }
        while(checkid != 0);
        tmpmat = readString_ne("Introduzca el material de la figura: ");
        do{
            tmpfoto = readString_ne("Introduzca la foto de la figura (archivo.png): ");
        }while(!tmpfoto.toLowerCase().endsWith(".png") || tmpfoto.length() < 5);
        tmpfab = readString_ne("Introduzca el fabricante de la figura: ");
        tmpalt = readDouble("Introduzca la altura de la figura: ",0.0,1.5);
        tmpcant = readInt("Introduzca la cantidad de figuras disponibles: ",1,265);
        tmpanio = readInt("Introduzca el año de creacion de la figura: ",0,2100);
        if (c.storeFigure(tmpid,tmpalt,tmpmat,tmpcant,tmpanio,tmpfoto,tmpfab) == 0){
            System.out.printf("%nLa figura se ha añadido correctamente.%n");
        }
        else{
            System.out.printf("%nNo se ha podido añadir la figura a la galeria.%n");
        }
    }
    public String[] searchFigure(){
        try{
            String tmpid = readString_ne("Por favor, introduzca el identificador de la figura: ");
            String[] datosFigura = c.checkFigure(tmpid);
            if(datosFigura != null){
                underline2("Datos de la figura con identificador "+tmpid);
                System.out.printf("%nIdentificador: "+tmpid+ "%nAltura: " +datosFigura[1] + "%nMaterial: "+datosFigura[2] + "%nCantidad: "+datosFigura[3] + "%nAño: "+datosFigura[4]+ "%nFoto: "+datosFigura[5]+"%nFabricante: "+datosFigura[6]+".%n");
                return datosFigura;
            }
            else{
                System.out.printf("Lo sentimos, pero la figura con identificador "+tmpid + " no existe.");
                return null;
            }
        }
        catch(Exception e){ System.out.printf("%nIdentificador no valido.%n"); return null;}
    }
    public void modifyFigure(){
        String[] tmp = this.searchFigure();
        if (tmp != null){     
            boolean salir = false;
            do{
                String option = readString_ne("%nQue dato desea modificar ?%na) Altura.%nb) Material.%nc) Cantidad.%nd) Año.%ne) Foto.%nf) Fabricante.%ng) Salir.%n Elija opcion ==> ").toLowerCase();
                switch(option){
                    case "a" -> {
                        Double tmpalt = readDouble("Introduzca la altura de la figura: ",0.0,1.5);
                        if(c.changeHeight(tmp,tmpalt)==0){
                            System.out.printf("%nAltura cambiada correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio de la altura de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "b" -> {
                        String tmpmat = readString_ne("Introduzca el material de la figura: ");
                        if(c.changeMaterial(tmp,tmpmat ) == 0){
                            System.out.printf("%nMaterial cambiado correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio del material de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "c" -> {
                        int cantidad = readInt("Introduzca la cantidad de figuras disponibles: ",1,265);
                        if(c.changeQuantity(tmp,cantidad)==0){
                            System.out.printf("%nCantidad disponible cambiada correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio de la cantidad de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "d" -> {
                        int tmpanio = readInt("Introduzca el año de creacion de la figura: ",0,2100);
                        if(c.changeYear(tmp,tmpanio)==0){   
                            System.out.printf("%nAnio cambiado correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio del anio de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "e" -> {
                        String tmpfoto = readString_ne("Introduzca la foto de la figura (archivo.png): ");
                        if(c.changePhoto(tmp,tmpfoto)==0){    
                            System.out.printf("%nFoto cambiada correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio de la foto de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "f" -> {
                        String tmpfab = readString_ne("Introduzca el fabricante de la figura: ");
                        if(c.changeFab(tmp,tmpfab)==0){   
                            System.out.printf("%nFabricante cambiado correctamente a la figura con id "+tmp[0]);
                        }
                        else{
                            System.out.printf("%nError en el cambio de fabricante de la figura, no se ha podido cambiar.%n");
                        }
                    }
                    case "g" -> salir=true;
                }
            }
            while(!salir);
        }
    }
    public void deleteFigure(){
        String[] tmp = this.searchFigure();
        if(tmp != null){
            boolean option = yesOrNo("Esta seguro que desea eliminar la figura con identificador "+tmp[0]+ " del inventario? ");
            if(option == true){
                if(c.deleteFigure(tmp)==0){
                System.out.printf("%nLa figura se ha borrado satisfactoriamente del inventario.%n");
                }else{ 
                    System.out.printf("%nNo se ha podido eliminar la figura.%n");
                }
            }
            else{
                System.out.printf("%nLa figura no se ha borrado.%n");
            }
        }
    }
    
    public void importCSV(){
        
        int[] tmp = c.importCSV();
        switch (tmp[0]) {
            case -1 -> System.out.printf("%nEl programa no pudo encontrar el archivo CSV en la carpeta datos_figuras, asegurese de que esté localizada en el escritorio.%n");
            case -2 -> System.out.printf("%nError en la importación de datos, asegurese de que el delimitador establecido es el tabulador.%n");
            default -> System.out.printf("%nLineas leidas: %d %nPorcentaje de objetos creados: %.2f%%",tmp[0],(double)(tmp[1]*100)/tmp[0]);
        }
    }
    
    public void exportCSV(){ 
        int tmp = c.exportCSV();
        switch (tmp) {
            case 0 -> System.out.printf("%nArchivo figuras.csv generado correctamente en la carpeta datos_figuras localizada en el escritorio.%n");
            case -1 -> {
                underline2("%n TIP: Para un uso correcto de exportar, asegurese que hay una carpeta en el Escritorio llamada datos_figuras.");
                System.out.printf("%nNo se ha podido generar el archivo figuras.csv porque la ruta no existe.%n");
            }
            case -2 -> {
                underline2("%n TIP: Para un uso correcto de exportar, asegurese que la lista de figuras no está vacia.");
                System.out.printf("%nNo se ha podido generar el archivo figuras.csv porque no hay figuras que exportar.%n");
            }
            case -3 -> {
                underline2("%n TIP: Para un uso correcto de exportar, asegurese que tiene cerrado el archivo figuras.csv antes de exportarlo.");
                System.out.printf("%nNo se ha podido generar el archivo figuras.csv porque ha habido un error en el parseo de datos.%n");
            }
        }
    }
    public void exportHTML(){
        if(c.exportHTML()==0){
            System.out.printf("%nSe ha generado el archivo figuras.html en la carpeta datos_figuras.%n");
        }
        else{
            System.out.printf("%nNo se ha podido generar el archivo figuras.html.%n");
        }
    }

    public int checkSource(){
        
        System.out.printf("%nIntentando cargar datos de figuras.bin...%n");
        boolean tmp = c.checkSource();
        if(!tmp ){
            boolean option = yesOrNo("Error, el archivo figuras.bin no está en la carpeta datos_figuras, desea importar un archivo CSV? ");
            if(option == true){
                int[] res = c.importCSV();
                    switch (res[0]) {
                        case -1 -> {System.out.printf("%nEl programa no pudo encontrar el archivo CSV en la carpeta datos_figuras, asegurese de que esté localizada en el escritorio.%n"); return 1;}
                        case -2 -> {System.out.printf("%nError en la importación de datos, asegurese de que el delimitador establecido es el tabulador.%n"); return 1;  }
                        default ->{ System.out.printf("%nLineas leidas: %d %nPorcentaje de objetos creados: %.2f%%",res[0],(double)(res[1]*100)/res[0]); return 0;}
                    }
            }
            else{
                return 1;
            }
        }
        else{
            System.out.printf("%nDatos de figuras.bin cargados correctamente.%n");
            return 0;
        }
    } 
}