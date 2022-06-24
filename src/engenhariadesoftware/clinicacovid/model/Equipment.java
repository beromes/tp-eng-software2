package engenhariadesoftware.clinicacovid.model;

public class Equipment {
	
	private String name;
	private int quantity;
	private boolean consumable;
	
	public Equipment(String name, int quantity, boolean consumable) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.consumable = consumable;
	}
	
	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void use() throws Exception {
		if(consumable && quantity > 0) {
			quantity--;
		}
		else{
			throw new Exception("Equipamento sem unidades disponíveis ou não consumível");
		}
	}
	
	public boolean canUse() {
		return quantity > 0 && consumable;
	}
	
	public void addUnits(int qtt) {
		quantity += qtt;
	}

}
