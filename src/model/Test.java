package model;

public class Test {
    public static void main (String[] args) {
        PessoaDAO p = PessoaDAO.getInstance();
        AnimalDAO a = AnimalDAO.getInstance();
        TutorDAO t = TutorDAO.getInstance();
        
        p.create("henrique", "rua osasco, 226", "45396707860", "19996329573");
        t.create(1);
        a.create("goku", "cachorro", "pug", 7, "masculino", 1);
        
    }
}
