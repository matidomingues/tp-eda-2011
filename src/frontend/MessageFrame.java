package frontend;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Esta clase se utiliza al momento de tener que comunicar un mensaje
 * al usuario, en funcion del evento obtenido, esta clase recibe un 
 * mensaje que sera mostrado al usuario en la pantalla.
 *
 */

public class MessageFrame extends JFrame {

	private static final long serialVersionUID = 1L;	
	/**
	 * Aca se construye el mensaje, recibe el string con el mensaje
	 * y lo muestra en pantalla. Tiene un boton para aceptar y continuar
	 * el juego en el estado que se deba.
	 * 
	 * @param mensaje mensaje con el texto a mostrar.
	 */
	
	public MessageFrame(String mensaje) {
		setTitle("Reversi");
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450, 400);
		setSize(new Dimension(400,140));
		
		JLabel label = new JLabel(mensaje);
		label.setBounds(50, 0, 300, 50);
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBounds(150, 60, 100, 30);
		aceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		add(label);
		add(aceptar);
	}
	
}