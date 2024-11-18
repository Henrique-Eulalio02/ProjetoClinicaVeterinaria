package view;

import java.sql.Timestamp;
import java.util.List;
import model.Animal;
import model.AnimalDAO;
import model.Castracao;
import model.CastracaoDAO;
import model.ConsultaGeral;
import model.ConsultaGeralDAO;
import model.Exame;
import model.ExameDAO;
import model.VeterinarioDAO;
/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class ExameTableModel extends GenericTableModel {

    public ExameTableModel(List vDados){
        super(vDados, new String[]{"Tipo Exame", "Nome Animal", "Resultados", "Data", "Veterinário"});
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
        Exame e = (Exame) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return e.getTipoExame();
            case 1:
                return ExameDAO.getInstance().retriveAnimalName(e.getAnimalId());
            case 2:
                return e.getResultados();
            case 3:
                return e.getData();
            case 4:
                return ExameDAO.getInstance().retriveVeterinarioName(e.getVeterinarioId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Exame e = (Exame) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                e.setTipoExame((String)aValue);
                break;
            case 1:
                ExameDAO.getInstance().retriveAnimalName(e.getAnimalId());
                break;
            case 2:
                e.setResultados((String) aValue);
                break;
            case 3:
                e.setData((Timestamp) aValue);
            case 4:
                ExameDAO.getInstance().retriveVeterinarioName(e.getVeterinarioId());
                break;
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        ExameDAO.getInstance().update(e);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
