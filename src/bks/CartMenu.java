package bks;

public class CartMenu implements MenuAction{
	
	// Submenu to ask the user what they want to do in the cart menu
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
		// Sets the internal menu to a submenu (changes rendering)
		this.subMenu.subMenu = true;
	}

	// Executes when this action is chosen in a menu
	public boolean execute(){
		Render render = Application.GetRenderer();
		
		// While we should be in the menu, stay in it
		boolean run = true;
		while(run){
			// Instantiate the cart
			Cart cart = new Cart();
			
			// Get the products list from the cart, and map its data to a list of books
			Product cartProducts[] = cart.getProducts();
			Book books[] = new Book[cartProducts.length];
			
			for(int i = 0 ; i <  cartProducts.length; i++){
				books[i] = new Book(cartProducts[i]);
			}
			
			// Render the cart, and add a newline at the end
			render.cart(books);
			System.out.println("");
			
			// Get the user choice, and take an action based on that choice
			char choice = this.subMenu.getMenuChoice();
			run = this.subMenu.action(choice);
		}
		
		return true;
	}
	
}
