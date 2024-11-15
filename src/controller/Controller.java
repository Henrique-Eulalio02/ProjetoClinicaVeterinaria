package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.AnimalDAO;
import model.Tutor;
import model.TutorDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.GenericTableModel;
import view.TutorTableModel;
import view.VeterinarioTableModel;

public class Controller {
    private static Tutor tutorSelecionado = null;
    private static Animal animalSelecionado = null;
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
}
