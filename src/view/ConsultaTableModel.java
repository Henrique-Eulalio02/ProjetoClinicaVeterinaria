package view;

import java.util.List;
import model.Animal;
import model.AnimalDAO;
import model.ConsultaGeral;
import model.ConsultaGeralDAO;
import model.VeterinarioDAO;
/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class ConsultaTableModel extends GenericTableModel {

    public ConsultaTableModel(List vDados){
        super(vDados, new String[]{"Motivo", "Diagnóstico", "Nome Animal", "Veterinario"});
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
        ConsultaGeral cg = (ConsultaGeral) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return cg.getMotivo();
            case 1:
                return cg.getDiagnostico();
            case 2:
                return ConsultaGeralDAO.getInstance().retriveAnimalName(cg.getAnimalId());
            case 3:
                return ConsultaGeralDAO.getInstance().retriveVeterinarioName(cg.getVeterinarioId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ConsultaGeral cg = (ConsultaGeral) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                cg.setMotivo((String)aValue);
                break;
            case 1:
                cg.setDiagnostico((String)aValue);
                break;
            case 2:
                ConsultaGeralDAO.getInstance().retriveAnimalName(cg.getAnimalId());
                break;
            case 3:
                ConsultaGeralDAO.getInstance().retriveVeterinarioName(cg.getVeterinarioId());
                break;
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        ConsultaGeralDAO.getInstance().update(cg);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
