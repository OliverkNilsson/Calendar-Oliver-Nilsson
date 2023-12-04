import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Week implements ActionListener {

    private JFrame main;
    private JPanel day;
    private JPanel buttonPanel;
    private JTextField[] textFields;
    private JPanel[] eventPanels;

    Week() {

        // Gör frame att ha alla dagar i
        main = new JFrame();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setTitle("WEEK");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        weekDays();

        main.setVisible(true);
    }

    public void weekDays() {
        // Använder GridLayout för att göra så panelerna lägger sig i 7 st kolumner
        main.setLayout(new GridLayout(0, 7));
        // Gör en Array för att namnge dagarna i min titledBorder
        String[] weekDays = {"MÅNDAG", "TISDAG", "ONSDAG", "TORSDAG", "FREDAG", "LÖRDAG", "SÖNDAG", "MÅNDAG", "TISDAG", "ONSDAG", "TORSDAG", "FREDAG", "LÖRDAG", "SÖNDAG"};

        eventPanels = new JPanel[7];

        textFields = new JTextField[7];

        // for-loop för att skapa 7 st paneler till dagarna
        // Här hämtar jag även dagens datum och gör sedan om till datum på mån-sön
        for (int i = 0; i < 7; i++) {

            day = new JPanel();
            day.setLayout(new BorderLayout());
            day.setBorder(BorderFactory.createTitledBorder(weekDays[i]));
            main.add(day);

            eventPanels[i] = new JPanel();
            eventPanels[i].setLayout(new BoxLayout(eventPanels[i], BoxLayout.Y_AXIS));
            day.add(eventPanels[i], BorderLayout.CENTER);

            // Skapar en panel för button/text-field så man kan lägga de i SOUTH
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            day.add(buttonPanel, BorderLayout.SOUTH);

            JButton button = new JButton("Lägg till");
            button.setActionCommand(String.valueOf(i));
            button.addActionListener(this);
            buttonPanel.add(button, BorderLayout.SOUTH);

            // Skapar mina textFields där jag tar text ifrån till att göra event i kalendern
            // Lägger till dom i min Array "textFields" så jag kan urskilja dom
            JTextField event = new JTextField();
            textFields[i] = event;
            buttonPanel.add(event);

            // Hämtar dagens datum
            LocalDate now = LocalDate.now();
            // Hämtar vilken dag på veckan det är och gör uträkning för att få datum för mån-sön
            DayOfWeek currentDate = now.getDayOfWeek();
            LocalDate date = now.minusDays(currentDate.getValue() - i-1);

            // Använder "DateTimeFormatter" för att skriva ut "datum månadsnamn" ist för "år-månad-dag"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL");

            // Lägger till en på varje dag med datumet för dagen
            JLabel dateOfDay = new JLabel(String.valueOf(date.format(formatter)));
            day.add(dateOfDay, BorderLayout.NORTH);

            // Använder en if-sats för att kolla om "now"(tiden/datumet just nu) är samma som "date"(datumet för dagen)
            // Om de är true lägger jag till en markering på den dagen
            if(now == date) {
                day.setBackground(Color.GREEN);
                eventPanels[i].setBackground(Color.GREEN);
            }

        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {

        // Gör om String-värdet som genereras fram i for-loopen till en int(buttonNumber)
        int buttonNumber = Integer.parseInt(e.getActionCommand());

        // Använder sen "buttonNumber" för att bestämma så varje knapp lägger till på rätt panel(eventPanels)
        String textField = textFields[buttonNumber].getText();
        JLabel eventText = new JLabel(textField);
        eventText.setAlignmentX(Component.CENTER_ALIGNMENT);
        eventPanels[buttonNumber].add(eventText);

        // Återstället textField så man kan fylla i ny händelse
        textFields[buttonNumber].setText("");


        main.revalidate();
        main.repaint();

    }
}
