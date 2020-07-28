package datas;

public class donneesNutritionnelles {
	
	//Public obligé pour le moment pour parcourir les attributs de la classe dynamiquement
	//TO DO Trouver une technique pour les passer en private mais pouvoir parcourir les attributs de la classe 
	public double energie100g;
	public double graisse100g;
	public double sucres100g;
	public double fibres100g;
	public double proteines100g;
	public double sel100g;
	public double vitA100g;
	public double vitD100g; 
	public double VitE100g;
	public double vitK100g;
	public double vitC100g;
	public double vitB1_100g;
	public double vitB2_100g;
	public double vitPP100g;
	public double vitB6100g;
	public double vitB9100g;
	public double vitB12100g;
	public double calcium100g;
	public double magnesium100g;
	public double irong100g; // En double, à check pour pour opti
	public double fer100g;
	public double betaCarotene100g;
	public double presenceHuilePalme;

	
	
	
	public donneesNutritionnelles() {}




	public donneesNutritionnelles(double energie100g, double graisse100g, double sucres100g, double fibres100g,
			double proteines100g, double sel100g, double vitA100g, double vitD100g, double vitE100g, double vitK100g,
			double vitC100g, double vitB1_100g, double vitB2_100g, double vitPP100g, double vitB6100g, double vitB9100g,
			double vitB12100g, double calcium100g, double magnesium100g, double irong100g, double fer100g,
			double betaCarotene100g, double presenceHuilePalme) {
		super();
		this.energie100g = energie100g;
		this.graisse100g = graisse100g;
		this.sucres100g = sucres100g;
		this.fibres100g = fibres100g;
		this.proteines100g = proteines100g;
		this.sel100g = sel100g;
		this.vitA100g = vitA100g;
		this.vitD100g = vitD100g;
		this.VitE100g = vitE100g;
		this.vitK100g = vitK100g;
		this.vitC100g = vitC100g;
		this.vitB1_100g = vitB1_100g;
		this.vitB2_100g = vitB2_100g;
		this.vitPP100g = vitPP100g;
		this.vitB6100g = vitB6100g;
		this.vitB9100g = vitB9100g;
		this.vitB12100g = vitB12100g;
		this.calcium100g = calcium100g;
		this.magnesium100g = magnesium100g;
		this.irong100g = irong100g;
		this.fer100g = fer100g;
		this.betaCarotene100g = betaCarotene100g;
		this.presenceHuilePalme = presenceHuilePalme;
	}




	@Override
	public String toString() {
		return "donneesNutritionnelles [energie100g=" + energie100g + ", graisse100g=" + graisse100g + ", sucres100g="
				+ sucres100g + ", fibres100g=" + fibres100g + ", proteines100g=" + proteines100g + ", sel100g="
				+ sel100g + ", vitA100g=" + vitA100g + ", vitD100g=" + vitD100g + ", VitE100g=" + VitE100g
				+ ", vitK100g=" + vitK100g + ", vitC100g=" + vitC100g + ", vitB1_100g=" + vitB1_100g + ", vitB2_100g="
				+ vitB2_100g + ", vitPP100g=" + vitPP100g + ", vitB6100g=" + vitB6100g + ", vitB9100g=" + vitB9100g
				+ ", vitB12100g=" + vitB12100g + ", calcium100g=" + calcium100g + ", magnesium100g=" + magnesium100g
				+ ", irong100g=" + irong100g + ", fer100g=" + fer100g + ", betaCarotene100g=" + betaCarotene100g
				+ ", presenceHuilePalme=" + presenceHuilePalme + "]";
	};
	
	
	

}
