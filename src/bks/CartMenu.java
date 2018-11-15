package bks;

public class CartMenu implements MenuAction{
	
	private Menu subMenu = new Menu(
			"Enter your choice",
			new char[] {
				'd',
				'e',
				'q'
			},
			new String[] {
				"Delete item",
				"Edit item",
				"Return to member menu"
			},
			new MenuAction[] {
				new CartDelete(),
				new CartEdit(),
				new Quit()
			}
	);
	
	CartMenu(){
		this.subMenu.subMenu = true;
	}

	public boolean execute(){
		DBConnection db = Application.GetDB();
		Render render = Application.GetRenderer();
		
		boolean run = true;
		while(run){
			Cart cart = new Cart();
			
			Product cartProducts[] = cart.getProducts();
			Book books[] = new Book[cartProducts.length];
			
			for(int i = 0 ; i <  cartProducts.length; i++){
				books[i] = new Book(cartProducts[i]);
			}
			
			render.cart(books);
			System.out.println("");
			
			char choice = this.subMenu.getMenuChoice();
			run = this.subMenu.action(choice);
		}
		
		return true;
	}
	
}
