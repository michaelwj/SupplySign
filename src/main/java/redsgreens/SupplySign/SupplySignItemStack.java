package redsgreens.SupplySign;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class SupplySignItemStack {

	private Material material;
	private Short durability;
	private Integer amount;

	public SupplySignItemStack(Material m, Short d, Integer a)
	{
		material = m;
		durability = d;
		amount = a;
	}

	public ItemStack getItemStack()
	{
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		if (itemMeta instanceof Damageable) {
			((Damageable) itemMeta).setDamage(durability);
			itemStack.setItemMeta(itemMeta);
		}

		return itemStack;
	}
	
	public Short getDurability()
	{
		return durability;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public Integer getAmount()
	{
		return amount;
	}
	
	public boolean equals(Object other) 
	{
	    if (this == other)
	      return true;
	    if (!(other instanceof SupplySignItemStack))
	      return false;
	    SupplySignItemStack otherIS = (SupplySignItemStack) other;
	    return (this.material == otherIS.getMaterial() && this.durability == otherIS.getDurability() && this.amount == otherIS.getAmount()); 
	}

	public int hashCode() { 
		return this.material.hashCode() + this.durability.hashCode();	
	}
	
	public String toString()
	{
		return "Material=" + material.name().toLowerCase() + ", Durability=" + durability.toString() + ", Amount=" + amount.toString();
	}
}
