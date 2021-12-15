package br.ufsc.ine5429.numerosprimos;

import java.math.BigInteger;


public class GeradorDeNumeroComQuantidadeBytes {

	private String numeroBinario;
	private String novoNumeroBinario;

	public GeradorDeNumeroComQuantidadeBytes() {
	}

	public BigInteger alteraQuantidadeBitsNumero(BigInteger numero, int quantidadeBits) {
		this.numeroBinario = numero.toString(2); // conversão do numero para binário
		this.numeroBinario += "1"; // força o número a ser ímpar
		String bitsAdicionados = "";
		// verifica se o número não tem a quantidade de bits desejado
		if (this.numeroBinario.length() < quantidadeBits) {
			// repete i vezes até i ser iguais a diferença entre a quantidade de bits desejada e o quantidade de bits atual do número em binário
			// vai concatenando 1 a uma nova string
			for (int i = 0; i < (quantidadeBits - this.numeroBinario.length()); i++) {
				bitsAdicionados += "1";
			}
		}
		// o novo número é a série de 1's obtida acima concatenado com o número original em binário
		this.novoNumeroBinario = bitsAdicionados + this.numeroBinario;
		// retorna um BigInteger, convertendo o novoNumeroBinario para decimal
		return new BigInteger(this.novoNumeroBinario, 2);
	}

	// metodo que retorna a quantidade de bits do número em binário
	public int quantidadeBitsNumeroBinario() {
		return this.novoNumeroBinario.length();
	}

}
