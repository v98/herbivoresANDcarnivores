//Vrinda Mittal
import java.io.*;
import java.util.*;


abstract class Animal implements Comparable<Animal>{
	protected float health;
	protected int[] coords;
	protected int timeS;
	protected int isdead;
	protected Grassland g1;
	protected Grassland g2;
	protected int type;

	public Animal(float h,int x,int y,int ts,Grassland g1,Grassland g2){
		this.health=h;
		this.coords=new int[2];
		coords[0]=x;
		coords[1]=y;
		timeS=ts;
		
		this.g1=g1;
		this.g2=g2;
		isdead=1;
	}
	protected abstract void settype();

	@Override
	public int compareTo(Animal a2){
		if(showTS()<a2.showTS()){
			return -1;
		}
		else if(showTS()>a2.showTS()){
			return 1;
		}

		else{
			if(showH()<a2.showH()){
				return 1;
			}
			else if(showH()>a2.showH()){
				return -1;
			}
			else{
				if(getClass()==a2.getClass()){
					//int[] origin={0,0};
					if(getDistance(0,0)<a2.getDistance(0,0)){
						return -1;
					}
					else if(getDistance(0,0)<a2.getDistance(0,0)){
						return 1;
					}
					else{
						return 0;
					}

				}

				else if(this.type==0){
					return -1;
				}
				else{
					return 1;	
				}
			}
		}




	}


	public int is_dead(){ //tell weather  dead or not
		return isdead;
	}

	public int showCx(){
	        // returns current coordinates
		return coords[0];
	}
	public int showCy(){
	        // returns current coordinates
		return coords[1];
	}

	public float showH(){    //returns current health
		return health;
	}

	public int showTS(){
		//System.out.println(timeS);
		return timeS;
	}
	public void updateC(int newCx,int newCy){
		coords[0]=newCx;
		coords[1]=newCy;
	}

	public void newTS(int t){
		timeS=t;
	}

	public void updateH(float h){
		health=h;
	}


	public void setDeath(){
		//System.out.println(toString()+"is dead.");
		isdead=0;
	}

	public int getDistance(int pointx,int pointy){
		double t=(double)((coords[0]-pointx)*(coords[0]-pointx)+(coords[1]-pointy)*(coords[1]-pointy));
		int val=(int)Math.floor(Math.sqrt(t));
		return val;

	}

	public boolean inGrassland(){
		
		int d1=getDistance(g1.getCoordx(),g1.getCoordy());
		int d2=getDistance(g2.getCoordx(),g2.getCoordy());

		if(d1<=g1.getRadius() || d2<=g2.getRadius()){
			return true;
		}
		return false;

	}
	protected Grassland showGrass(){
		int d1=getDistance(g1.getCoordx(),g1.getCoordy());
		int d2=getDistance(g2.getCoordx(),g2.getCoordy());
		if(inGrassland()){
			if(d1<=g1.getRadius()){
				return g1;
			}
			else{
				return g2;
			}
		}
		
		else{
			if(d1<=d2){
				return g1;
			}
			else{
				return g2;
			}
		}
	}
	protected void runToward(int steps,int pointx,int pointy){
		int d=getDistance(pointx,pointy);
		d=d-steps;
		int valx=(int)(steps*pointx+d*coords[0])/(steps+d);
		int valy=(int)(steps*pointy+d*coords[1])/(steps+d);
		updateC(valx,valy);

	}

	protected void runAway(int steps,int pointx,int pointy){
		int d=getDistance(pointx,pointy);
		d=d+steps;
		int valx=(int)(((d*coords[0])-(steps*pointx))/(d-steps));
		int valy=(int)(((d*coords[1])-(steps*pointy))/(d-steps));
		updateC(valx,valy);
	}

	public abstract int takeT();

	public abstract void uTurn();

}

class Herbivore extends Animal{
	protected int capH;
	protected Carnivore car1;
	protected Carnivore car2;
	protected int gTurn;
	protected static int nHerbivore;
	protected int ndata;
	

	public Herbivore(float h,int x,int y,int ts,Grassland g1,Grassland g2,int capH){
		super(h,x,y,ts,g1,g2);
		this.capH=capH;
		//car1=c1;
		//car2=c2;
		nHerbivore+=1;
		ndata=nHerbivore;
		settype();
	}

	public void setCarnivores(Carnivore c1,Carnivore c2){
		car1=c1;
		car2=c2;
	}
	@Override
	protected void settype(){
		type=0;
	}

	@Override
	public void uTurn(){
		if(inGrassland()){
			gTurn=0;
		}
		else if(gTurn>=7){
			updateH(health-5);
			gTurn+=1;
		}
	}

	

	protected Carnivore showCar(){
		int d1=getDistance(car1.showCx(),car1.showCy());
		int d2=getDistance(car2.showCx(),car2.showCy());
		if(d1<d2 && car1.is_dead()==1){
			return car1;

		}
		else{
			return car2;
		}
	}

	@Override
	public int takeT(){
		uTurn();
		if(car1.is_dead()==0 && car2.is_dead()==0){
			Random rand=new Random();
			int prob=rand.nextInt(2);

			if(prob==0){ //goes to grass land
				if(inGrassland()){ //currently in grassland
					updateH(health-25); //chooses not to stay
					Grassland g=showGrass();
					if(g==g1){
						g=g2;
					}
					else{
						g=g1;
					}

					runToward(5,g.getCoordx(),g.getCoordy());

				}
				else{ //currently in forest
					runToward(5,showGrass().getCoordx(),showGrass().getCoordy());

				}
			}

			else if(prob==1){  // stays in grassland
				if(inGrassland()){
				Grassland g=showGrass();//in grass land eat -update health
				if(g.getA()>=capH){
					updateH(health+health/2);
					g.updateA(g.getA()-capH);
				}
				else{
					if(g.getA()>0){
						updateH(health+health/5);
						g.updateA(0);
					}
					
				}
				}

			}
			

		}

		else{
			if(inGrassland()){
				Grassland g=showGrass();
				if(g.getA()>=capH){  //enough grass
					Random rand=new Random();
					int prob=rand.nextInt(10)+1;
					
					if(prob<=9){//stays +eats+health
						updateH(health+health/2);
						g.updateA(g.getA()-capH);
					}
					else{
						updateH(health-25);
						int prob2=rand.nextInt(2)+1;
						if(prob2==1){
							runAway(2,showCar().showCx(),showCar().showCy());
						}
						else{
							if(g==g1){
								g=g2;
							}
							else{
								g=g1;
							}
							runToward(3,g.getCoordx(),g.getCoordy());
						}
					}
					

				}
				else{  
					Random rand=new Random();
					int prob=rand.nextInt(10)+1;         // not enough grass
					
					if (prob<=2){
						if(g.getA()>0){
							updateH(health+health/5);
							g.updateA(0);//stays +eats +health
						}
							
					}
					else{
						updateH(health-25); //does not stay
						int prob2=rand.nextInt(10)+1;
						
						if(prob2<=7){
							runAway(4,showCar().showCx(),showCar().showCy());//runs carnivore +4 steps
						}
						else{
							if(g==g1){
								g=g2;
							}
							else{
								g=g1;
							}
							runToward(2,g.getCoordx(),g.getCoordy());  //other grassland +2 step
						}
					}
				}
			}
			else{  //currently not in grassland
				Random rand=new Random();
				int prob=rand.nextInt(20)+1;
				if(prob>1){
					int prob2=rand.nextInt(20)+1;
					if(prob2<=13){
						runToward(5,showGrass().getCoordx(),showGrass().getCoordy());
						//to grassland-5 steps
					}
					else{
						runAway(4,showCar().showCx(),showCar().showCy());
						//away from carnivore
					}
				}

			}

		}
		if(showH()<=0){
			setDeath();
		}
		return is_dead();

	}

	@Override
	public String toString(){
		return "Herbivore "+ndata;
	}

}

class Carnivore extends Animal{
	static int nCarnivore;
	protected Herbivore her1;
	protected Herbivore her2;
	protected int hTurn;
	protected int ndata;

	public Carnivore(float h,int x,int y,int ts,Grassland g1,Grassland g2){
		super(h,x,y,ts,g1,g2);
		//her1=h1;
		//her2=h2;
		nCarnivore+=1;
		ndata=nCarnivore;
		settype();
	}

	public void setHerbivores(Herbivore h1,Herbivore h2){
		her1=h1;
		her2=h2;
		// System.out.println(her1);
		// System.out.println(her2);
		
	}

	@Override
	protected void settype(){
		type=1;
	}
	@Override
	public void uTurn(){
		Herbivore h=showHerb();
		int d=getDistance(h.showCx(),h.showCy());
		if(d<=5){
			hTurn=0;
		}
		else{
			if(hTurn>=7){
				updateH(health-6);
			}
			hTurn+=1;
		}
	}
	protected Herbivore showHerb(){
		// int[] temp1=her1.showC();
		// int[] temp2=her2.showC();
		// System.out.println(temp1[1]);
		int d1=getDistance(her1.showCx(),her1.showCy());
		int d2=getDistance(her1.showCx(),her1.showCy());
		if(d1<d2 && her1.is_dead()==1){
			return her1;

		}
		else{
			return her2;
		}
		//return her1;
	}

	@Override
	public int takeT(){
		uTurn();
		if (her1.is_dead()==0 && her2.is_dead()==0){    //all herbivores dead

			if(inGrassland()){  //inside a grassland
				updateH(health-30); //case:stay
			}
			else{  // not in grassland
				updateH(health-60); //case:stay
			}

		}
		else{                  //not all dead
			Herbivore h=showHerb();
			int d=getDistance(h.showCx(),h.showCy());
			if(d<=1){   //kills herbivore
				updateH(health+h.showH()*2/3);
				h.setDeath();
			}
			else{
				if(inGrassland()){ // if in grassland
					Random rand=new Random();
					int prob=rand.nextInt(4)+1;
					if(prob==1){  //stays
						updateH(health-30);
					}
					else{        // does not stay
						runToward(2,showHerb().showCx(),showHerb().showCy());						
					}

				}
				else{   //not in grassland
					Random rand=new Random();
					int prob=rand.nextInt(50)+1;
					if(prob<=4){   //stays
						updateH(health-60);
					}
					else{  //does not stay
						runToward(4,showHerb().showCx(),showHerb().showCy());
					}


				}
			}

		}
		if(showH()<=0){
			setDeath();
		}
		return is_dead();

	}

	@Override
	public String toString(){
		return "Carnivore "+ndata;
	}

}
class Grassland{
	protected int availability;
	protected final int[] coordinates;
	protected final int radius;

	public Grassland(int units,int xcoord,int ycoord,int r){
		this.availability=units;
		this.coordinates=new int[2];
		coordinates[0]=xcoord;
		coordinates[1]=ycoord;
		this.radius=r;
	}

	public int getCoordx(){
		return coordinates[0];
	}

	public int getCoordy(){
		return coordinates[1];
	}

	public int getRadius(){
		return radius;
	}

	public int getA(){
		return availability;
	}

	public void updateA(int ategrass){
		availability=ategrass;
	}

	
}

public class World{

	static int keeptime;
	static int maxTS;
	
	public static void main(String[] args)throws IOException{
		Scanner rd=new Scanner(System.in);
		System.out.println("Enter Total Final Time for Simulation: ");
		
		keeptime=rd.nextInt();
		
		System.out.println("Enter x,y centre, radius and Grass Available for First Grassland: ");
		
		int xcoord1=rd.nextInt();
		int ycoord1=rd.nextInt();
		int radii1=rd.nextInt();
		int units1=rd.nextInt();

		Grassland g1=new Grassland(units1,xcoord1,ycoord1,radii1);
		
		System.out.println("Enter x,y centre, radius and Grass Available for Second Grassland: ");
		
		int xcoord2=rd.nextInt();
		int ycoord2=rd.nextInt();
		int radii2=rd.nextInt();
		int units2=rd.nextInt();
		
		Grassland g2=new Grassland(units2,xcoord2,ycoord2,radii2);

		System.out.println("Enter Health and Grass Capacity for Herbivores: ");
		int healthH=rd.nextInt();
		int eatingH=rd.nextInt();

		System.out.println("Enter x,y position and timestamp for First Herbivore: ");
		int x1=rd.nextInt();
		int y1=rd.nextInt();
		int ts1=rd.nextInt();

		Herbivore h1=new Herbivore((float)healthH,x1,y1,ts1,g1,g2,eatingH);//instantiate first herbivore
		maxTS=Math.max(ts1,maxTS);

		System.out.println("Enter x,y position and timestamp for Second Herbivore: ");
		int x2=rd.nextInt();
		int y2=rd.nextInt();
		int ts2=rd.nextInt();

		Herbivore h2=new Herbivore((float)healthH,x2,y2,ts2,g1,g2,eatingH);//instantiate second herbivore
		maxTS=Math.max(ts2,maxTS);

		System.out.println("Enter Health for Carnivores: ");
		int healthC=rd.nextInt();

		System.out.println("Enter x,y position and timestamp for First Carnivore: ");
		int x1c=rd.nextInt();
		int y1c=rd.nextInt();
		int ts1c=rd.nextInt();

		Carnivore c1=new Carnivore((float)healthC,x1c,y1c,ts1c,g1,g2);//instantiate first carnivore
		maxTS=Math.max(ts1c,maxTS);

		System.out.println("Enter x,y position and timestamp for Second Carnivore: ");
		int x2c=rd.nextInt();
		int y2c=rd.nextInt();
		int ts2c=rd.nextInt();

		Carnivore c2=new Carnivore((float)healthC,x2c,y2c,ts2c,g1,g2);//instanciate second herbivore
		maxTS=Math.max(ts2c,maxTS);

		h1.setCarnivores(c1,c2);
		h2.setCarnivores(c1,c2);
		c1.setHerbivores(h1,h2);
		c1.setHerbivores(h1,h2);

		System.out.println("The Simulation Begins-");

		PriorityQueue<Animal> q1=new PriorityQueue<Animal>();

		q1.add(h1);
		q1.add(h2);
		q1.add(c1);
		q1.add(c2);

		int count=0;

		while(q1.peek()!=null || count<=keeptime){
			Animal temp=q1.remove();
			System.out.println("It is turn of "+temp+".");
			int v=temp.takeT();
			if (v==1){
				System.out.println("It's health after taking turn is "+temp.showH()+".");
				Random rant=new Random();
				int t=rant.nextInt(keeptime-maxTS)+maxTS+1;
				temp.newTS(t);
				maxTS=Math.max(maxTS,t);
				q1.add(temp);
			}
			else{
				System.out.println("It is dead.");
			}
			count+=1;
		}

		
		// Animal head=q1.remove();
		// System.out.println(head+" "+head.showTS());

		// Animal head2=q1.remove();
		// System.out.println(head2+" "+head2.showTS());

		// Animal head3=q1.remove();
		// System.out.println(head3+" "+head3.showTS());

		// Animal head4=q1.remove();
		// System.out.println(head4+" "+head4.showTS());
		// // for(Animal a:q1){
		// 	System.out.println(a);
		// }

		//main loop

	}	
}