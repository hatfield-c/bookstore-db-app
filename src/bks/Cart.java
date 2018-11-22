package bks;

// Data object to interact with the cart in the DB
public class Cart {
	DBConnection db;
	Render render;
	
	// The cart gets its own copies of the DB and renderer on instantiation
	Cart(){
		this.db = Application.GetDB();
		this.render = Application.GetRenderer();
	}
	
	// Deletes all products in the cart
	public void dumpCart(){
		try{
			db.delete(
				"cart", 
				new Condition(
					new String[] { "userid" }, 
					new String[] { Login.CurrentUserId() }
				)
			);
		}catch(Exception e){
			this.render.error("Could not clear the cart.");
		}
	}
	
	// Returns an array of all the products currently in the cart
	public Product[] getProducts(){
		QueryData cartData[];
		Product products[];
		
		try{
			// Try to read all the products current in this user's cart
			cartData = this.db.read(
				"cart", 
				new String[]{
					"isbn",
					"qty"
				}, 
				new Condition(
					new String[] {
						"userid"
					},
					new String[]{
						Login.CurrentUserId()
					}
				)
			);
			
			// Instantiate the product list
			products = new Product[cartData.length];
			
			// Map the results to each product list entry
			for(int i = 0; i < cartData.length; i++){
				String isbn = cartData[i].getData()[0];
				int qty = Integer.parseInt(cartData[i].getData()[1]);
				products[i] = new Product(isbn, qty);
			}
		} catch(Exception e){
			// If there's a problem, return an empty list
			products = new Product[0];
		}
		
		return products;
	}
	
	// Adds a product to the cart
	public void addProduct(Product product){
		boolean success;
		
		try{
			// Check if the product already exists in the cart - if it doesn't, add as a new product, otherwise edit the existing
			// product, and use its return value to determine if it was successful
			if(!this.checkForProduct(product))
				success = this.newProduct(product);
			else
				success = this.editProduct(product);
			
		}catch(Exception e){
			// If there was a problem, we've failed
			success = false;
		}
		
		// If we failed, render an error and exit
		if(!success){
			this.render.error("Could not add the product.");
			return;
		}
		
		// We've succeeded - render a success message
		this.render.success("Products have been added to the cart!");
	}
	
	// Edits a product in the cart
	public boolean editProduct(Product product){
		boolean success;
		
		try{
			// Check if the product exists in the cart or no
			if(!this.checkForProduct(product)){
				this.render.error("Product not found in cart.");
				throw new Exception("Product does not exist in the cart.");
			}
			
			// Tries to update the product record currently in the cart, overwriting its data with the
			// new data provided
			success = this.db.update(
					"cart",
					new String[] {
						"qty"
					},
					new String[] {
						Integer.toString(product.getQty()),
					},
					new Condition(
						new String[] { "userid", "isbn" },
						new String[] { Login.CurrentUserId(), product.getIsbn() }
					)
				);
			
		}catch(Exception e){
			// If there was a problem, we failed
			success = false;
		}
		
		// If we failed, render an error and exit
		if(!success){
			this.render.error("Unable to update product quantity.");
			return false;
		}
		
		// We've succeeded, render a success message and return true to signify success
		this.render.success("Updated product quantity.");
		return true;
	}
	
	// Adds a new product to the cart - only called when it is guaranteed the product doesn't already exist
	private boolean newProduct(Product product){
		
		try{
			// Tries to add the new product to the cart, returning its results
			return this.db.insert(
				"cart", 
				new String[] { 
					Login.CurrentUserId(), 
					product.getIsbn(), 
					Integer.toString(product.getQty()) }
			);
		}catch(Exception e){
			// If we've failed, returned false to let the application know
			return false;
		}
	}
	
	// Deletes the product given from the cart
	public void deleteProduct(Product product){
		boolean success;
		
		try{
			// Check that the product is in the cart
			if(!this.checkForProduct(product)){
				this.render.error("Product not found in cart.");
				throw new Exception("Product does not exist in the cart.");
			}
			
			// Try to delete that product from the database
			success = this.db.delete(
				"cart", 
				new Condition(
					new String[] { 
						"userid", 
						"isbn" 
					}, 
					new String[] { 
						Login.CurrentUserId(), 
						product.getIsbn() 
					}
				)
			);
		} catch(Exception e){
			// If there was a problem, we failed
			success = false;
		}
		
		// If we failed, output an error message and return
		if(!success){
			this.render.error("Could not delete product.");
			return;
		}
		
		// We've succeeded - output a success message
		this.render.success("Product has been removed from cart.");
	}
	
	// Checks if the product given is in the cart or not
	private boolean checkForProduct(Product product){
		try {
			// Tries to read the product record from the cart in the db
			QueryData[] cartRecord = this.db.read(
				"cart", 
				new String[] {
					"isbn",
					"qty"
				}, 
				new Condition(
					new String[] { "userid", "isbn" },
					new String[] { Login.CurrentUserId(), product.getIsbn() }
				)
			);
			
			// If no results returned, the product is not in the cart
			if(cartRecord.length < 1){
				return false;
			}
		} catch(Exception e){
			// If there was a problem, assume product is not in the cart
			return false;
		}
		
		// The product is in the cart - return true
		return true;
	}
	
}
