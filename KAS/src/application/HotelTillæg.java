package application;

public class HotelTillęg {
	private String navn;
	private double pris;

	public HotelTillęg(String navn, double pris) {
		this.navn = navn;
		this.pris = pris;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

}
