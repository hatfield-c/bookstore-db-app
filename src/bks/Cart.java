package bks;

public class Cart {
	DBConnection db;
	Render render;
	
	Cart(){
		this.db = Application.GetDB();
		this.render = Application.GetRenderer();
	}
	
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
	
	public Product[] getProducts(){
		QueryData cartData[];
		Product products[];
		
		try{
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
			
			products = new Product[cartData.length];
			
			for(int i = 0; i < cartData.length; i++){
				String isbn = cartData[i].getData()[0];
				int qty = Integer.parseInt(cartData[i].getData()[1]);
				products[i] = new Product(isbn, qty);
			}
		} catch(Exception e){
			products = new Product[0];
		}
		
		return products;
	}
	
	public void addProduct(Product product){
		boolean success;
		
		try{

			if(!this.checkForProduct(product))
				success = this.newProduct(product);
			else
				success = this.editProduct(product);
			
		}catch(Exception e){
			success = false;
		}
		
		if(!success){
			this.render.error("Could not add the product.");
			return;
		}
		
		this.render.success("Products have been added to the cart!");
	}
	
	public boolean editProduct(Product product){
		boolean success;
		
		try{
			if(!this.checkForProduct(product)){
				this.render.error("Product not found in cart.");
				throw new Exception("Product does not exist in the cart.");
			}
			
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
			success = false;
		}
		
		if(!success){
			this.render.error("Unable to update product quantity.");
			return false;
		}
		
		this.render.success("Updated product quantity.");
		return true;
	}
	
	private boolean newProduct(Product product){
		
		try{
			return this.db.insert(
				"cart", 
				new String[] { 
					Login.CurrentUserId(), 
					product.getIsbn(), 
					Integer.toString(product.getQty()) }
			);
		}catch(Exception e){
			return false;
		}
	}
	
	public void deleteProduct(Product product){
		boolean success;
		
		try{
			if(!this.checkForProduct(product)){
				this.render.error("Product not found in cart.");
				throw new Exception("Product does not exist in the cart.");
			}
			
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
			success = false;
		}
		
		if(!success){
			this.render.error("Could not delete product.");
			return;
		}
		
		this.render.success("Product has been removed from cart.");
	}
	
	private boolean checkForProduct(Product product){
		try {
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
			
			if(cartRecord.length < 1){
				return false;
			}
		} catch(Exception e){
			return false;
		}
		
		return true;
	}
	
}
