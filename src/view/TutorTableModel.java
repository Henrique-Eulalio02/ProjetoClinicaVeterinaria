package view;

import java.util.List;
import model.Tutor;
import model.TutorDAO;

/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class TutorTableModel extends GenericTableModel {

    public TutorTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Endereço", "CPF", "Celular"});
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
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tutor t = (Tutor) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return t.getNome();
            case 1:
                return t.getEndereco();
            case 2:
                return t.getCpf();
            case 3:
                return t.getCelular();              
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tutor t = (Tutor) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                t.setNome((String)aValue);
                break;
            case 1:
                t.setEndereco((String)aValue);
                break;
            case 2:
                t.setCpf((String)aValue);    
                break;
            case 3:
                t.setCelular((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        TutorDAO.getInstance().update(t);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
