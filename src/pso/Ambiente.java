package pso;

public class Ambiente {

	private double esf;
	private double ros;
	private double ras;
	// definição de modificadores
	private double W = 0.5; // (inercia)
	private double C1 = 0.46; // cognitiva
	private double C2 = 2; // social

	public double setEsf(Coordenada c) //calculo do ponto otimo da esfera
	{
		this.esf = ((Math.pow(c.Getx1(), 2)) + (Math.pow(c.Getx2(), 2)));
		return this.esf;
	}

	public double setRos(Coordenada c) //calculo do ponto otimo da rosembrock
	{
		this.ros = (Math.pow(1 - c.Getx1(), 2) + 100*(Math.pow(c.Getx2()-Math.pow(c.Getx1(), 2), 2)));
		return this.ros;
	}

	public double setRas(Coordenada c) //calculo do ponto otimo da rastrigin
	{
		this.ras = 10 * 2 + ((c.Getx1() * c.Getx1()) - 10 * Math.cos(2 * Math.PI * c.Getx1()));
		this.ras += (c.Getx2() * c.Getx2()) - 10 * Math.cos(2 * Math.PI * c.Getx2());
		return this.ras;
	}

	public double getW()
	{
		return this.W;
	}

	public double getC1() 
	{
		return this.C1;
	}

	public double getC2() 
	{
		return this.C2;
	}

}
