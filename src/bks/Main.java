package bks;

public class Main {
	
	public static void main(String[] args){
		Render render = new Render();
		Application app = new Application(render);
		app.run();
	}
	
}
