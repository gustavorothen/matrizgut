package main;

import service.Compromisso;
import service.CompromissoBuilder;
import sql.CompromissoDAO;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class CompromissoGUI extends JFrame {
    private JTextField txtDescricao, txtDiaMes;
    private JComboBox<String> cbDiaSemana, cbGravidade, cbUrgencia, cbTendencia, cbMes;
    private JTextArea areaLista;
    private final CompromissoDAO dao = new CompromissoDAO();

    public CompromissoGUI() {
        setTitle("Cadastro de Compromissos");
        setSize(500, 550);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Descrição:")).setBounds(10, 10, 100, 20);
        txtDescricao = new JTextField();
        txtDescricao.setBounds(120, 10, 300, 25);
        add(txtDescricao);

        add(new JLabel("Dia da Semana:")).setBounds(10, 45, 100, 20);
        cbDiaSemana = new JComboBox<>(new String[]{"Segunda", "Terça", "Quarta", "Quinta", "Sexta"});
        cbDiaSemana.setBounds(120, 45, 150, 25);
        add(cbDiaSemana);

        add(new JLabel("Mês:")).setBounds(10, 80, 100, 20);
        cbMes = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        cbMes.setBounds(120, 80, 70, 25);
        add(cbMes);

        add(new JLabel("Dia do Mês:")).setBounds(200, 80, 100, 20);
        txtDiaMes = new JTextField();
        txtDiaMes.setBounds(280, 80, 50, 25);
        add(txtDiaMes);

        add(new JLabel("Gravidade:")).setBounds(10, 115, 100, 20);
        cbGravidade = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cbGravidade.setBounds(120, 115, 70, 25);
        add(cbGravidade);

        add(new JLabel("Urgência:")).setBounds(200, 115, 100, 20);
        cbUrgencia = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cbUrgencia.setBounds(280, 115, 70, 25);
        add(cbUrgencia);

        add(new JLabel("Tendência:")).setBounds(10, 150, 100, 20);
        cbTendencia = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cbTendencia.setBounds(120, 150, 70, 25);
        add(cbTendencia);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(120, 190, 100, 30);
        btnSalvar.addActionListener(e -> salvarCompromisso());
        add(btnSalvar);

        JButton btnListar = new JButton("Listar");
        btnListar.setBounds(230, 190, 100, 30);
        btnListar.addActionListener(e -> listarCompromissos());
        add(btnListar);

        JButton btnVoltarTexto = new JButton("Voltar ao Menu de Texto");
        btnVoltarTexto.setBounds(130, 460, 180, 30);
        btnVoltarTexto.addActionListener(e -> {
            dispose();
            Main.iniciar();
        });
        add(btnVoltarTexto);


        areaLista = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaLista);
        scroll.setBounds(10, 240, 410, 200);
        add(scroll);

       setVisible(true);
    }

    private void salvarCompromisso() {
        try {
            Compromisso c = new CompromissoBuilder()
                    .builderDescricao(txtDescricao.getText())
                    .builderDiaSemana(cbDiaSemana.getSelectedIndex() + 1)
                    .builderMes(Integer.parseInt((String) cbMes.getSelectedItem()))
                    .builderDiaMes(Integer.parseInt(txtDiaMes.getText()))
                    .builderGravidade(Integer.parseInt((String) cbGravidade.getSelectedItem()))
                    .builderUrgencia(Integer.parseInt((String) cbUrgencia.getSelectedItem()))
                    .builderTendencia(Integer.parseInt((String) cbTendencia.getSelectedItem()))
                    .build();

            dao.salvar(c);
            JOptionPane.showMessageDialog(this, "Compromisso salvo com sucesso!");
            txtDescricao.setText("");
            txtDiaMes.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void listarCompromissos() {
        List<Compromisso> lista = dao.listarTodos();

        for (Compromisso c : lista) {
            c.calcularPrioridade();
        }

        Collections.sort(lista);

        StringBuilder sb = new StringBuilder();
        for (Compromisso c : lista) {
            sb.append(c).append("\n");
        }
        areaLista.setText(sb.toString());
    }



    //public static void main(String[] args) {
   //     new CompromissoGUI();
   // }
}
