package Links;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author AA2020
 */
public class ConectionSQL {
    Connection conexion;
    
    public void ConnectDataBase(){
        try {
            Class.forName("org.postgresql.Driver") ;
              conexion = DriverManager.getConnection(
                      "jdbc:postgresql://127.0.0.1:5432/la_neveria", "postgres", "laam");
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Recurso No Encontrado 404: No funciona la CONEXION con la BASE DE DATOS");
        }
    }
    
    public void InsertarTipoHelado(String tipoHelado, String descripcionHelado){
        try {
            String query = "INSERT INTO helados (tipo_helado, descripcion_helado)" + " VALUES (?, ?)"; 
            
            PreparedStatement pst = conexion.prepareStatement(query);
            
            pst.setString(1, tipoHelado);
            pst.setString(2, descripcionHelado);
            
            pst.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void InsertarProducto(String nombreProducto, double precioProducto, int stockProducto, String descripcionProducto){
        try {
            String query = "INSERT INTO productos (nombre_producto, precio_producto, stock_producto, descripcion_producto)" + " VALUES (?, ?, ?, ?)"; 
            
            PreparedStatement pst = conexion.prepareStatement(query);
            
            pst.setString(1, nombreProducto);
            pst.setDouble(2, precioProducto);
            pst.setInt(3, stockProducto);
            pst.setString(4, descripcionProducto);
            
            pst.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void UpdateHeladosType(DefaultTableModel modelHelado, JTable tablaHelado){
        try {
            Statement stmt = this.conexion.createStatement();
            String query = "SELECT * FROM helados ORDER BY id_helado;" ;
            ResultSet rs = stmt.executeQuery(query);
            
            modelHelado = (DefaultTableModel) tablaHelado.getModel();
            modelHelado.setRowCount(0);
            Object Registro[] = new Object[4];
            
            while (rs.next())
            {
                for (int i=0;i<3;i++) Registro[i]=rs.getObject(i+1).toString().trim();
              modelHelado.addRow(Registro);
            }
            tablaHelado.setModel(modelHelado);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void UpdateProducto(DefaultTableModel modelProducto, JTable TablaProducto){
        try {
            Statement stmt = this.conexion.createStatement();
            String query = "SELECT * FROM productos ORDER BY id_producto;" ;
            ResultSet rs = stmt.executeQuery(query);
            
            modelProducto = (DefaultTableModel) TablaProducto.getModel();
            modelProducto.setRowCount(0);
            Object Registro[] = new Object[6];
            
            while (rs.next())
            {
                for (int i=0;i<5;i++) Registro[i]=rs.getObject(i+1).toString().trim();
              modelProducto.addRow(Registro);
            }
            TablaProducto.setModel(modelProducto);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void ActualizarTipoHelado(JTable TablaTipoHelado, String tipoHelado, String descripcionHelado, int IDHelado){
        int Indice= TablaTipoHelado.getSelectedRow();
        if(Indice>-1)
        {
            try
            {
                String query = "UPDATE helados SET tipo_helado=?, descripcion_helado=?"
                        + " WHERE id_helado="+IDHelado;
                                
                PreparedStatement pst = conexion.prepareStatement(query);
               
                pst.setString(1, tipoHelado);
                pst.setString(2, descripcionHelado);
                                          
                pst.execute();
      
            }
            catch(Exception e) 
            {
                 JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE); 
            }
        }
        else JOptionPane.showMessageDialog(null,"Mala Petición Realizada 400: Debe SELECCIONAR un dato de la tabla" + JOptionPane.WARNING_MESSAGE);     
    }
    
    public void ActualizarProducto(JTable TablaProducto, String nombreProducto, double precioProducto, int stockProducto, String descripcionProducto, int IDProducto){
        int Indice= TablaProducto.getSelectedRow();
        if(Indice>-1)
        {
            try
            {
                String query = "UPDATE productos SET nombre_producto=?, precio_producto=?, stock_producto=?, descripcion_producto=?"
                        + " WHERE id_producto="+ ((DefaultTableModel) TablaProducto.getModel()).getValueAt(Indice, 0).toString();
                                
                PreparedStatement pst = conexion.prepareStatement(query);
               
                pst.setString(1, nombreProducto);
                pst.setDouble(2, precioProducto);
                pst.setInt(3, stockProducto);
                pst.setString(4, descripcionProducto);
                                          
                pst.execute();
      
            }
            catch(Exception e) 
            {
                 JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE); 
            }
        }
        else JOptionPane.showMessageDialog(null,"Mala Petición Realizada 400: Debe SELECCIONAR un dato de la tabla" + JOptionPane.WARNING_MESSAGE);     
    }
    
    public void EliminarTipoHelado(int ID){
        try
            {
                Statement stmt = this.conexion.createStatement();
                String query = "DELETE FROM helados WHERE id_helado="+ID;
                ResultSet rs = stmt.executeQuery(query) ; 
            }
        catch(Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Petición Correcta 200: OK, el registro HA SIDO ELIMINADO");  
        }
    }
    
    public void EliminarProducto(int ID){
        try
            {
                Statement stmt = this.conexion.createStatement();
                String query = "DELETE FROM productos WHERE id_producto="+ID;
                ResultSet rs = stmt.executeQuery(query) ; 
            }
        catch(Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Petición Correcta 200: OK, el registro HA SIDO ELIMINADO");  
        }
    }
}
