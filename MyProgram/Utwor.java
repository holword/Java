package MyProgram;

class Utwor implements Comparable<Utwor> {
	private String tytulUtworu;
	private String wykonawca;
	private int rokWydania;

	Utwor(String tytulUtworu, String wykonawca, int rokWydania) throws Exception {
		if (tytulUtworu.isBlank() == true || wykonawca.isBlank() == true) {
			throw new Exception("Pole nie może być puste");
		}
		if (rokWydania < 1900 || rokWydania > 2024) {
			throw new Exception("Rok wydania musi się zawierać między 1900 a rokiem obecnym");
		}
		this.tytulUtworu = tytulUtworu;
		this.wykonawca = wykonawca;
		this.rokWydania = rokWydania;
	}

	public String getTytulUtworu() {
		return tytulUtworu;
	}

	public String getWykonawca() {
		return wykonawca;
	}

	public int getRokWydania() {
		return rokWydania;
	}

	public String tytulPelny() {
		String rok = Integer.toString(rokWydania);

		return tytulUtworu + " " + "(" + wykonawca + ")" + " " + "[" + rok + "]";
	}

	@Override
	public int compareTo(Utwor o) {
		if (o == null) {
			throw new NullPointerException("The object to compare to is null.");
		}
		
		String title1 = this.getTytulUtworu();
		String title2 = o.getTytulUtworu();

		if (title1 == null && title2 == null) {
			return 0;
		} else if (title1 == null) {
			return -1;
		} else if (title2 == null) {
			return 1;
		} else {
			return title1.compareToIgnoreCase(title2);
		}

	}

	@Override
	public String toString() {
		return "%s, (%s), [%d]".formatted(tytulUtworu, wykonawca, rokWydania); // %d trzeba bo %i nie jest obsługiwany
	}

}
