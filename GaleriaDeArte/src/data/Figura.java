
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Figura implements Serializable {
    
    private String identificador;
    private double altura;
    private String material;
    private int cantidad;
    private int anio;
    private String foto;
    private String fabricante;
    
    public static Figura factory(String [] dataRow){
        int longitud = dataRow.length;
        int tmpcant,tmpanio;
        double tmpalt;
        List <String> parsedData = new ArrayList<>();
        if(dataRow.length != 7){
            return null;
        }
        for(int i = 0; i< longitud;i++){
            if(dataRow[i].isEmpty()){
                return null;
            }
            parsedData.add(dataRow[i]);
        }
        try{   
            tmpalt = Double.parseDouble(parsedData.get(1));
            tmpcant = Integer.parseInt(parsedData.get(3));
            tmpanio = Integer.parseInt(parsedData.get(4));
            if(!dataRow[5].toLowerCase().endsWith(".png") || dataRow[5].length() < 5){
                return null;
            }
        }
        catch(NumberFormatException e){
            return null;
        }
        return (new Figura(parsedData.get(0),tmpalt,parsedData.get(2),tmpcant,tmpanio,parsedData.get(5),parsedData.get(6)));
    }
    public Figura(String identificador, double altura, String material, int cantidad, int anio, String foto, String fabricante) {
        this.identificador = identificador;
        this.altura = altura;
        this.material = material;
        this.cantidad = cantidad;
        this.anio = anio;
        this.foto = foto;
        this.fabricante = fabricante;
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    
}
