package pso;

public class Enxame {
	private Particula[] vetor;
	private Particula gbest;
	private double[] ftmedia;
	private double[] ftgbest;

	public Enxame() {
	}

	public Enxame(int maxIter, int qntpartic, int tipoFuncao) {
		// definição do tamanho dos vetores
		this.vetor = new Particula[qntpartic];
		this.ftmedia = new double[maxIter];
		this.ftgbest = new double[maxIter];

		// laço de repetição para a quantidade de particulas definidas
		for (int identificador = 0; identificador < qntpartic; identificador++) {
			vetor[identificador] = new Particula(); // criacao do objeto para cada espaço no vetor

			randomizarPos(identificador, tipoFuncao);
			randomizarVel(identificador);
			calcularFit(identificador, tipoFuncao);
			procurarPbest(identificador, tipoFuncao);
		}
		procurarGbest();
		setFitnessMedia();
		setFitnessGbest(qntpartic);
	}

	public void randomizarPos(int identif, int tipoFuncao) // funçao para randomizar posição
	{
		Coordenada posInicial = new Coordenada();
		posInicial.Setx1((Math.random() * 200) - 100); // definindo limite vertical de -100 a 100
		posInicial.Setx2((Math.random() * 200) - 100); // definindo limite horizontal de -100 a 100

		setCoordenadas(posInicial, identif, tipoFuncao); // função para colocar as coordenadas das particulas no vetor
	}

	public void setCoordenadas(Coordenada x, int identif, int tipoFuncao) // função para colocar coordenadas
	{
		vetor[identif].SetCoord(x);
		procurarPbest(identif, tipoFuncao); // gancho para procurar o pbest da posicao alterada
	}

	public void randomizarVel(int identif) // função para randomizar velocidade
	{
		Coordenada velInicial = new Coordenada();
		velInicial.Setx1((Math.random() * 200) - 100); // definindo limite vertical de -100 a 100
		velInicial.Setx2((Math.random() * 200) - 100); // definindo limite horizontal de -100 a 100
	}

	public double calcularFit(int identif, int tipoFuncao) // funçao para calcular fitness independente
	{ 														// do tipo de função desejada
		Ambiente a = new Ambiente();
		double fit = 0.0;
		
		if (tipoFuncao == 1) // esfera
		{
			vetor[identif].SetFitness(a.setEsf(vetor[identif].GetCoord())); //calculado pela funcao setEsf do Ambiente
			fit = a.setEsf(vetor[identif].GetCoord());
		} else {
			if (tipoFuncao == 2) // rosembrock
			{
				vetor[identif].SetFitness(a.setRos(vetor[identif].GetCoord()));
				fit = a.setRos(vetor[identif].GetCoord());
			} else {
				if (tipoFuncao == 3) // rastrigin
				{
					vetor[identif].SetFitness(a.setRas(vetor[identif].GetCoord()));
					fit = a.setRas(vetor[identif].GetCoord());
				}
			}
		}
		return fit; //retorno utilizado para calculo de pbest
	}

	public void procurarGbest() //função para calcular o gbest apos uma interação
	{

		if (gbest == null) // inserir caso for o primeiro gbest
		{
			gbest = new Particula();
			gbest.SetCoord(vetor[0].GetCoord());
			gbest.SetFitness(vetor[0].GetFitness());
		}
		for (int identif = 0; identif < vetor.length; identif++) //laço para passar em todas as posicoes do vetor
		{
			if (gbest.GetFitness() > vetor[identif].GetFitness())  // compara o valor da fitness
			{
				gbest.SetCoord(vetor[identif].GetCoord()); 
				gbest.SetFitness(vetor[identif].GetFitness());
			}
		}
	}

	public void setFitnessMedia() 
	{
		double fit = 0; // variavel para calculo de fitness media
		boolean flag = false; // flag para controlar inserção no vetor ftmedia

		for (int identif = 0; identif < vetor.length; identif++) 
		{ 
			fit += vetor[identif].GetFitness(); //somatoria de todos os fitness
		}
		for (int identif = 0; identif < ftmedia.length; identif++) //laço para achar posição vazia no vetor ftmedia
		{
			if (ftmedia[identif] == 0.0 && flag == false) //flag para deixar inserir apenas 1x 
			{
				ftmedia[identif] = fit / vetor.length; //soma das fitness dividido pela quantidade de particulas
				flag = true; // flag
				System.out.println("fitness media : " + ftmedia[identif]);
			}
		}
	}

	public void setFitnessGbest(int qntpartic)
	{
		boolean flag = false; //flag para evitar mais de 1 inserçao igual
		
		for (int identif = 0; identif < ftgbest.length; identif++) //achando posição vazia
		{
			if (ftgbest[identif] == 0.0 && flag == false) 
			{
				ftgbest[identif] = gbest.GetFitness();
				flag = true;
				System.out.println("fitness gbest : " + ftgbest[identif]);
			}
		}
	}

	public void procurarPbest(int identif, int tipoFuncao)
	{
		// teste para inserir pbest na primeira interação
		if (vetor[identif].GetPbest().Getx1() == 0.0 || vetor[identif].GetPbest().Getx2() == 0.0) {
			vetor[identif].SetPbest(vetor[identif].GetCoord());

		} else { // teste fit da nova coordenada com a antiga, para atualizar o pbest da posição
			double fitAnt = vetor[identif].GetFitness();
			double fitNovo = calcularFit(identif, tipoFuncao);

			if (fitAnt > fitNovo) 
			{
				vetor[identif].SetPbest(vetor[identif].GetCoord());
			}
			System.out.println("pbest na pos " + identif + ": (" + vetor[identif].GetPbest().Getx1() + ", "
					+ vetor[identif].GetPbest().Getx2() + ")");
		}
	}

	public void calcVelocidade(double w, double c1, double c2, int qntpartic, int tipoFuncao) 
	{
		//objetos criados para facilitar compreensao
		Coordenada velocidade = new Coordenada();
		Coordenada novaPos = new Coordenada();
		Coordenada inercia = new Coordenada();
		Coordenada cognitivo = new Coordenada();
		Coordenada social = new Coordenada();

		for (int identif = 1; identif < vetor.length; identif++) 
		{
			// calculo de inercia
			inercia.Setx1(w * vetor[identif].GetVel().Getx1());
			inercia.Setx2(w * vetor[identif].GetVel().Getx2());
			// calculo cognitivo
			cognitivo.Setx1(c1 * (vetor[identif].GetPbest().Getx1() - vetor[identif].GetCoord().Getx1()));
			cognitivo.Setx2(c1 * (vetor[identif].GetPbest().Getx2() - vetor[identif].GetCoord().Getx2()));
			// calculo social
			social.Setx1(c2 * (gbest.GetCoord().Getx1() - vetor[identif].GetCoord().Getx1()));
			social.Setx2(c2 * (gbest.GetCoord().Getx2() - vetor[identif].GetCoord().Getx2()));
			//soma do cognitivo + inercia + social
			velocidade.Setx1(inercia.Getx1() + cognitivo.Getx1() + social.Getx1());
			velocidade.Setx2(inercia.Getx2() + cognitivo.Getx2() + social.Getx2());
			//atualizando a velocidade da particula
			vetor[identif].SetVel(velocidade);

			novaPos.Setx1(velocidade.Getx1() + vetor[identif].GetCoord().Getx1());
			novaPos.Setx2(velocidade.Getx2() + vetor[identif].GetCoord().Getx2());

			// setando novas coordenadas
			setCoordenadas(novaPos, identif, tipoFuncao);


			System.out.println("posiçao nova " + identif + ": (" + novaPos.Getx1() + ", " + novaPos.Getx2() + ")");
			System.out.println("=======================================================");
			calcularFit(identif, tipoFuncao);
		}

	}
	public void reproduzirEnxame(double w, double c1, double c2, int qntpartic, int tipoFuncao, int maxIter)
	{
		for(int i = 0; i < maxIter; i++) {
		calcVelocidade(w, c1, c2, qntpartic, tipoFuncao);
		procurarGbest(); 
		setFitnessMedia();
		setFitnessGbest(qntpartic);
		}
	}
	
}
