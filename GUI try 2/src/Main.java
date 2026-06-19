import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("Benutzerformular");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Textfeld
        JTextField name = new JTextField();
        panel.add(new JLabel("Name: "));
        panel.add(name);


        /*
        //Auswahlmenü
        JSpinner alter = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        panel.add(new JLabel("Alter:"));
        panel.add(alter);


         */

        JButton abschicken = new JButton("Abschicken");

        //Textbereich
        JTextArea ergebnisse = new JTextArea();

        abschicken.addActionListener(e->{
            ergebnisse.setText("Ergebnis:\n\n");
            ergebnisse.append("Name: " + name.getText() + "\n");
        });


        frame.add(panel, BorderLayout.NORTH);
        frame.add(abschicken, BorderLayout.CENTER);
        frame.add(ergebnisse, BorderLayout.SOUTH);

        frame.setVisible(true);

    }
}