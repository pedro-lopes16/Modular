import org.junit.Test;
import static org.junit.Assert.*;

public class UniversidadeTest {

    // =========================================================
    //  TESTES: calcularCreditos()
    // =========================================================

    @Test
    public void testCreditosProfissional_30h_deve_gerar_1_credito() {
        // ARRANGE
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Trabalho empresa", 30, TipoAtividade.PROFISSIONAL
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(1, creditos);
    }

    @Test
    public void testCreditosEstagio_90h_deve_gerar_2_creditos() {
        // ARRANGE
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Estágio empresa X", 90, TipoAtividade.ESTAGIO
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(2, creditos);
    }

    @Test
    public void testCreditosExtensao_60h_deve_gerar_3_creditos() {
        // ARRANGE
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Curso de extensão", 60, TipoAtividade.EXTENSAO
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(3, creditos);
    }

    @Test
    public void testCreditos_nao_devem_ser_fracionarios() {
        // ARRANGE — 50h de PROFISSIONAL = 50/30 = 1.66 → deve retornar 1
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Consultoria parcial", 50, TipoAtividade.PROFISSIONAL
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(1, creditos); // fração descartada pela divisão inteira
    }

    // =========================================================
    //  TESTES: limite máximo de 4 créditos por atividade
    // =========================================================

    @Test
    public void testLimiteMaximo_extensao_300h_deve_retornar_4_creditos() {
        // ARRANGE — 300/20 = 15 créditos, mas o teto é 4
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Extensão muito longa", 300, TipoAtividade.EXTENSAO
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(4, creditos);
        assertTrue(creditos <= 4); // garantia explícita do teto
    }

    @Test
    public void testLimiteMaximo_profissional_200h_deve_retornar_4_creditos() {
        // ARRANGE — 200/30 = 6 créditos, mas o teto é 4
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Trabalho longo", 200, TipoAtividade.PROFISSIONAL
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(4, creditos);
    }

    @Test
    public void testLimiteMaximo_estagio_300h_deve_retornar_4_creditos() {
        // ARRANGE — 300/45 = 6 créditos, mas o teto é 4
        AtividadeComplementar atividade = new AtividadeComplementar(
            "Estágio longo", 300, TipoAtividade.ESTAGIO
        );
        // ACT
        int creditos = atividade.calcularCreditos();
        // ASSERT
        assertEquals(4, creditos);
    }

    // =========================================================
    //  TESTES: podeSeFormar()
    // =========================================================

    @Test
    public void testPodeSeFormar_com_creditos_e_tipos_suficientes() {
        // ARRANGE — aluno com 10+ créditos e 2+ tipos
        Aluno aluno = new Aluno("Ana", "2021001", "CC01");
        // 4 créditos (teto) + 3 + 4 = 11 créditos, 3 tipos diferentes
        aluno.adicionarAtividade(new AtividadeComplementar("Estágio",      180, TipoAtividade.ESTAGIO));       // 4 créditos (teto)
        aluno.adicionarAtividade(new AtividadeComplementar("Extensão",      60, TipoAtividade.EXTENSAO));      // 3 créditos
        aluno.adicionarAtividade(new AtividadeComplementar("Profissional", 120, TipoAtividade.PROFISSIONAL)); // 4 créditos (teto)
        // ACT
        boolean resultado = aluno.podeSeFormar();
        // ASSERT
        assertTrue(resultado);
    }

    @Test
    public void testNaoPodeSeFormar_creditos_insuficientes() {
        // ARRANGE — 2 tipos, mas apenas 5 créditos
        Aluno aluno = new Aluno("Bruno", "2021002", "CC01");
        aluno.adicionarAtividade(new AtividadeComplementar("Estágio curto",    45, TipoAtividade.ESTAGIO));    // 1 crédito
        aluno.adicionarAtividade(new AtividadeComplementar("Extensão curta",   80, TipoAtividade.EXTENSAO));   // 4 créditos (teto)
        // Total: 5 créditos — insuficiente
        // ACT
        boolean resultado = aluno.podeSeFormar();
        // ASSERT
        assertFalse(resultado);
    }

    @Test
    public void testNaoPodeSeFormar_apenas_um_tipo_de_atividade() {
        // ARRANGE — créditos suficientes, mas só 1 tipo
        Aluno aluno = new Aluno("Carlos", "2021003", "CC01");
        aluno.adicionarAtividade(new AtividadeComplementar("Extensão 1", 80, TipoAtividade.EXTENSAO)); // 4 créditos
        aluno.adicionarAtividade(new AtividadeComplementar("Extensão 2", 80, TipoAtividade.EXTENSAO)); // 4 créditos
        aluno.adicionarAtividade(new AtividadeComplementar("Extensão 3", 80, TipoAtividade.EXTENSAO)); // 4 créditos
        // Total: 12 créditos, MAS só 1 tipo → não pode se formar
        // ACT
        boolean resultado = aluno.podeSeFormar();
        // ASSERT
        assertFalse(resultado);
    }

    @Test
    public void testNaoPodeSeFormar_sem_nenhuma_atividade() {
        // ARRANGE
        Aluno aluno = new Aluno("Daniela", "2021004", "CC01");
        // Nenhuma atividade adicionada
        // ACT
        boolean resultado = aluno.podeSeFormar();
        // ASSERT
        assertFalse(resultado);
    }

    @Test
    public void testCreditosAcumulados_soma_correta_entre_atividades() {
        // ARRANGE
        AtividadeComplementar a1 = new AtividadeComplementar("Estágio",     90, TipoAtividade.ESTAGIO);       // 2 créditos
        AtividadeComplementar a2 = new AtividadeComplementar("Extensão",    60, TipoAtividade.EXTENSAO);      // 3 créditos
        AtividadeComplementar a3 = new AtividadeComplementar("Profissional",150, TipoAtividade.PROFISSIONAL); // 5 → teto 4
        // ACT
        int c1 = a1.calcularCreditos();
        int c2 = a2.calcularCreditos();
        int c3 = a3.calcularCreditos();
        // ASSERT
        assertEquals(2, c1);
        assertEquals(3, c2);
        assertEquals(4, c3); // teto aplicado
        assertEquals(9, c1 + c2 + c3);
    }
}