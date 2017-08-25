//Vrinda Mittal
import java.io.*;
import java.util.*;

class grassland{
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


	}	
}