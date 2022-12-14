package controller;
import data.*;
import java.io.IOException;


public class Controller{ 
    
   private final Model m = new Model();
   
    public int volcarDatosBinario() throws IOException{
        if(m.volcarDatosBinario() !=0){
            return 1;
        }
        return 0;
    }
    
    public String[][] sortByIdentifier(){
        return m.sortByIdentifier();
       
    }
    
    public String[][] sortByYearAndIdentifier(){
        return  m.sortByYearAndIdentifier();
    }
    
    public String[][] sortByFabAndYear(){
        return m.sortByFabAndYear();
    }
    
    
    public int check_id(String idNuevo){
        if(m.check_id(idNuevo) == 1){
            return 1;
        }
        return 0;
    }
    
    public int storeFigure(String id, double alt, String mat, int cant, int anio, String foto, String fab){
        if(m.storeFigure(id, alt, mat, cant, anio, foto, fab) == 1){
            return 1;
        }
        return 0;
    } 

    public int deleteFigure(String[] tmp){
        if(m.deleteFigure(tmp) == 1){
            return 1;
        }
        return 0;
    }

    public String[] checkFigure(String tmpid){
        return m.checkFigure(tmpid);
    } 
    

    public int changeHeight(String[] tmpid, double tmpalt){
        if(m.changeHeight(tmpid, tmpalt) == 1){
            return 1;
        }
        return 0;
    }

    public int changeMaterial(String[] tmpid, String tmpmat){
        if(m.changeMaterial(tmpid, tmpmat) == 1){
            return 1;
        }
        return 0;
    }

    public int changeQuantity(String[] tmpid, int tmpcant){
        if(m.changeQuantity(tmpid, tmpcant) == 1){
            return 1;
        }
        return 0;
    }


    public int changeYear(String[] tmpid, int tmpanio){
        if(m.changeYear(tmpid, tmpanio) == 1){
            return 1;
        }
        return 0;
    }


    public int changePhoto(String[] tmpid, String tmpfoto){
        if(m.changePhoto(tmpid, tmpfoto) == 1){
            return 1;
        }
        return 0;
    }

    public int changeFab(String[] tmpid, String tmpfab){
        if(m.changeFab(tmpid, tmpfab) == 1){
            return 1;
        }
        return 0;
    }

    public int[] importCSV(){
        return m.importCSV();
    }

    public int exportCSV(){
        if(m.exportCSV() == 1){
            return 1;
        }
        return 0;
    }

    public int exportHTML(){
        if(m.exportHTML() == 1){
            return 1;
        }
        return 0;
    }

    public boolean checkSource(){
        return m.checkSource() == true;
    }


}