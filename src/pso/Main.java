package pso;

public class Main {

	public static void main(String[] args) {
		//parametros
		int qntpartic = 100;	//quatidade de particulas
		int maxIter = 15;	// maximo de intera�oes
		int tipoFuncao = 1;	//1 esfera, 2 rosenbrock, 3 rastrigin

		Enxame x;
		x = new Enxame(maxIter, qntpartic, tipoFuncao); //criando objeto e randomizando as posi�oes
		Ambiente a = new Ambiente();

		x.reproduzirEnxame(a.getW(), a.getC1(), a.getC2(), qntpartic, tipoFuncao, maxIter); //fun��o geral
	}
}