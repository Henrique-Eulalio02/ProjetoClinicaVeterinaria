package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.AnimalDAO;
import model.Castracao;
import model.CastracaoDAO;
import model.ConsultaGeral;
import model.ConsultaGeralDAO;
import model.Exame;
import model.ExameDAO;
import model.Tutor;
import model.TutorDAO;
import model.Vacina;
import model.VacinaDAO;
import model.Vacinacao;
import model.VacinacaoDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.CastracaoTableModel;
import view.ConsultaTableModel;
import view.ExameTableModel;
import view.GenericTableModel;
import view.TutorTableModel;
import view.VacinacaoTableModel;
import view.VeterinarioTableModel;

public class Controller {
    private static Tutor tutorSelecionado = null;
    private static Animal animalSelecionado = null;
    private static ConsultaGeral consultaSelecionada = null;
    private static Castracao castracaoSelecionada = null;
    private static Exame exameSelecionado = null;
    private static Vacinacao vacinacaoSelecionada = null;
    private static Veterinario veterinarioSelecionado = null;
    private static JTextField tutorSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static void setTextFields(JTextField tutor, JTextField animal) {
        tutorSelecionadoTextField = tutor;
        animalSelecionadoTextField = animal;
    }
    
    public static Tutor getTutorSelecionado() {
        return tutorSelecionado;
    }
    
    public static Veterinario getVeterinarioSelecionado() {
        return veterinarioSelecionado;
    }
    
    public static Animal getAnimalSelecionado() {
        return animalSelecionado;
    }
    
    public static ConsultaGeral getConsultaSelecionada() {
        return consultaSelecionada;
    }
    
    public static Castracao getCastracaoSelecionada() {
        return castracaoSelecionada;
    }
    
    public static Exame getExameSelecionado() {
        return exameSelecionado;
    }
    
    public static Vacinacao getVacinacaoSelecionada() {
        return vacinacaoSelecionada;
    }
    
    public static void setSelected(Object selected) {
        if (selected instanceof Tutor) {
            tutorSelecionado = (Tutor) selected;
            tutorSelecionadoTextField.setText(tutorSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        } else if (selected instanceof Animal) {
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        } else if (selected instanceof Veterinario) {
            veterinarioSelecionado = (Veterinario) selected;
        } else if (selected instanceof ConsultaGeral) {
            consultaSelecionada = (ConsultaGeral) selected;
        } else if (selected instanceof Castracao) {
            castracaoSelecionada = (Castracao) selected;
        } else if (selected instanceof Exame) {
            exameSelecionado = (Exame) selected;
        } else if (selected instanceof Vacinacao) {
            vacinacaoSelecionada = (Vacinacao) selected;
        }
    }
    
    public static void jRadioButtonTutoresSelecionados(JTable table) {
        setTableModel(table, new TutorTableModel(TutorDAO.getInstance().retrieveAll()));
    }
    
    public static void jRadioButtonVeterinariosSelecionados(JTable table) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }
    
    public static boolean jRadioButtonAnimaisSelecionados(JTable table) {
        if (getTutorSelecionado() != null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdTutor(Controller.getTutorSelecionado().getId())));
            return true;
        } else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static void atualizaBuscaPorNomeSimiliar(JTable table, String nome) {
         if (table.getModel() instanceof TutorTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(TutorDAO.getInstance().retrieveBySimilarName(nome));
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveBySimilarName(nome));
        } else if (table.getModel() instanceof AnimalTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveBySimilarName(nome));
        } else if (table.getModel() instanceof ConsultaTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(ConsultaGeralDAO.getInstance().retrieveByDiagnostico(nome));
        } else if (table.getModel() instanceof CastracaoTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(CastracaoDAO.getInstance().retrieveByTipoCastracaoName(nome));
        } else if (table.getModel() instanceof ExameTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(ExameDAO.getInstance().retrieveByTipoExame(nome));
        }
    }
    
    public static List<Object> getAllTutor() {
        return TutorDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllVeterinario() {
        return VeterinarioDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllAnimal() {
        return AnimalDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllConsulta() {
        return ConsultaGeralDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllCastracao() {
        return CastracaoDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllExame() {
        return ExameDAO.getInstance().retrieveAll();
    }
    
    public static List<Object> getAllVacinacao() {
        return VacinacaoDAO.getInstance().retrieveAll();
    }
    
    public static Tutor adicionarTutor(String nome, String endereco, String cpf, String celular) {
        Tutor novoTutor = TutorDAO.getInstance().create(nome, endereco, cpf, celular);
        return novoTutor;
    }
    
    public static Animal adicionarAnimal(String nome, String especie, String raca, int idade, String sexo, Tutor tutor, Veterinario veterinario) {
        return AnimalDAO.getInstance().create(nome, especie, raca, idade, sexo, tutor.getId(), veterinario.getId());
    }
    
    public static Veterinario adicionarVeterinario(String nome, String endereco, String cpf, String celular, String crmv) {
        Veterinario novoVeterinario = VeterinarioDAO.getInstance().create(nome, endereco, cpf, celular, crmv);
        return novoVeterinario;
    }
    
    public static ConsultaGeral adicionarConsulta(String motivo, String diagnostico, int animalId, int veterinarioId) {
        return ConsultaGeralDAO.getInstance().create(animalId, veterinarioId, motivo, diagnostico);
    }
    
    public static Castracao adicionarCastracao(String tipoCastracao, int idade, double peso, int animalId, int veterinarioId) {
        return CastracaoDAO.getInstance().create(animalId, veterinarioId, tipoCastracao, idade, peso);
    }
    
    public static Exame adicionarExame(String tipoExame, String resultados, int animalId, int veterinarioId, Timestamp data) {
        return ExameDAO.getInstance().create(animalId, veterinarioId, tipoExame, resultados, data);
    }
    
    public static Vacina adicionarVacina(String nome, String descricao, String marca, int quantidadeEstoque) {
        return VacinaDAO.getInstance().create(nome, descricao, marca, quantidadeEstoque);
    }
    
    public static Vacinacao adicionarVacinacao(int animalId, int veterinarioId, int vacinaId, Timestamp data, Timestamp nextData) {
        return VacinacaoDAO.getInstance().create(animalId, veterinarioId, vacinaId, data, nextData);
    }
    
    public static void removerTutor(Tutor tutor) {
        TutorDAO.getInstance().delete(tutor);
        tutorSelecionadoTextField.setText("");
    }
    
    public static void removerVeterinario(Veterinario veterinario) {
        VeterinarioDAO.getInstance().delete(veterinario);
    }
    
    public static void removerAnimal(Animal animal) {
        AnimalDAO.getInstance().delete(animal);
        animalSelecionadoTextField.setText("");
    }
    
    public static void removerConsulta(ConsultaGeral consulta) {
        ConsultaGeralDAO.getInstance().delete(consulta);
    }
    
    public static void removerCastracao(Castracao castracao) {
        CastracaoDAO.getInstance().delete(castracao);
    }
    
    public static void removerExame(Exame exame) {
        ExameDAO.getInstance().delete(exame);
    }
    
    public static void removerVacinacao(Vacinacao vacinacao) {
        VacinacaoDAO.getInstance().delete(vacinacao);
    }
    
    public static void controlaBotaoTodos(JTable table, JTextField texto) {
        if (table.getModel() instanceof TutorTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllTutor());
            texto.setText("");
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllVeterinario());
            texto.setText("");
        } else if (table.getModel() instanceof AnimalTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllAnimal());
            texto.setText("");
        } else if (table.getModel() instanceof ConsultaTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllConsulta());
        } else if (table.getModel() instanceof CastracaoTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllCastracao());
        } else if (table.getModel() instanceof ExameTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllExame());
        } else if (table.getModel() instanceof VacinacaoTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(Controller.getAllVacinacao());
        }
    }
    
    public static void atualizarTutor(String nome, String endereco, String cpf, String celular) {
        Tutor tutorAtualizado = new Tutor(tutorSelecionado.getId(), nome, endereco, cpf, celular);
        TutorDAO.getInstance().update(tutorAtualizado);
    }
    
    public static void atualizarVeterinario(String nome, String endereco, String cpf, String celular, String crmv) {
        Veterinario veterinarioAtualizado = new Veterinario(veterinarioSelecionado.getId(), nome, endereco, cpf, celular, crmv);
        VeterinarioDAO.getInstance().update(veterinarioAtualizado);
    }
    
    public static void atualizarAnimal(String nome, String especie, String raca, int idade, String sexo, int tutorId, int veterinarioId) {
        Animal animalAtualizado = new Animal(animalSelecionado.getId(), nome, especie, raca, idade, sexo, tutorId, veterinarioId);
        AnimalDAO.getInstance().update(animalAtualizado);
    }
    
    public static void atualizarConsulta(String motivo, String diagnostico, int animalId, int veterinarioId) {
        ConsultaGeral cg = new ConsultaGeral(consultaSelecionada.getId(), animalId, veterinarioId, motivo, diagnostico);
        ConsultaGeralDAO.getInstance().update(cg);
    }
}
