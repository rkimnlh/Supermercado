package supermercado;
import java.time.LocalDate;
//@author juan6
public class SuperMercado {
    public static void main(String[] args) {
        Cereal miCereal = new Cereal(25,"Cereal natural", "Kelloggs","Cereal",011,23,4,150, "Chocolate");
        
        Lacteo miLeche = new Lacteo(17.20,"Leche", "Lala", "Leche", 022,54,1, "Entera", LocalDate.of(2023, 12, 31));
        
        Caja cajaRegistradora = new Caja(); 
        
        cajaRegistradora.agregarProducto(miCereal);
        cajaRegistradora.agregarProducto(miLeche);
        
        System.out.println(cajaRegistradora);
    }
    
}
