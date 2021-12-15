package br.ufsc.ine5429.numerosprimos.prng;

import java.math.BigInteger;
import java.util.ArrayList;

public class BlumBlumShub {
	
	private BigInteger x;
	private BigInteger m;
	private BigInteger numeroZero;
	private ArrayList<BigInteger> numerosAleatorios;
	
	public BlumBlumShub() {
		this.x = BigInteger.valueOf(System.currentTimeMillis()); // semente, timestamp
		this.m = new BigInteger("77232917"); // o número escolhido para módulo foi um primo grande (para melhorar a aleatoriedade)
		this.numeroZero = new BigInteger("0");
	}
	
	public ArrayList<BigInteger> geraNumeros(int quantidadeNumeros) {

		this.numerosAleatorios = new ArrayList<>();
		int i = 0;
		/* repete a quantidade de vezes definida pelo usuário		
		 * ou seja, se quantidadeNumeros = 3
		 * 3 números aleatórios serão gerados
		 */
		while(i < quantidadeNumeros) {
			/* 
			 * xₙ+1 = (xₙ)² mod M , onde:
			 * xₙ é a semente (que na primeira rodada é o timestamp)
			 * M é o módulo, que foi escolhido como um número primo grande 
			 * a cada rodada a semente usada é o valor aleatório obtido anteriormente
			 */			
			this.x = (this.x.multiply(this.x)).mod(this.m); // essa atribuição realiza a definição anterior (x*x)%m
			// verifica se a atribuição acima é maior do que zero
			if(this.x.compareTo(numeroZero) == -1) { 
				// enquanto não for maior que zero continua realizando o cálculo
				while(this.x.compareTo(numeroZero) == -1) { 
					this.x = (this.x.multiply(this.x)).mod(this.m);		
					// se a atribuição for maior do que zero vai adicionar o valor no array (garante que apenas números positivos sejam considerados)
					if(this.x.compareTo(numeroZero) == 1) { 
						i++; // incrementa em contador
						// adiciona o número no array de números aleatório
						numerosAleatorios.add(this.x);
					}
				}
			}else {
				i++; // incrementa em um
				// se a primeira atribuição já é > 0, vem direto para cá
				// adiciona o número no array de números aleatório
				numerosAleatorios.add(this.x);
			}
			
		}
		// retorna os números aleatórios criados
		return numerosAleatorios;
	}
	

}
