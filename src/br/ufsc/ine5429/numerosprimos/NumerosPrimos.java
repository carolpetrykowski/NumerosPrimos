package br.ufsc.ine5429.numerosprimos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import br.ufsc.ine5429.numerosprimos.prng.BlumBlumShub;
import br.ufsc.ine5429.numerosprimos.prng.LinearCongruentialGenerator;
import br.ufsc.ine5429.numerosprimos.testeprimalidade.Fermat;
import br.ufsc.ine5429.numerosprimos.testeprimalidade.MillerRabin;

public class NumerosPrimos {

	private static LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator();
	private static BlumBlumShub blumBlumShub = new BlumBlumShub();
	private static MillerRabin millerRabin = new MillerRabin();
	private static Fermat fermat = new Fermat();
	private static GeradorDeNumeroComQuantidadeBytes geradorNumeroQuantidadeBytes = new GeradorDeNumeroComQuantidadeBytes();
	private static int[] bits = new int[] { 40, 56, 80, 128, 168, 224, 256, 512, 1024, 2048, 4096 }; // bits solicitados
	public static void main(String[] args) {
		menu();
	}
	
	// menu para escolher apenas gerar os numeros aleatório ou gerar os números aleatórios e verificar se são primos
	public static void menu() {
		Scanner entrada = new Scanner(System.in);
		System.out.println(" ______________________________");
		System.out.println("|    SELECIONE UMA OPÇÃO:      |");
		System.out.println("|                              |");
		System.out.println("| 1. Gerar números aleatórios  |");
		System.out.println("| 2. Verificar primalidade     |");
		System.out.println("|______________________________|");
		System.out.print("Selecione um tipo de dificuldade do tabuleiro: ");
		int opcaoEscolhida = entrada.nextInt();
		
		switch (opcaoEscolhida) {
		case 1:
			geraNumerosAleatorios();
			break;
		case 2:
			gerarNumerosAleatoriosComNumeroBits();
			break;
		}
		entrada.close();
	}

	public static void geraNumerosAleatorios() {
		System.out.println("GERAR NÚMEROS ALEATÓRIOS");
		System.out.println("----------------------------------------");
		// loop que passa por todos as quantidades de bits exigidos
		for (int i = 0; i < bits.length; i++) {
			long tempoInicial = System.nanoTime();
			
			// gera os números aleatórios com alg. LCG
			// a quantidade de números gerada é definida pelo número entre parenteses
			// ou seja para cada bit solicitado serão gerados dois numeros, por exemplo
			ArrayList<BigInteger> numeros = linearCongruentialGenerator.geraNumeros(2);
			for(BigInteger numero : numeros) {
				// força o número a ser ímpar e ter a quantidade de bits solicitada
				numero = geradorNumeroQuantidadeBytes.alteraQuantidadeBitsNumero(numero, bits[i]);
				
				// calcula tempo total de execução
				long tempoTotal =  System.nanoTime() - tempoInicial;
				// conta quantos bits o numero tem (após conversão feita acima)
				int quantidadeBits = geradorNumeroQuantidadeBytes.quantidadeBitsNumeroBinario();
				
				// mostra mensagens na tela
				imprimeMensagem("Linear Congruential Generator", quantidadeBits, tempoTotal, numero);
				System.out.println("----------------------------------------");
			}			

		}
		// loop que passa por todos as quantidades de bits exigidos
		for (int i = 0; i < bits.length; i++) {
			long tempoInicial =  System.nanoTime();
			
			// gera os números aleatórios com alg. BBS
			// a quantidade de números gerada é definida pelo número entre parenteses
			// ou seja para cada bit solicitado serão gerados dois numeros, por exemplo			
			ArrayList<BigInteger> numeros = blumBlumShub.geraNumeros(2);
			for(BigInteger numero : numeros) {
				// força o número a ser ímpar e ter a quantidade de bits solicitada
				numero = geradorNumeroQuantidadeBytes.alteraQuantidadeBitsNumero(numero, bits[i]);
				
				// calcula tempo total de execução
				long tempoTotal =  System.nanoTime() - tempoInicial;
				// conta quantos bits o numero tem (após conversão feita acima)
				int quantidadeBits = geradorNumeroQuantidadeBytes.quantidadeBitsNumeroBinario();
				
				// mostra mensagens na tela
				imprimeMensagem("Blum Blum Shub", quantidadeBits, tempoTotal, numero);
				System.out.println("----------------------------------------");			
			}
		}		
	}		
	
	
	public static void gerarNumerosAleatoriosComNumeroBits() {
		System.out.println("TESTE DE PRIMALIDADE");
		System.out.println("----------------------------------------");
		for (int i = 0; i < bits.length; i++) {
			long tempoInicial = System.currentTimeMillis();
			
		
			ArrayList<BigInteger> numeros = linearCongruentialGenerator.geraNumeros(1);
			for(BigInteger numero : numeros) {			
				numero = geradorNumeroQuantidadeBytes.alteraQuantidadeBitsNumero(numero, bits[i]);
				
				long tempoTotal = System.currentTimeMillis() - tempoInicial;
				int quantidadeBits = geradorNumeroQuantidadeBytes.quantidadeBitsNumeroBinario();
				
				imprimeMensagem("Linear Congruential Generator", quantidadeBits, tempoTotal, numero);
				System.out.println("Miller-Rabin: " + millerRabin.testaPrimalidade(numero));
				System.out.println("Fermat: " + fermat.testaPrimalidade(numero));		
				System.out.println("----------------------------------------");
			}			

		}
		for (int i = 0; i < bits.length; i++) {
			long tempoInicial = System.currentTimeMillis();
			
			ArrayList<BigInteger> numeros = blumBlumShub.geraNumeros(1);
			for(BigInteger numero : numeros) {
				numero = geradorNumeroQuantidadeBytes.alteraQuantidadeBitsNumero(numero, bits[i]);
				
				long tempoTotal = System.currentTimeMillis() - tempoInicial;
				int quantidadeBits = geradorNumeroQuantidadeBytes.quantidadeBitsNumeroBinario();
				
				imprimeMensagem("Blum Blum Shub", quantidadeBits, tempoTotal, numero);
				System.out.println("Miller-Rabbin: " + millerRabin.testaPrimalidade(numero));
				System.out.println("Fermat: " + fermat.testaPrimalidade(numero));		
				System.out.println("----------------------------------------");			
			}
		}		
	}
	
	// imprime mensagens na tela
	public static void imprimeMensagem(String tipoAlgoritmo, int quantidadeBits, long tempoTotal, BigInteger numero) {
		System.out.println("Algoritmo: " + tipoAlgoritmo);
		System.out.println("Tamanho do nº: " + quantidadeBits);
		System.out.println("Tempo para gerar nº: " + tempoTotal + "ns");
		System.out.println("Número: " + numero);	
	}
	
}
