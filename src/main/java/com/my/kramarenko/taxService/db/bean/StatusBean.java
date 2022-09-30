package ua.nure.kramarenko.SummaryTask4.db.bean;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual manufacturer table
 * 
 * @author Vlad Kramarenko
 */
public class ManufacturerBean extends Entity {

	private static final long serialVersionUID = -7050931886531217891L;
	private String name;
	private boolean selected = false;

	public ManufacturerBean() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return name;
	}
}
