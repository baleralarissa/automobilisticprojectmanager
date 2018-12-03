package Conexões;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Classes.Projeto;
import java.sql.SQLException;
/**
 *
 * @author Larissa Balera & Luiz Felipe Falci   '
 */
public class CRUD {
    
    public void gravaProjeto(Projeto dado) throws SQLException {
        
        PreparedStatement pstmt = null;
                   
        Connection conn = Conexao.getConnection();
        
        try{            
            String sql = "insert into projeto (id_projeto, tipoProj, modelagem2d, "
                    + " modelagem3d, valor, dataEntrega, dataPagamento, detalhePag, softAUTOCAD,"
                    + " softSOLID, nomeResp, nomeEmp, contato, email, detalheProj, nomeProjeto) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, dado.getId());
            pstmt.setBoolean(2, dado.getTipoProj());
            pstmt.setBoolean(3, dado.getModelagem2d());
            pstmt.setBoolean(4, dado.getModelagem3d());
            //pstmt.setFloat(5, dado.getValor());
           // pstmt.setInt(6, dado.getDataEntrega());
           // pstmt.setInt(7, dado.getDataPagamento());
            pstmt.setString(8, dado.getDetalhePag());
            pstmt.setBoolean(9, dado.getSoftAUTOCAD());
            pstmt.setBoolean(10, dado.getSoftSOLID());
            pstmt.setString(11, dado.getNomeResp());
            pstmt.setString(12, dado.getNomeEmp());
            //pstmt.setInt(13, dado.getContato());
            pstmt.setString(14, dado.getEmail());
            pstmt.setString(15, dado.getDetalheProj());
            pstmt.setString(16, dado.getNomeProjeto());
            pstmt.execute();
            
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
    }
    
    public Projeto encontrar(Projeto dados){
        
        PreparedStatement pstmt = null;
        Projeto aux = new Projeto();
        String sql = "'SELECT * FROM projeto WHERE id = " + dados.getId() + "'";
        
        try(Connection conn = Conexao.getConnection()) 
        {  
            pstmt = conn.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                aux.setId(rs.getInt("id"));
                aux.setTipoProj(rs.getBoolean("tipoProj"));
                aux.setModelagem2d(rs.getBoolean("modelagem2d"));
                aux.setModelagem3d(rs.getBoolean("modelagem3d"));
                //aux.setValor(rs.getFloat("valor"));  
             //   aux.setDataEntrega(rs.getInt("dataEntrega"));
//                aux.setDataPagamento(rs.getInt("dataPagamento"));
                aux.setDetalhePag(rs.getString("detalhePag"));
                aux.setSoftAUTOCAD(rs.getBoolean("softAUTOCAD"));
                aux.setSoftSOLID(rs.getBoolean("softSOLID"));
                aux.setNomeResp(rs.getString("nomeResp"));
                aux.setNomeEmp(rs.getString("nomeEmp"));
                aux.setContato(rs.getInt("Contato"));
                aux.setEmail(rs.getString("email"));
                aux.setDetalheProj(rs.getString("detalheProj"));
                aux.setNomeProjeto(rs.getString("nomeProjeto"));
            }
            
            conn.close();
            
            
        } catch(SQLException emf){
            emf.printStackTrace();
        }
        
        return aux;       
    }
    
    public Projeto deletar(Projeto dados) {

        Projeto aux = new Projeto();
        
        Connection conn = Conexao.getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM projeto WHERE id = " + dados.getId());
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!!!");
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return aux;

}
    public boolean atualizar(Projeto dado) {
        boolean retorno = false;
        String sql = "UPDATE projeto SET tipoProj=?, modelagem2d=?, modelagem3d=?, valor=?, "
                + "dataEntrega=?, dataPagamento=?, detalhePaga=?, softAUTOCAD=?, softSOLID=?, "
                + "nomeResp=?, nomeEmp=?, contato=?, email=?,"
                + "detalhesProj=?, nomeProjeto=? WHERE id=?";
        PreparedStatement pstmt;
        try(Connection conn = Conexao.getConnection())
        {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, dado.getId());
            pstmt.setBoolean(2, dado.getTipoProj());
            pstmt.setBoolean(3, dado.getModelagem2d());
            pstmt.setBoolean(4, dado.getModelagem3d());
//            pstmt.setFloat(5, dado.getValor());
//            pstmt.setInt(6, dado.getDataEntrega());
         //   pstmt.setInt(7, dado.getDataPagamento());
            pstmt.setString(8, dado.getDetalhePag());
            pstmt.setBoolean(9, dado.getSoftAUTOCAD());
            pstmt.setBoolean(10, dado.getSoftSOLID());
            pstmt.setString(11, dado.getNomeResp());
            pstmt.setString(12, dado.getNomeEmp());
 //           pstmt.setInt(13, dado.getContato());
            pstmt.setString(14, dado.getEmail());
            pstmt.setString(15, dado.getDetalheProj());
            pstmt.setString(16, dado.getNomeProjeto());
            pstmt.executeUpdate();
            retorno = true;
            conn.close();
            
        } catch (SQLException ex) {
            retorno = false;
            ex.printStackTrace();
        }
          return retorno;

    
}
}