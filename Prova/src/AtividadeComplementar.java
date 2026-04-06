public class AtividadeComplementar {

    private String descricao;
    private int cargaHoraria;
    private TipoAtividade tipo;

    public AtividadeComplementar(String descricao, int cargaHoraria, TipoAtividade tipo) {
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.tipo = tipo;
    }

    /**
     * Calcula os créditos gerados por esta atividade.
     * - PROFISSIONAL: 1 crédito a cada 30h
     * - ESTAGIO:      1 crédito a cada 45h
     * - EXTENSAO:     1 crédito a cada 20h
     * Nenhuma atividade pode gerar mais de 4 créditos.
     * Créditos não são fracionários (divisão inteira).
     */
    public int calcularCreditos() {
        int horasPorCredito;

        switch (this.tipo) {
            case PROFISSIONAL: horasPorCredito = 30; break;
            case ESTAGIO:      horasPorCredito = 45; break;
            case EXTENSAO:     horasPorCredito = 20; break;
            default: return 0;
        }

        int creditos = this.cargaHoraria / horasPorCredito; // divisão inteira = sem fração
        return Math.min(creditos, 4);                        // teto de 4 créditos
    }

    // Getters
    public String getDescricao() { return descricao; }
    public int getCargaHoraria() { return cargaHoraria; }
    public TipoAtividade getTipo() { return tipo; }
}