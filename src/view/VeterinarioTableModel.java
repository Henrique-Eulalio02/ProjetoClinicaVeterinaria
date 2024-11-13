package view;

import java.util.List;
import model.Tutor;
import model.TutorDAO;
import model.Veterinario;
import model.VeterinarioDAO;

/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class VeterinarioTableModel extends GenericTableModel {

    public VeterinarioTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Endereço", "CPF", "Celular", "CRMV"});
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
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario v = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return v.getNome();
            case 1:
                return v.getEndereco();
            case 2:
                return v.getCpf();
            case 3:
                return v.getCelular();
            case 4:
                return v.getCrmv();
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Veterinario v = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                v.setNome((String)aValue);
                break;
            case 1:
                v.setEndereco((String)aValue);
                break;
            case 2:
                v.setCpf((String)aValue);    
                break;
            case 3:
                v.setCelular((String)aValue);
                break;
            case 4:
                v.setCrmv((String)aValue);
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        VeterinarioDAO.getInstance().update(v);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
