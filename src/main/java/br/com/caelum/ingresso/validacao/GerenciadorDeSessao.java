package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	private List<Sessao> sessoesDaSala;

	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}	

	public boolean conflita(Sessao sessao1, Sessao sessao2) {
		LocalDate hoje = LocalDate.now();
		LocalDateTime inicioSessao1 = sessao1.getHorario().atDate(hoje);
		LocalDateTime inicioSessao2 = sessao2.getHorario().atDate(hoje);
		LocalDateTime finalSessao1 = inicioSessao1.plus(sessao1.getFilme().getDuracao());
		LocalDateTime finalSessao2 = inicioSessao2.plus(sessao2.getFilme().getDuracao());

		boolean sessao1ComecaAntesDaSessao2 = inicioSessao1.isBefore(inicioSessao2);
		
		if (sessao1ComecaAntesDaSessao2)
			return finalSessao1.isAfter(inicioSessao2);
		else
			return finalSessao2.isAfter(inicioSessao1);		
	}

	public boolean cabe(Sessao sessaoNova) {
		return sessoesDaSala.stream().noneMatch(sessaoExistente -> conflita(sessaoExistente, sessaoNova));
	}
}
