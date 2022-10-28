import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/*
    -- The GUICalendar contains the GUI of a weekly Calendar. The user can see what the current day and date is,
    add customisable schedule events. It is also possible to set what time the event starts and also deleting
    the event by placing your mouseover and right-clicking.
 */
public class GUICalendar extends JFrame {
    private String newSpinnerValue;
    private int createDayPanel;
    private JTextArea eventText;
    private JPanel dayPanel, cBoxContainer;
    private JTextField dayTextField, favTextField;
    private JButton dayButton, favButton;
    private JSpinner spinner;
    private DefaultComboBoxModel<String> model;
    private JTextArea[] eventTextArr;

    GUICalendar() {
        // -- JFrame --
        super.setTitle("Week Calendar");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(null);
        super.setSize(1350, 800);
        super.setResizable(false);
        super.getContentPane().setBackground(Color.decode("#fff9e6"));
        ImageIcon image = new ImageIcon("image.png");
        super.setIconImage(image.getImage());

        /*
        -- Calling all methods that contains different components to create the JPanel layout &
            creating 7 panels in total --
        */
        for (createDayPanel = 1; createDayPanel < 8; createDayPanel++) {
            setPanelLayout();
            dayOfWeek();
            dayDate();
            setBgCurrentDay();
            textAreaSchedule();
            createDayTextField();
            createDayButton();
            addButtonListener(eventTextArr, dayTextField);
        }
        // -- Calling methods to create JComboBox & JSpinner --
        addComboBoxFav();
        showTimeSpinner();
        addButtonActionFav();
        setVisible(true);
    } // End of constructor


    // -- Method for setting panel layout & initializing JPanel  --
    private void setPanelLayout() {
        dayPanel = new JPanel();
        dayPanel.setBackground(Color.decode("#ffd9d9"));
        dayPanel.setLayout(new GridLayout(9, 1));
        dayPanel.setBounds(30 + (createDayPanel * 140), 20, 140, 540);
        super.add(dayPanel);
    }

    /* -- Declaring & initializing new JLabel & calling calculateDayOfWeek() method
     from Class DateAndWeekDay --
     */
    private void dayOfWeek() {
        JLabel dayOfWeekLabel = new JLabel(String.valueOf(DateAndWeekDay.calculateDayOfWeek(createDayPanel)), SwingConstants.CENTER);
        dayPanel.add(dayOfWeekLabel);
    }

    /* -- Declaring & initializing new JLabel & calling calculateDate() method
   from Class DateAndWeekDay --
    */
    private void dayDate() {
        JLabel dayDate = new JLabel(String.valueOf(DateAndWeekDay.calculateDate(createDayPanel)), SwingConstants.CENTER);
        dayPanel.add(dayDate);
    }

    // -- Sets new background color on today's date's dayPanel --
    private void setBgCurrentDay() {
        int today = LocalDate.now().getDayOfWeek().getValue();
        if (createDayPanel == today) {
            dayPanel.setBackground(Color.decode("#c6e6f7"));

        }
    }

    // -- Initializing new JTextArea array, adding eventText to array & implementing new MouseAdapter --
    private void textAreaSchedule() {
        eventTextArr = new JTextArea[5];

        for (int i = 0; i < 5; i++) {
            eventText = new JTextArea();
            eventTextArr[i] = eventText;
            // -- Gets source of where mouse was clicked at x eventText and performs actions --
            eventTextArr[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        eventText = (JTextArea) e.getSource();
                        eventText.setEditable(true);
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        eventText = (JTextArea) e.getSource();
                        eventText.setText("");
                    }
                }

                public void mouseExited(MouseEvent e) {
                    eventText.setEditable(false);
                }
            });
            eventTextArr[i].setLineWrap(true);
            eventTextArr[i].setEditable(false);
            eventText.setBackground(Color.decode("#ffd9d9"));
            eventText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dayPanel.add(eventText);
        } // End of For-Loop
    }

    // -- Initializing new JButton --
    private void createDayButton() {
        dayButton = new JButton("Add");
        dayButton.setBackground(Color.decode("#c6e6f7"));
        dayPanel.add(dayButton);
    }

    // -- Initializing new TextField --
    private void createDayTextField() {
        dayTextField = new JTextField();
        dayTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dayPanel.add(dayTextField);
    }

    // -- ComboBox components, creating new vector & adding components to new JPanel container --
    private void addComboBoxFav() {
        Vector<String> comboBoxItems = new Vector<>();
        model = new DefaultComboBoxModel<>(comboBoxItems);
        model.addElement("");
        cBoxContainer = new JPanel();
        JComboBox<String> favouritesCB = new JComboBox<>(model);

        favTextField = new JTextField();
        favButton = new JButton("Add to favourites");

        cBoxContainer.setLayout(null);
        cBoxContainer.setBackground(Color.decode("#fff9e6"));
        cBoxContainer.setBounds(180, 560, 950, 160);

        favouritesCB.setBounds(260, 5, 200, 40);
        favTextField.setBounds(380, 50, 200, 40);
        favButton.setBounds(380, 90, 200, 40);
        favButton.setBackground(Color.decode("#c6e6f7"));

        cBoxContainer.add(favouritesCB);
        cBoxContainer.add(favTextField);
        cBoxContainer.add(favButton);
        add(cBoxContainer);
    }

    // -- Creating a JSpinner, set layout and model of Spinner --
    private void showTimeSpinner() {
        Date date = new Date();
        SpinnerDateModel spinnerModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor edit = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        spinner.setEditor(edit);
        spinner.setBounds(480, 5, 130, 40);
        spinner.setFont(new Font("Times", Font.PLAIN, 20));
        cBoxContainer.add(spinner);
    }

    // -- Formatting date value to show time only & retrieving current (time) --
    private void newSpinnerValue() {
        Date value = (Date) spinner.getValue();
        newSpinnerValue = new SimpleDateFormat("HH:mm").format(value);
    }

    // -- If array index is empty -> set text --
    private void addButtonListener(JTextArea[] eventTextArr, JTextField dayTextField) {
        dayButton.addActionListener(e -> {
            newSpinnerValue();
            for (int i = 0; i < 5; i++) {
                if (eventTextArr[i].getText().length() == 0) {
                    eventTextArr[i].setText((newSpinnerValue + " - " + model.getSelectedItem() + "\n" + dayTextField.getText()));
                    break;
                }
            }
            dayTextField.setText("");
        });
    }

    private void addButtonActionFav() {
        favButton.addActionListener(ea -> {
            model.addElement(favTextField.getText());
            favTextField.setText("");
        });
    }
} // End of Class


