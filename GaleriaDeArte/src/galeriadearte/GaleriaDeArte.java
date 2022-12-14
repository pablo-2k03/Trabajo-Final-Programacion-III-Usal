package galeriadearte;
import java.io.IOException;
import static com.coti.tools.DiaUtil.showFinalTime;
import view.View;
/**
 *
 * @author Pablo
 */
public class GaleriaDeArte {

    public static void main(String[] args) throws IOException {
        View v = new View();
        if(v.checkSource()!= 0) { 
            System.out.printf("%nError, el programa no puede iniciar si no existe la carpeta datos_figuras ni se importa un archivo CSV.%n"); 
            return;
        }
        v.runMenu("%n1. Archivos%n2. Gestión de la galería%n3. Listados%n4. Salir%n ==> Elija opcion: ");
        showFinalTime();
    }  
}