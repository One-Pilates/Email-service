package com.onepilates.email_service.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailAlunoTest {

    private static final LocalDateTime AULA = LocalDateTime.of(2026, 11, 12, 9, 0);

    @Test
    void aulaMarcada_mostraDetalhesEEscapaHtmlDoNome() {
        EmailAulaMarcadaAluno email = EmailAulaMarcadaAluno.criar(
                "aluno@teste.com", "Ana <script>", "Mariana", AULA, "Sala 1", "Pilates");

        String html = email.getBodyHtml();
        assertTrue(html.contains("12 de novembro de 2026 às 09:00"));
        assertTrue(html.contains("Pilates"));
        assertTrue(html.contains("Sala 1"));
        assertTrue(html.contains("Mariana"));
        assertTrue(html.contains("Ana &lt;script&gt;"));
        assertFalse(html.contains("<script>"));
    }

    @Test
    void aulaMarcada_rejeitaCamposVazios() {
        assertThrows(IllegalArgumentException.class, () -> EmailAulaMarcadaAluno.criar(
                "aluno@teste.com", " ", "Mariana", AULA, "Sala 1", "Pilates"));
        assertThrows(IllegalArgumentException.class, () -> EmailAulaMarcadaAluno.criar(
                "aluno@teste.com", "Ana", "Mariana", null, "Sala 1", "Pilates"));
    }

    @Test
    void cancelamentoPeloAluno_comDevolucao_avisaQueVoltouAoPacote() {
        String html = EmailCancelamentoAulaAluno.criar("a@t.com", "Ana", AULA, "Sala 1", "Pilates", true, true).getBodyHtml();

        assertTrue(html.contains("feito por você"));
        assertTrue(html.contains("voltou para o seu pacote"));
    }

    @Test
    void cancelamentoPeloAluno_foraDoPrazo_avisaQueFoiDescontada() {
        String html = EmailCancelamentoAulaAluno.criar("a@t.com", "Ana", AULA, "Sala 1", "Pilates", true, false).getBodyHtml();

        assertTrue(html.contains("menos de 24 horas"));
        assertTrue(html.contains("descontada do seu pacote"));
    }

    @Test
    void cancelamentoPelaEquipe_semInformacaoDePacote_naoFalaDePacote() {
        String html = EmailCancelamentoAulaAluno.criar("a@t.com", "Ana", AULA, "Sala 1", "Pilates", null, null).getBodyHtml();

        assertTrue(html.contains("cancelada</strong> pela equipe"));
        assertFalse(html.contains("pacote"));
    }

    @Test
    void lembrete_pedeConfirmacaoNoAplicativoOuContatoComSecretaria() {
        String html = EmailLembretePresenca.criar("a@t.com", "Ana", "Mariana", AULA, "Sala 1", "Pilates").getBodyHtml();

        assertTrue(html.contains("confirme a sua presença"));
        assertTrue(html.contains("secretaria"));
        assertTrue(html.contains("24 horas"));
    }
}
