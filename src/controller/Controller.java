package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Animal;
import model.AnimalDAO;
import model.Tutor;
import model.TutorDAO;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.GenericTableModel;
import view.TutorTableModel;
import view.VeterinarioTableModel;

public class Controller {
    private static Tutor tutorSelecionado = null;
    private static Animal animalSelecionado = null;
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
    
    public static void setSelected(Object selected) {
        if (selected instanceof Tutor) {
            tutorSelecionado = (Tutor) selected;
            tutorSelecionadoTextField.setText(tutorSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        } else if (selected instanceof Animal) {
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
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
    
    public static List<Object> getTutorPorNomeSimiliar(String nome) {
        return TutorDAO.getInstance().retrieveBySimilarName(nome);
    }
    
    public static List<Object> getVeterinarioPorNomeSimiliar(String nome) {
        return VeterinarioDAO.getInstance().retrieveBySimilarName(nome);
    }
}
