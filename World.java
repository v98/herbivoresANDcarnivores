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




	}

	public int is_dead(){
		return isdead;
	}

	public int[] showC(){
		return coords;
	}

	public int showH(){
		return health;
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

	public abstract void takeT();
}

class Herbivore extends Animal{
	int capH;
	Carnivore car1;
	Carnivore car2;
	public Herbivore(int h,int x,int y,int ts,Grassland g1,Grassland g2,int capH,Carnivore c1,Carnivore c2){
		super(h,x,y,ts,g1,g2);
		this.capH=capH;
		car1=c1;
		car2=c2;
	}

	@Override
	public void takeT(){

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