//Vrinda Mittal
import java.io.*;
import java.util.*;


abstract class Animal implements Comparable<Animal>{
	protected int health;
	protected int[] coords;
	protected int timeS;
	protected int isdead;
	protected Grassland g1;
	protected Grassland g2;

	public Animal(int h,int x,int y,int ts,Grassland g1,Grassland g2){
		this.health=h;
		this.coords=new int[2];
		coords[0]=x;
		coords[1]=y;
		int timeS=ts;
		this.g1=g1;
		this.g2=g2;
		isdead=1;
	}

	public int compareTo(Animal a2){
		if(showTS()<a2.showTS()){
			return 1;
		}
		else if(showTS()>a2.showTS()){
			return -1;
		}

		else{
			if(showH()<a2.showH()){
				return -1;
			}
			else if(showH()>a2.showH()){
				return 1;
			}
			else{
				if(getClass()==a2.getClass()){
					int[] origin={0,0};
					if(getDistance(origin)<a2.getDistance(origin)){
						return 1;
					}
					else if(getDistance(origin)<a2.getDistance(origin)){
						return -1;
					}
					else{
						return 0;
					}

				}

				else if(this.toString().split(" ")[0] =="Herbivore" && a2.toString().split()[0]=="Carnivore"){
					return 1;
				}
				else{
					return -1;
				}
			}
		}




	}


	public int is_dead(){ //tell weather  dead or not
		return isdead;
	}

	public int[] showC(){          // returns current coordinates
		return coords;
	}

	public int showH(){    //returns current health
		return health;
	}

	public int showTS(){
		return timeS;
	}
	public void updateC(int[] newC){
		coords[0]=newC[0];
		coords[1]=newC[1];
	}

	public void newTS(int t){
		timeS=t;
	}

	public int updateH(int h){
		health=h;
	}


	public void setDeath(){
		System.out.println(toString+"is dead.")
		isdead=0;
	}

	public int getDistance(int[] point){
		double t=(double)((coords[0]-point[0])*(coords[0]-point[0])+(coords[1]-point[1])*(coords[1]-point[1]));
		int val=(int)Math.floor(Math.sqrt(t));
		return val;

	}

	public boolean inGrassland(){
		
		int d1=getDistance(g1.getCoords());
		int d2=getDistance(d1.getCoords());

		if(d1<=g1.getRadius() || d2<=g2.getRadius()){
			return true;
		}

	}
	protected Grassland showGrass(){
		d1=getDistance(g1.getCoords());
		d2=getDistance(g2.getCoords());
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
	protected void runToward(int steps,int[] point){
		int d=getDistance(point);
		d=d-steps;
		int valx=(int)(steps*point[0]+d*coords[0])/(steps+d);
		int valy=(int)(steps*point[0]+d*coords[0])/(steps+d);
		coords[0]=valx;
		coords[1]=valy;

	}

	protected void runAway(int steps,int[] point){
		int d=getDistance(point);
		d=d+steps;
		int valx=(int)(((d*coords[0])-(steps*point[0]))/(d-steps));
		int valy=(int)(((d*coords[1])-(steps*point[1]))/(d-steps));
		coords[0]=valx;
		coords[1]=valy;
	}

	public abstract int takeT();

	public abstract void uTurn();

}

class Herbivore extends Animal{
	int capH;
	Carnivore car1;
	Carnivore car2;
	int gTurn;
	static int nHerbivore;

	public Herbivore(int h,int x,int y,int ts,Grassland g1,Grassland g2,int capH,Carnivore c1,Carnivore c2){
		super(h,x,y,ts,g1,g2);
		this.capH=capH;
		car1=c1;
		car2=c2;
		nHerbivore+=1;
	}

	@Override
	public void uTurn(){
		if(inGrassland()){
			gTurn=0;
		}
		else if(gTurn>7){
			updateH(health-5);
			gTurn+=1;
		}
	}

	

	protected Carnivore showCar(){
		d1=getDistance(car1.showC());
		d2=getDistance(car2.showC());
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
		if(car1.is_dead()==0 && car2.is_dead()){
			Random rand=new Random();
			int prob=rand.nextInt(2);

			if(prob==0){ //goes to grass land
				if(inGrassland()){ //currently in grassland
					updateH(health-25); //chooses not to stay
					grassland g=showGrass();
					if(g==g1){
						g=g2;
					}
					else{
						g=g1;
					}

					runToward(5,g.getCoords());

				}
				else{ //currently in forest
					runToward(5,showGrass().getCoords());

				}
			}

			else if(prob==1){  // stays in grassland
				if(inGrassland()){
				Grassland g=showGrass();//in grass land eat -update health
				if(g.getA()>=capH){
					updateH(health+health*0.5);
					g.updateA(g.getA()-capH);
				}
				else{
					updateH(health+health*0.2);
					g.updateA(0);
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
						updateH(health+0.5*health);
						g.updateA(g.getA()-capH);
					}
					else{
						updateH(health-0.25*health);
						int prob2=rand.nextInt(2)+1;
						if(prob2==1){
							runAway(2,showCar().showC());
						}
						else{
							if(g==g1){
								g=g2;
							}
							else{
								g=g1;
							}
							runToward(3,g.getCoords());
						}
					}
					

				}
				else{  
					Random rand=new Random();
					int prob=rand.nextInt(10)+1;         // not enough grass
					
					if (prob<=2){
						updateH(health+health*0.2);
						g.updateA(0);//stays +eats +health	
					}
					else{
						updateH(health-25); //does not stay
						int prob2=rand.nextInt(10)+1;
						
						if(prob2<=7){
							runAway(4,showCar().showC());//runs carnivore +4 steps
						}
						else{
							if(g==g1){
								g=g2;
							}
							else{
								g=g1;
							}
							runToward(2,g.getCoords());  //other grassland +2 step
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
						runToward(5,showGrass().getCoords());
						//to grassland-5 steps
					}
					else{
						runAway(4,showCar().showC());
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
		return "Herbivore "+nHerbivore;
	}

}

class Carnivore extends Animal{
	Static int nCarnivore;
	Herbivore her1;
	Herbivore her2;
	int hTurn;

	public Carnivore(int h.int x,int y,int ts,Grassland g1,Grassland g2,Herbivore h1,Herbivore h2){
		super(h,x,y,ts,g1,g2,h1,h2);
		her1=h1;
		her2=h2;
		nCarnivore+=1;
	}
	@Override
	public void uTurn(){
		Herbivore h=showH();
		int d=getDistance(h.showC);
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
	protected Herbivore showH(){
		d1=getDistance(her1.showC());
		d2=getDistance(her2.showC());
		if(d1<d2 && her1.is_dead()==1){
			return her1;

		}
		else{
			return her2;
		}
	}

	@Override
	public void takeT(){
		uTurn();
		if (her1.is_dead==0 && her2.is_dead==0){    //all herbivores dead

			if(inGrassland()){  //inside a grassland
				updateH(health-30); //case:stay
			}
			else{  // not in grassland
				updateH(health-60); //case:stay
			}

		}
		else{                  //not all dead
			Herbivore h=showH();
			int d=getDistance(h.showC());
			if(d<=1){   //kills herbivore
				updateH(health+g.showH()*2/3);
				h.setDeath();
			}
			else{
				if(inGrassland()){ // if in grassland
					Random rand=new Random();
					prob=rand.nextInt(4)+1;
					if(prob==1){  //stays
						updateH(health-30);
					}
					else{        // does not stay
						runToward(2,showH().showC());						
					}

				}
				else{   //not in grassland
					Random rand=new Random();
					prob=nextInt(50)+1;
					if(prob<=4){   //stays
						updateH(health-60);
					}
					else{  //does not stay
						runToward(4,showH().showC());
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
		return "Carnivore "+nCarnivore;
	}

}
class Grassland{
	protected int availability;
	protected final int[] coordinates;
	protected final int radius;

	public grassland(int units,int xcoord,int ycoord,int r){
		this.availability=units;
		this.coordinates=new int[2];
		coordinates[0]=xcoord;
		coordinates[1]=ycoord;
		this.radius=r;
	}

	public int[] getCoords(){
		return coordinates;
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
	
	public static void main(String[] args)throws IOException{
		Scanner rd=new Scanner(System.in);
		System.out.println("Enter Total Final Time for Simulation: ");
		
		keeptime=rd.nextInt();
		
		System.out.println("Enter x,y centre, radius and Grass Available for First Grassland: ");
		
		int xcoord1=rd.nextInt();
		int ycoord1=rd.nextInt();
		int radii1=rd.nextInt();
		int units1=rd.nextInt();

		grassland g1=new grassland(units1,xcoord1,ycoord1,radii1);
		
		System.out.println("Enter x,y centre, radius and Grass Available for Second Grassland: ");
		
		int xcoord2=rd.nextInt();
		int ycoord2=rd.nextInt();
		int radii2=rd.nextInt();
		int units2=rd.nextInt();
		
		grassland g2=new grassland(units2,xcoord2,ycoord2,radii2);

		System.out.println("Enter Health and Grass Capacity for Herbivores: ");
		int healthH=rd.nextInt();
		int eatingH=rd.nextInt();

		System.out.println("Enter x,y position and timestamp for First Herbivore: ");
		int x1=rd.nextInt();
		int y1=rd.nextInt();
		int ts1=rd.nextInt();

		//instantiate first herbivore

		System.out.println("Enter x,y position and timestamp for Second Herbivore: ");
		int x2=rd.nextInt();
		int y2=rd.nextInt();
		int ts2=rd.nextInt();

		//instantiate second herbivore

		System.out.println("Enter Health for Carnivores: ");
		int healthC=rd.nextInt();

		System.out.println("Enter x,y position and timestamp for First Carnivore: ");
		int x1c=rd.nextInt();
		int y1c=rd.nextInt();
		int ts1c=rd.nextInt();

		//instantiate first carnivore

		System.out.println("Enter x,y position and timestamp for Second Carnivore: ");
		int x2c=rd.nextInt();
		int y2c=rd.nextInt();
		int ts2c=rd.nextInt();

		//instanciate second herbivore

		System.out.println("The Simulation Begins-");

		//main loop
		
	}	
}