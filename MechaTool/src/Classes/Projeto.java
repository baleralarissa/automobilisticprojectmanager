package Classes;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  Classe feita para criar objetos do tipo Projeto para consulta do mesmo em
 *  tempo real. 
 */
public class Projeto {
    
    private int idprojeto;
    private boolean tipoProj; 
    private boolean modelagem2d;
    private boolean modelagem3d;
    private long valor;
    private String dataEntrega;
    private String dataPagamento;
    private String detalhePag;
    private boolean softAUTOCAD;
    private boolean softSOLID;
    private String nomeResp;
    private String nomeEmp;
    private long contato;
    private String email;
    private String detalheProj;
    private String nomeProjeto;
    private boolean tipoProj1;
    
       
    public int getId(){
        return idprojeto;
    }
    
    public void setId(int idprojeto){
        this.idprojeto = idprojeto;
    }
    
    public boolean getTipoProj(){
        return tipoProj;
    }
    
    public void setTipoProj(boolean tipoProj){
        this.tipoProj = tipoProj;
    }
    
    public boolean getModelagem2d(){
        return modelagem2d;
    }
    
    public void setModelagem2d(boolean modelagem2d){
        this.modelagem2d = modelagem2d;
    }
    public boolean getModelagem3d(){
        return modelagem3d;
    }
    
    public void setModelagem3d(boolean modelagem3d){
        this.modelagem3d = modelagem3d;
    }
    
    public long getValor(){
        return valor;
    }
    
    public void setValor(long valor){
        this.valor = valor;
    }
    
    public String getDataEntrega(){
        return dataEntrega;
    }
    
    public void setDataEntrega(String dataEntrega){
        this.dataEntrega = dataEntrega;
    }
    
    public String getDataPagamento(){
        return dataPagamento;
    }
    
    public void setDataPagamento(String dataPagamento){
        this.dataPagamento = dataPagamento;
    }
    
    public String getDetalhePag(){
        return detalhePag;
    }
    
    public void setDetalhePag(String detalhePag){
        this.detalhePag = detalhePag;
    }
    
    public boolean getSoftAUTOCAD(){
        return softAUTOCAD;
    }
    
    public void setSoftAUTOCAD(boolean softAUTOCAD){
        this.softAUTOCAD = softAUTOCAD;
    }
    
    public boolean getSoftSOLID(){
        return softSOLID;
    }
    
    public void setSoftSOLID(boolean softSOLID){
        this.softSOLID = softSOLID;
    }
    
    public String getNomeResp(){
        return nomeResp;
    }
    
    public void setNomeResp(String nome_resp){
        this.nomeResp = nome_resp;
    }
    
    public String getNomeEmp(){
        return nomeEmp;
    }
    
    public void setNomeEmp(String nome_emp){
        this.nomeEmp = nome_emp;
    }
    
    public long getContato(){
        return contato;
    }
    
    public void setContato(long contato){
        this.contato = contato;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getDetalheProj(){
        return detalheProj;
    }
    
    public void setDetalheProj(String detalheProj){
        this.detalheProj = detalheProj;
    }
    
    public String getNomeProjeto(){
        return nomeProjeto;
    }
    
    public void setNomeProjeto(String nomeProjeto){
        this.nomeProjeto = nomeProjeto;
    }
    public boolean getTipoProj1(){
        return tipoProj1;
    }
    
    public void setTipoProj1(boolean tipoProj1){
        this.tipoProj1 = tipoProj1;
    }
}