package pso;


public class Particula {
	Coordenada cord = new Coordenada();
	Coordenada vel = new Coordenada();
	double Fitness = 0;
	Coordenada pbest = new Coordenada();
	
	public Particula() 
	{
	}
	
	public void SetFitness(double x) 
	{
		this.Fitness = x;
	}
	public double GetFitness() 
	{
		return this.Fitness;
	}
	public void SetCoord(Coordenada x) 
	{
		this.cord.Setx1(x.Getx1());
		this.cord.Setx2(x.Getx2());
	}
	public Coordenada GetCoord() {
		
		return cord;
	}
	public void SetVel(Coordenada x) {
		this.vel.Setx1(x.Getx1());
		this.vel.Setx2(x.Getx2());
	}
	public Coordenada GetVel() {
		return vel;
	}
	public void SetPbest(Coordenada x) {
		this.pbest.Setx1(x.Getx1());
		this.pbest.Setx2(x.Getx2());
	}
	public Coordenada GetPbest() {
		return pbest;
	}
}
