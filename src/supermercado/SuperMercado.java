package supermercado;
import java.time.LocalDate;
//@author juan6
public class SuperMercado {
    public static void main(String[] args) {
        
        CarneFria carne1 = new CarneFria(240, "Carne molida", "La Canasta", "Carnes Frias", "033", 50, 1, .5, "Cerdo"); 
        Cereal cereal1 = new Cereal(25,"Cereal natural", "Kelloggs","Cereal","A11",23,4,150, "Chocolate");
        Fritura fritura1= new Fritura("Doritos", "Pizza", 350.0, 20.50, "Doritos Pizza", "Sabritas", "Frituras", "044", 80, 15); 
        Lacteo leche1 = new Lacteo(17.20,"Leche", "Lala", "Leche", "022",54,1, "Entera", LocalDate.of(2023, 12, 31));
        Queso queso1 = new Queso(80, "Manchego", "Lala", "Queso", "055", 92, 3, "Lala", LocalDate.of(2023, 12, 31), "Manchego", .5, true); 
        Verduras verdura = new Verduras(2.5, 40, "Jitomate", "El Granjero", "Verduras", "0666", 50, 1); 
        Caja cajaRegistradora = new Caja(); 
        
        cajaRegistradora.agregarProducto(cereal1);
        cajaRegistradora.agregarProducto(leche1);
        cajaRegistradora.agregarProducto(carne1);
        cajaRegistradora.agregarProducto(fritura1);
        cajaRegistradora.agregarProducto(queso1);
        cajaRegistradora.agregarProducto(verdura);
        System.out.println(cajaRegistradora);
    }
    
}
