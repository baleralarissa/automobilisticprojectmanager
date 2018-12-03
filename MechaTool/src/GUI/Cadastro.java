/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.*;
import Conexões.Conexao;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.text.DateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Larissa
 */
public class Cadastro extends javax.swing.JFrame {

    /**
     * Creates new form Cadastro
     */
    private final Projeto dados = new Projeto();

    public static final int EDITAR = 0;
    public static final int CADASTRAR = 1;

    public Cadastro(int tipo) {
        this.setResizable(false);
        initComponents();
        this.setLocationRelativeTo(null);

        jButton1.setEnabled(tipo == CADASTRAR);
        jButton3.setEnabled(tipo == EDITAR);

    }

    public void recebeId(String r) {
        int t = Integer.parseInt(r);
        dados.setId(t);
    }

    public void salvar() {

        String sql = "INSERT INTO projeto (tipoProj, modelagem2d, "
                + " modelagem3d, valor, dataEntrega, dataPagamento, detalhePag, softAUTOCAD,"
                + " softSOLID, nomeResp, nomeEmp, contato, email, detalhesProj, nomeProjeto, tipoProj1) VALUES"
                + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); //até a linha do return verificando se a data é valida
        df.setLenient(false);
        try {
            df.parse(jFormattedTextField1.getText());
            df.parse(jFormattedTextField4.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Data Inválida");
            return;
        }
        
        boolean email = jTextField5.getText().matches("[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$");//Verifica se o email bate com a Expressão Regular

        if (jTextField1.getText().trim().equals("") || jTextField2.getText().trim().equals("")
                || jTextField3.getText().trim().equals("") || jTextField4.getText().trim().equals("")
                || jTextField5.getText().trim().equals("") || jFormattedTextField1.getText().trim().equals("")
                || jFormattedTextField2.getText().trim().equals("") || jFormattedTextField4.getText().trim().equals("")) { //verificando se os campos estão vazios

            JOptionPane.showMessageDialog(null, "Você esqueceu de inserir algum dado");

        } else if (email == false) {
            JOptionPane.showMessageDialog(null, "O email inserido não é válido");
        } else {
            try (Connection conn = Conexao.getConnection()) {

                dados.setTipoProj(jCheckBox5.isSelected());
                dados.setModelagem2d(jCheckBox1.isSelected());
                dados.setModelagem3d(jCheckBox2.isSelected());
                dados.setValor(Long.parseLong(jTextField4.getText()));
                dados.setDataEntrega(jFormattedTextField1.getText());
                dados.setDataPagamento(jFormattedTextField4.getText());
                dados.setDetalhePag(jTextArea2.getText());
                dados.setSoftAUTOCAD(jCheckBox3.isSelected());
                dados.setSoftSOLID(jCheckBox4.isSelected());
                dados.setNomeResp(jTextField1.getText());
                dados.setNomeEmp(jTextField2.getText());
                dados.setContato(Long.parseLong(jFormattedTextField2.getText()));
                dados.setEmail(jTextField5.getText());
                dados.setDetalheProj(jTextArea1.getText());
                dados.setNomeProjeto(jTextField3.getText());
                dados.setTipoProj1(jCheckBox6.isSelected());

                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setBoolean(1, dados.getTipoProj());
                stmt.setBoolean(2, dados.getModelagem2d());
                stmt.setBoolean(3, dados.getModelagem3d());
                stmt.setLong(4, dados.getValor());
                stmt.setString(5, dados.getDataEntrega());
                stmt.setString(6, dados.getDataPagamento());
                stmt.setString(7, dados.getDetalhePag());
                stmt.setBoolean(8, dados.getSoftAUTOCAD());
                stmt.setBoolean(9, dados.getSoftSOLID());
                stmt.setString(10, dados.getNomeResp());
                stmt.setString(11, dados.getNomeEmp());
                stmt.setDouble(12, dados.getContato());
                stmt.setString(13, dados.getEmail());
                stmt.setString(14, dados.getDetalheProj());
                stmt.setString(15, dados.getNomeProjeto());
                stmt.setBoolean(16, dados.getTipoProj1());
                stmt.executeUpdate();

                conn.close();
                stmt.close();
                JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
                TelaPrincipal t = new TelaPrincipal();
                t.setVisible(true);
                dispose();
            } catch (SQLException e) {
                System.out.println("ERRO " + e);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Insira um número válido");
            }
        }
    }

    public void pegaDados() {

        String sql = "SELECT * FROM projeto WHERE idprojeto = '" + dados.getId() + "'";

        try (Connection conn = Conexao.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                dados.setId(rs.getInt("idprojeto"));
                dados.setTipoProj(rs.getBoolean("tipoProj"));
                dados.setModelagem2d(rs.getBoolean("modelagem2d"));
                dados.setModelagem3d(rs.getBoolean("modelagem3d"));
                dados.setValor(rs.getLong("valor"));
                dados.setDataEntrega(rs.getString("dataEntrega"));
                dados.setDataPagamento(rs.getString("dataPagamento"));
                dados.setDetalhePag(rs.getString("detalhePag"));
                dados.setSoftAUTOCAD(rs.getBoolean("softAUTOCAD"));
                dados.setSoftSOLID(rs.getBoolean("softSOLID"));
                dados.setNomeResp(rs.getString("nomeResp"));
                dados.setNomeEmp(rs.getString("nomeEmp"));
                dados.setContato(rs.getLong("contato"));
                dados.setEmail(rs.getString("email"));
                dados.setDetalheProj(rs.getString("detalhesProj"));
                dados.setNomeProjeto(rs.getString("nomeProjeto"));
                dados.setTipoProj1(rs.getBoolean("tipoProj1"));

            }
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
        }

    }

    public void preencheCampos() {
        if (dados.getTipoProj() == true) {
            jCheckBox5.setSelected(true);
        } else {
            jCheckBox5.setSelected(false);
        }

        if (dados.getModelagem2d() == true) {
            jCheckBox1.setSelected(true);
        } else {
            jCheckBox1.setSelected(false);
        }

        if (dados.getModelagem3d() == true) {
            jCheckBox2.setSelected(true);
        } else {
            jCheckBox2.setSelected(false);
        }

        jTextField4.setText(String.valueOf(dados.getValor()));
        jFormattedTextField1.setText(dados.getDataEntrega().toString());
        jFormattedTextField4.setText(dados.getDataPagamento().toString());
        jTextArea2.setText(dados.getDetalhePag());

        if (dados.getSoftAUTOCAD() == true) {
            jCheckBox3.setSelected(true);
        } else {
            jCheckBox3.setSelected(false);
        }

        if (dados.getSoftSOLID() == true) {
            jCheckBox4.setSelected(true);
        } else {
            jCheckBox4.setSelected(false);
        }

        jTextField1.setText(dados.getNomeResp());
        jTextField2.setText(dados.getNomeEmp());
        jFormattedTextField2.setText(String.valueOf(dados.getContato()));
        jTextField5.setText(dados.getEmail());
        jTextArea1.setText(dados.getDetalheProj());
        jTextField3.setText(dados.getNomeProjeto());

        if (dados.getTipoProj1() == true) {
            jCheckBox6.setSelected(true);
        } else {
            jCheckBox6.setSelected(false);
        }
    }

    public void atualizar() {
        String sql = "UPDATE projeto SET tipoProj=?, modelagem2d=?, modelagem3d=?, valor=?, "
                + "dataEntrega=?, dataPagamento=?, detalhePag=?, softAUTOCAD=?, softSOLID=?, "
                + "nomeResp=?, nomeEmp=?, contato=?, email=?,"
                + "detalhesProj=?, nomeProjeto=?, tipoProj1=? WHERE idprojeto=?";

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(jFormattedTextField1.getText());
            df.parse(jFormattedTextField4.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Data Inválida");
            return;
        }

        boolean email = jTextField5.getText().matches("[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$");//Verifica se o email bate com a Expressão Regular

        if (jTextField1.getText().trim().equals("") || jTextField2.getText().trim().equals("")
                || jTextField3.getText().trim().equals("") || jTextField4.getText().trim().equals("")
                || jTextField5.getText().trim().equals("") || jFormattedTextField1.getText().trim().equals("")
                || jFormattedTextField2.getText().trim().equals("") || jFormattedTextField4.getText().trim().equals("")) { //Verificando se os campos estão vazios

            JOptionPane.showMessageDialog(null, "Você esqueceu de inserir algum dado");

        } else if (email == false) {
            JOptionPane.showMessageDialog(null, "O email inserido não é válido");
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja atualizar esse cadastro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = Conexao.getConnection()) {

                    dados.setTipoProj(jCheckBox5.isSelected());
                    dados.setModelagem2d(jCheckBox1.isSelected());
                    dados.setModelagem3d(jCheckBox2.isSelected());
                    dados.setValor(Long.parseLong(jTextField4.getText()));
                    dados.setDataEntrega(jFormattedTextField1.getText());
                    dados.setDataPagamento(jFormattedTextField4.getText());
                    dados.setDetalhePag(jTextArea2.getText());
                    dados.setSoftAUTOCAD(jCheckBox3.isSelected());
                    dados.setSoftSOLID(jCheckBox4.isSelected());
                    dados.setNomeResp(jTextField1.getText());
                    dados.setNomeEmp(jTextField2.getText());
                    dados.setContato(Long.parseLong(jFormattedTextField2.getText()));
                    dados.setEmail(jTextField5.getText());
                    dados.setDetalheProj(jTextArea1.getText());
                    dados.setNomeProjeto(jTextField3.getText());
                    dados.setTipoProj1(jCheckBox6.isSelected());

                    PreparedStatement stmt = conn.prepareStatement(sql);

                    stmt.setBoolean(1, dados.getTipoProj());
                    stmt.setBoolean(2, dados.getModelagem2d());
                    stmt.setBoolean(3, dados.getModelagem3d());
                    stmt.setLong(4, dados.getValor());
                    stmt.setString(5, dados.getDataEntrega());
                    stmt.setString(6, dados.getDataPagamento());
                    stmt.setString(7, dados.getDetalhePag());
                    stmt.setBoolean(8, dados.getSoftAUTOCAD());
                    stmt.setBoolean(9, dados.getSoftSOLID());
                    stmt.setString(10, dados.getNomeResp());
                    stmt.setString(11, dados.getNomeEmp());
                    stmt.setLong(12, dados.getContato());
                    stmt.setString(13, dados.getEmail());
                    stmt.setString(14, dados.getDetalheProj());
                    stmt.setString(15, dados.getNomeProjeto());
                    stmt.setBoolean(16, dados.getTipoProj1());
                    stmt.setInt(17, dados.getId());
                    stmt.execute();

                    conn.close();
                    stmt.close();
                    JOptionPane.showMessageDialog(null, "Editado com sucesso");
                    dispose();
                    new TelaPrincipal().setVisible(true);
                } catch (SQLException e) {
                    System.out.println(e);
                }

            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Orator Std", 0, 18)); // NOI18N
        jLabel1.setText("Cadastro");

        jLabel2.setText("Nome Responsável:");

        jLabel3.setText("Empresa:");

        jLabel4.setText("Nome do Projeto:");

        jCheckBox1.setText("2D");

        jCheckBox2.setText("3D");

        jLabel5.setText("Modelagem:");

        jLabel6.setText("Software utilizado:");

        jCheckBox3.setText("AUTOCAD");

        jCheckBox4.setText("TopSolid");

        jLabel7.setText("Detalhes e observações:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel8.setText("Tipo de projeto:");

        jCheckBox5.setText("Ferramenta");

        jCheckBox6.setText("Peça");

        jLabel9.setText("Telefone:");

        jLabel10.setText("E-mail:");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("ORÇAMENTO");

        jLabel12.setText("Valor:");

        jLabel13.setText("Data de entrega:");

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        try {
            jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setText("Data de pagamento:");

        try {
            jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel15.setText("Detalhes do pagamento:");

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Editar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                .addComponent(jTextField2))
                            .addComponent(jLabel10))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBox2)))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBox4))
                                    .addComponent(jLabel6)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBox6)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(370, 370, 370))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(359, 359, 359))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox6)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        new TelaPrincipal().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        salvar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        atualizar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (jButton3.isEnabled()) {
            preencheCampos();
        }
    }//GEN-LAST:event_formWindowActivated

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cadastro(Cadastro.CADASTRAR).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
