public class CurrencyChecker{
	private static Elements fetchData(String link, String way) {
        	Elements tr = null;
        	try {
            		Connection connect = Jsoup.connect(link).timeout(5000);
            		Document document = connect.get();
            		tr = document.select(way);
       		} catch (IOException e) {
        		System.err.println("Błąd podczas pobierania danych z strony kantoru: " + e.getMessage());
        	}
        	return tr;
	}

	public static void main(String[] args) {
	

	}

}
