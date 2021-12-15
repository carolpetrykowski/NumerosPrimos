package br.ufsc.ine5429.numerosprimos.testeprimalidade;

import java.math.BigInteger;


public class MillerRabin {

	private BigInteger k; // expoente máximo
	private BigInteger q; // resto da divisão
	private BigInteger a; // número aleátio maior, deve ser: 1 < a < numero - 1 
	
	private BigInteger numeroMenosUm;
	private BigInteger numeroZero;
	private BigInteger numeroUm;
	private BigInteger numeroDois;

	public MillerRabin() {
		/* para fins de simplificação o a escolhido foi 2
		 * do contrário seria necessário criar um método para escolher aleatóriamente entre dois números
		 * visto que o BigInteger não dispõem de um método como random
		 */
		this.a = new BigInteger("2");
		// algumas definições de número em BigInteger apenas para facilitar os cálculos e não deixar o código tão poluido
		this.numeroMenosUm = new BigInteger("-1");
		this.numeroZero = new BigInteger("0");
		this.numeroUm = new BigInteger("1");
		this.numeroDois = new BigInteger("2"); 
	}

	public String testaPrimalidade(BigInteger numero) {
		// variável para calcular tempo total de execução do algortimo
		long tempoInicial =  System.nanoTime();
		
		// verifica se número é par, se for nem entra, pois não pode ser primo
		if (numero.mod(this.numeroDois) != this.numeroZero) {
			/* encontra os valores para k e q
			 * numero - 1 = 2^k * q
			 * */
			retornaValoresParaKeQ(numero);

			/* verifica se a^q mod == 1
			 * se for igual a 1 é primo
			 * */
			if ((this.a.modPow(this.q, numero).equals(this.numeroUm))
					|| (this.a.modPow(this.q, numero).equals(this.numeroMenosUm))) {
				return "Primo"  + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
			} else {
				int expoente = 1;
				// se não repete-se até j ser igual a k 
				for (int j = 0; j < this.k.intValue(); j++) {
					/* verifica se;
					 * (a^q)^expoente mod numero == numero - 1
					 * se for é primo
					 * isso só é possível pela propriedade: a mod n = n - 1  ≡ -1 mod n
					 * */
					if (this.a.modPow((this.q.pow(expoente)), numero).equals(numero.subtract(this.numeroUm))) {
						return "Primo"  + ", tempo decorrido: " + ( System.nanoTime()-tempoInicial) + "ns";
					}
					expoente *= 2;
				}
			}
		} else {
			// é par então é composto
			return "Composto"  + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
		}
		// não caiu em nenhum dos if's então é composto
		return "Composto" + ", tempo decorrido: " + (System.nanoTime()-tempoInicial) + "ns";
	}

	public void retornaValoresParaKeQ(BigInteger numero) {
		int i = 0;
		// repete até que numero - 1 seja divisível por 2^i
		while ((numero.subtract(this.numeroUm)).mod(this.numeroDois.pow((int) i)).equals(this.numeroZero)) {
			// atribui os valores as variáveis, só vai entrar até enquanto for divisível, logo vai receber o valor máximo para k 
			this.k = BigInteger.valueOf(i);
			this.q = (numero.subtract(this.numeroUm)).divide(this.numeroDois.pow((int) i));
			i++;
		}
	}	
}
