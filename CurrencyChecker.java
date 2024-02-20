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
	private static void drawTable(Elements tr, String name) {
            String[] columnNames = {"Waluta", "Nazwa Waluty", "Ilość", "Sprzedaż", "Kupno"};
            DefaultTableModel model = new DefaultTableModel(null, columnNames);
            
        try {
            for (int i = 0; i < tr.size(); i++) {
                Elements td = tr.get(i).select("td");
                if (td.text() == null) {
                    continue;
                }
                if (td.size() < 1) {
                    System.out.println("Brak kolumny z wartosciami!");
                }
                String[] rowData = new String[td.size()];
                for (int j = 0; j < td.size(); j++) {
                    if (td.get(j).text().equals("")) {
                        rowData[j] = "0";
                        continue;
                    }
                    rowData[j] = td.get(j).text();
                }
                model.addRow(rowData);
            }

            JFrame f;
            JTable jt;
            f = new JFrame();
            f.setTitle(name);
            jt = new JTable(model);
            JScrollPane sp = new JScrollPane(jt);
            f.add(sp);
            f.pack();
            f.setVisible(true);
       	} catch (Exception err) {
        	System.err.println("Something go wrong " + err.getMessage());
        	}
	}

	public static void main(String[] args) {
	

	}

}
