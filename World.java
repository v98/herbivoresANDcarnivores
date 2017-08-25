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

				else if(this =="Herbivore" && a2=="Carnivore"){
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


}

class Herbivore extends Animal{
	int capH;
	Carnivore car1;
	Carnivore car2;
	int gTurn;

	public Herbivore(int h,int x,int y,int ts,Grassland g1,Grassland g2,int capH,Carnivore c1,Carnivore c2){
		super(h,x,y,ts,g1,g2);
		this.capH=capH;
		car1=c1;
		car2=c2;
	}

	public void uTurn(){
		if(inGrassland()){
			gTurn=0;
		}
		else if(gTurn>7){
			updateH(health-5);
			gTurn+=1;
		}
	}

	protected Grassland showG(){
		d1=getDistance(g1.getCoords());
		if(d1<=g1.getRadius()){
			return g1;
		}
		else{
			return g2;
		}
	}

	protected Carnivore showC(){
		d1=getDistance(car1.showC());
		d2=getDistance(car2.showC());
		if(d1<d2 && car1.isdead()==1){
			return car1;

		}
		else{
			return car2;
		}
	}

	@Override
	public int takeT(){
		uTurn();
		if(car1.isdead()==0 && car2.isdead()){
			Random rand=new Random();
			int prob=rand.nextInt(2);

			if(prob==0){
				//in grassland health(-25)
					//goes to grassland-5 steps
				
				//out of grassland	
					//goes to grassland -5 steps
			}

			else if(prob==1){
				//in grass land eat -update health


			}
			

		}

		else{
			if(inGrassland()){
				Grassland g=showG();
				if(g1.availability>=capH){  //enough grass
					Random rand=new Random();
					int prob=rand.nextInt();
					//stays +eats+health


					//does not stay
						//other grassland +3steps -25health
						//runs carnivore +2steps  -25health


				}
				else{  
					Random rand=new Random();
					int prob=rand.nextInt()+1;         // not enough grass
					
					if (prob<=2){
						//stays +eats +health	
					}
					else{

					//does not stay -health
						//runs carnivore +4 steps
						//other grassland +2 step

					}
				}

				
			}
			else{
				Random rand=new Random();
				int prob=rand.nextInt(20)+1;
				if(prob>1){
					int prob2=rand.nextInt(20)+1;
					if(prob2<=13){
						//to grassland-5 steps
					}
					else{
						//away from carnivore
					}
				}

			}

		}

	}

	@Override
	public String toString(){
		return "Herbivore";
	}

}

class Carnivore extends Animal{
	Herbivore her1;
	Herbivore her2;

	public Carnivore(int h.int x,int y,int ts,Grassland g1,Grassland g2,Herbivore h1,Herbivore h2){
		super(h,x,y,ts,g1,g2,h1,h2);
		her1=h1;
		her2=h2;
	}
	@Override
	public void takeT(){

	}

	@Override
	public String toString(){
		return "Carnivore";
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
		availability-=ategrass;
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