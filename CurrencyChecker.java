public class CurrencyChecker{
	private static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void createAndShowGUI() {
        ActionListener myActionListener = e -> pool.execute(() -> fetchDataAndDrawTable(
                "https://www.baksy.pl/kurs-walut?fbclid=IwAR2ofHnrbpu7HpJhNQjxMxdnbpem0DdJP98eRdoDkub9h-1gPrhmOw19wbQ",
                "table.rate-table tbody tr",
                "Kantor Baksy"
        ));

        ActionListener myActionListener1 = e -> pool.execute(() -> fetchDataAndDrawTable(
                "http://kantorwielopole.pl/pl/kursy-walut/?fbclid=IwAR2c2H1FrgVfH7xFXsLSP4QaWFoZ5unRHk_q9-UPdaxuSErdBAf5yGzOHCs",
                ".kursy tbody tr:not(:first-child)",
                "Kantor Wielopole"
        ));

        JFrame jf = new JFrame("Kursy Walut");
        jf.setLayout(new FlowLayout());

        JButton jb = new JButton("Kantor Baksy");
        jb.addActionListener(myActionListener);
        jf.getContentPane().add(jb);

        JButton jb2 = new JButton("Kantor Wielopole");
        jb2.addActionListener(myActionListener1);
        jf.getContentPane().add(jb2);

        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //wyśrodkowanie aplikacji 
	             
        int width =400;
	int height =300;
	jf.setSize(width,height);
    	int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	int x = (screenWidth - jf.getWidth()) / 2;
	int y = (screenHeight - jf.getHeight()) / 2;
	jf.setLocation(x, y);
    }
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
		javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
	}

}
