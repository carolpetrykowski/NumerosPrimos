package br.ufsc.ine5429.numerosprimos.testeprimalidade;

import java.math.BigInteger;
import java.util.Random;

public class Fermat {

	private BigInteger numeroZero;
	private BigInteger numeroUm;
	private BigInteger numeroDois;
	private int quantidadeRepeticoes;
	private Random random;

	public Fermat() {
		this.numeroZero = new BigInteger("0");
		this.numeroUm = new BigInteger("1");
		this.numeroDois = new BigInteger("2");
		this.quantidadeRepeticoes = 100;
		this.random = new Random();
	}

	public String testaPrimalidade(BigInteger numero) {
		long tempoInicial = System.nanoTime();
		
		// repete o algoritmo por uma quantidade pré-definida
		for (int i = 0; i < quantidadeRepeticoes; i++) {

			// gera um número aleatório entre 1 e numero - 1
			BigInteger numeroAleatorio = geraNumeroAleatorio(numero);
			/* faz o maximo divisor comum entre o numero e o numero aleatório
			 * se não for igual a 1 significa que existe mais de um divisor, portanto não é primo
			 */
			if (!numero.gcd(numeroAleatorio).equals(numeroUm)) {
				return "Composto" + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
			}

			BigInteger resultado = new BigInteger("1");
			BigInteger numeroMenosUm = new BigInteger("0");
			numeroMenosUm = numeroMenosUm.add(numero).subtract(numeroUm);
			// enquanto numero - 1 > 0
			while (numeroMenosUm.compareTo(numeroZero) == 1) {
				// se (numero - 1 mod 2) == numero 1
				if (numeroMenosUm.mod(numeroDois).equals(numeroUm)) {
					// resultado = resultado * numeroAleatorio mod numero
					resultado = resultado.multiply(numeroAleatorio).mod(numero);
					// decrementa o (numero - 1)
					numeroMenosUm = numeroMenosUm.subtract(numeroUm);
				}
				// divide (numero-1) por 2
				numeroMenosUm = numeroMenosUm.divide(numeroDois);
				// multiplica o numero aleatorio por ele mesmo e faz mod numero
				numeroAleatorio = numeroAleatorio.multiply(numeroAleatorio).mod(numero);
			}
			// se o resultado não for igual a um é composto
			if (!resultado.equals(numeroUm)) {
				return "Composto" + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
			}
		}
		// quando acaba as iterações e não retornou composto, então é primo
		return "Primo" + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
	}

	// método criado para retornar um número (BigInteger) entre 1 e numero - 1
	public BigInteger geraNumeroAleatorio(BigInteger numero) {
		BigInteger numeroMaximo = numero.subtract(numeroUm);
		BigInteger numeroMinimo = new BigInteger("1");
		BigInteger amplitudeNumeros = numeroMaximo.subtract(numeroMinimo);

		int quantidadeMaximaBit = amplitudeNumeros.bitLength();
		BigInteger numeroAleatorio = new BigInteger(quantidadeMaximaBit, this.random);

		if (numeroAleatorio.compareTo(numeroMinimo) < 0) {
			numeroAleatorio = numeroAleatorio.add(numeroMinimo);
		}
		if (numeroAleatorio.compareTo(numeroMaximo) >= 0) {
			numeroAleatorio = numeroAleatorio.mod(amplitudeNumeros).add(numeroMinimo);
		}

		return numeroAleatorio;
	}

}
