import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Aluno {

    private String nome;
    private String matricula;
    private String codigoCurso;
    private List<AtividadeComplementar> atividades;

    public Aluno(String nome, String matricula, String codigoCurso) {
        this.nome = nome;
        this.matricula = matricula;
        this.codigoCurso = codigoCurso;
        this.atividades = new ArrayList<>();
    }

    public void adicionarAtividade(AtividadeComplementar atividade) {
        this.atividades.add(atividade);
    }

    /**
     * Verifica se o aluno pode se formar.
     * Condições (ambas devem ser verdadeiras):
     *   1. Acumulou pelo menos 10 créditos no total
     *   2. Participou de pelo menos 2 tipos diferentes de atividade
     */
    public boolean podeSeFormar() {
        int totalCreditos = 0;
        Set<TipoAtividade> tiposUsados = new HashSet<>();

        for (AtividadeComplementar a : this.atividades) {
            totalCreditos += a.calcularCreditos();
            tiposUsados.add(a.getTipo()); // Set ignora duplicatas automaticamente
        }

        return totalCreditos >= 10 && tiposUsados.size() >= 2;
    }

    // Getters
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getCodigoCurso() { return codigoCurso; }
    public List<AtividadeComplementar> getAtividades() { return atividades; }
}