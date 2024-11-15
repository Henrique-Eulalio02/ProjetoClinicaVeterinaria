package view;

import java.util.List;
import model.Animal;
import model.AnimalDAO;
/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class AnimalTableModel extends GenericTableModel {

    public AnimalTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Especie", "Raca", "Idade", "Sexo", "Nome Tutor"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;     
            case 4:
                return String.class;
            case 5: 
                return String.class;
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal a = (Animal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return a.getNome();
            case 1:
                return a.getEspecie();
            case 2:
                return a.getRaca();
            case 3:
                return a.getIdade();
            case 4:
                return a.getSexo();
            case 5: 
                return AnimalDAO.getInstance().retriveTutorName(a.getTutorId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Animal a = (Animal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                a.setNome((String)aValue);
                break;
            case 1:
                a.setEspecie((String)aValue);
                break;
            case 2:
                a.setRaca((String)aValue);    
                break;
            case 3:
                a.setIdade((int)aValue);
                break;
            case 4:
                a.setSexo((String)aValue);
            case 5:
                AnimalDAO.getInstance().retriveTutorName(a.getTutorId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        AnimalDAO.getInstance().update(a);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
