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
import model.Vacinacao;
import model.VacinacaoDAO;
import model.VeterinarioDAO;
/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class VacinacaoTableModel extends GenericTableModel {

    public VacinacaoTableModel(List vDados){
        super(vDados, new String[]{"Nome Vacina", "Nome Animal", "Veterinário", "Data", "Data Próxima Dose"});
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
        Vacinacao v = (Vacinacao) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return VacinacaoDAO.getInstance().retriveVacinaName(v.getVacinaId());
            case 1:
                return VacinacaoDAO.getInstance().retriveAnimalName(v.getAnimalId());
            case 2:
                return VacinacaoDAO.getInstance().retriveVeterinarioName(v.getVeterinarioId());
            case 3:
                return v.getData();
            case 4:
                return v.getDataProximaDose();
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
    }    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Vacinacao v = (Vacinacao) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                VacinacaoDAO.getInstance().retriveVacinaName(v.getVacinaId());
                break;
            case 1:
                VacinacaoDAO.getInstance().retriveAnimalName(v.getAnimalId());
                break;
            case 2:
                VacinacaoDAO.getInstance().retriveVeterinarioName(v.getVeterinarioId());
                break;
            case 3:
                v.setData((Timestamp) aValue);
            case 4:
                v.setDataProximaDose((Timestamp) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("Column Index out of bounds");
        }
        
        VacinacaoDAO.getInstance().update(v);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}