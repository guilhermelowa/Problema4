package br.uefs.ecomp.controller;

import java.util.ArrayList;

import br.uefs.ecomp.exceptions.PontoNaoExistenteException;
import br.uefs.ecomp.model.Grafo;
import br.uefs.ecomp.model.Ponto;

public class Controller {

	private ArrayList<Ponto> listaPontos;
	
	
	public void calcularRota(ArrayList<Ponto> listaPontos, Ponto pontoInicial, Ponto pontoFinal) throws PontoNaoExistenteException{
		
		final int NUMERO_DE_PONTOS = listaPontos.size(); 
		
		// Remove o ponto inicial da lista de pontos e adiciona-o na primeira posição
		if (!listaPontos.remove(pontoInicial)){ // Caso ponto inicial nao conste na lista
			throw new PontoNaoExistenteException("Ponto inicial nao foi cadastrado!");
		}
		listaPontos.add(0, pontoInicial);
		
		// Remove ponto final da lista de pontos e insere-o no final
		if (!listaPontos.remove(pontoFinal)){
			throw new PontoNaoExistenteException("Ponto final nao foi cadastrado!");
		}
		listaPontos.add(pontoFinal);
		
		
		// Constroi grafo
		Grafo grafo = new Grafo(listaPontos);
		
		
		// Inicio do Algoritmo de Dijkstra
		
		final int VALOR_MAXIMO = 2147483647; // Maior valor de int possivel
		int[][] matriz = grafo.getMatrizAdjacencia(); // Recebe a matriz de adjacencia do grafo
		int[] distancia = new int[NUMERO_DE_PONTOS]; // Array que guarda a distancia de um ponto para outro
		boolean[] visitado = new boolean[NUMERO_DE_PONTOS]; // Array que diz se ponto ja foi visitado ou nao 
		int[] pontoAnterior = new int[NUMERO_DE_PONTOS]; // Array que registra o caminho
		
		
		
		// Primeiro, inicializa-se todas as arrays.
		
		for (int i=0; i<NUMERO_DE_PONTOS; i++){
			
			visitado[i] = false;
			pontoAnterior[i] = 0;
			
			for (int j=0; j<NUMERO_DE_PONTOS; j++){
				if(matriz[i][j] == 0)
					matriz[i][j] = VALOR_MAXIMO; 
			}
		}
		
		distancia = matriz[0]; // As distancias do ponto inicial para os pontos seguintes ja estao prontas!
		visitado[0] = true; // O ponto inicial ja foi visitado, pois sao conhecidas suas distancias
		
		
		
		// Depois, começa o calculo das distancias
		
		int distanciaMinima; // distancia minima parcial
		int proximoPonto = 0;
		
		for (int i=0; i<NUMERO_DE_PONTOS; i++){
			
			distanciaMinima = VALOR_MAXIMO;
			
			// Obtem proximo ponto que sera visitado.
			// Proximo ponto visitado sera aquele com a menor distancia total
			for(int j = 0; j<NUMERO_DE_PONTOS; j++){
				
				if(distanciaMinima > distancia[j] && !visitado[j]){
					distanciaMinima = distancia[j];
					proximoPonto = j;
				}
				
			}
			
			
			visitado[proximoPonto] = true;
			
			// A partir do ponto obtido anteriormente, visita todos os outros pontos.
			for (int k = 0; k<NUMERO_DE_PONTOS; k++){
				if(!visitado[k]){ // Se K nao ja tiver sido visitado
					if(distanciaMinima + matriz[proximoPonto][k] < distancia[k]){
						distancia[k] = distanciaMinima + matriz[proximoPonto][k];
						pontoAnterior[k] = proximoPonto;
					}
				}
			}
			
			// Caso ja tenha chegado no ponto final, pare.
			if (visitado[NUMERO_DE_PONTOS - 1]){ 
				break;
			}
			
			
		}
		
	}
	
}
