package view;

import java.util.List;
import model.Animal;
import model.AnimalDAO;
import model.Castracao;
import model.CastracaoDAO;
import model.ConsultaGeral;
import model.ConsultaGeralDAO;
import model.VeterinarioDAO;
/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class CastracaoTableModel extends GenericTableModel {

    public CastracaoTableModel(List vDados){
        super(vDados, new String[]{"Tipo Castração", "Nome Animal", "Idade", "Peso", "Veterinário"});
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
        Castracao c = (Castracao) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getTipoCastracao();
            case 1:
                return CastracaoDAO.getInstance().retriveAnimalName(c.getAnimalId());
            case 2:
                return c.getIdadeAnimalNaCastracao();
            case 3:
                return c.getPesoAnimalNaCastracao();
            case 4:
                return CastracaoDAO.getInstance().retriveVeterinarioName(c.getVeterinarioId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Castracao c = (Castracao) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                c.setTipoCastracao((String)aValue);
                break;
            case 1:
                CastracaoDAO.getInstance().retriveAnimalName(c.getAnimalId());
                break;
            case 2:
                c.setIdadeAnimalNaCastracao((int) aValue);
                break;
            case 3:
                c.setPesoAnimalNaCastracao((double) aValue);
                break;
            case 4:
                CastracaoDAO.getInstance().retriveVeterinarioName(c.getVeterinarioId());
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        CastracaoDAO.getInstance().update(c);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
