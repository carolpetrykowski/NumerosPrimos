package br.ufsc.ine5429.numerosprimos.prng;

import java.math.BigInteger;
import java.util.ArrayList;


public class LinearCongruentialGenerator {

	private BigInteger m;
	private BigInteger a;
	private BigInteger c;
	private BigInteger x;
	private ArrayList<BigInteger> numerosAleatorios;
	private BigInteger numeroZero;

	public LinearCongruentialGenerator() {
		/*
		 * os valores para m, a e b são números primos grandes
		 * para uma melhor aleatoriedade
		 * */
		this.m = new BigInteger("74207281");
		this.a = new BigInteger("77232917");
		this.c = new BigInteger("57885161");
		// o valor escolhido para semente é uma marca no tempo, para garantir a aleatoriedade
		this.x = BigInteger.valueOf(System.currentTimeMillis());
		this.numeroZero = new BigInteger("0");
	}

	public ArrayList<BigInteger> geraNumeros(int quantidadeNumeros) {
		int i = 0;
		this.numerosAleatorios = new ArrayList<>();
		/* repete a quantidade de vezes definida pelo usuário		
		 * ou seja, se quantidadeNumeros = 3
		 * 3 números aleatórios serão gerados
		 */
		while(i < quantidadeNumeros) {
			/*
			 * xₙ+1= (axₙ + c) mod m
			 * a é o multiplicador
			 * c é o incremento
			 * m é o módulo
			 * */
			this.x = ((this.a.multiply(this.x)).add(this.c)).mod(this.m); // essa atribuição reflete a definição acima: a*x+c%m
			// verifica se a atribuição é maior do que zero
			if(this.x.compareTo(numeroZero) == -1){ 
				// enquanto não for maior que zero continua realizando o cálculo
				while(this.x.compareTo(numeroZero) == -1) {
					this.x = ((this.a.multiply(this.x)).add(this.c)).mod(this.m);
					// se a atribuição for maior do que zero vai adicionar o valor no array (garante que apenas números positivos sejam considerados)
					if(this.x.compareTo(numeroZero) == 1) {
						i++; // incrementa em contador
						// adiciona o número no array de números aleatório						
						numerosAleatorios.add(this.x);
					}
				}
			}else {
				i++; // incrementa em contador
				/* se a primeira atribuição já é > 0, vem direto para cá
				 * adiciona o número no array de números aleatório
				 */
				numerosAleatorios.add(this.x);
			}		
		}
		// retorna os números aleatórios criados		
		return numerosAleatorios;
	}

}
