package Exceptions;

import javax.swing.JTextArea;

/**
 * Klasse implementiert die Funktionalität zur Ausgabe einer Exception mit dem
 * dazugehörigem Stacktrace. Es gibt die Möglichkeit die Ausgabe über die Konsole und
 * über die ein übergebenes Objekt des Typens JTextArea zu erstellen. Um dieses Klasse
 * nutzen zu können muss keine Instanz erzeugt werden.
 * @author Markus Eberl
 * @version 1.0.0
 */
public class CatchPrinter {
	
	/**
	 * Methode gibt den Stacktrace einer Exception auf der Konsole aus.
	 * @param ex Exception dessen Stacktrace ausgegeben werden soll.
	 */
	public static void printException(Exception ex) {
		System.out.println(ex.getMessage());
		System.out.println(ex.getStackTrace());
		System.out.println("StackTrace");
		for(StackTraceElement element : ex.getStackTrace()) {
			System.out.println("Klasse: " + element.getClassName());
			System.out.println("Methode: " + element.getMethodName());
			System.out.println("Zeile: " + element.getLineNumber());
		}
	}
	
	/**
	 * Methode gibt den Stacktrace einer Exception auf der Konsole und übergebener Textarea aus.
	 * @param ex Exception dessen Stacktrace ausgegeben werden soll.
	 * @param textArea Referenz der TextArea auf die auch eine Ausgabe adressiert ist.
	 */
	public static void printException(Exception ex, JTextArea textArea) {
		System.out.println(ex.getMessage());
		textArea.append(ex.getMessage());
		System.out.println(ex.getStackTrace());
		textArea.append(ex.getStackTrace().toString());
		System.out.println("StackTrace");
		textArea.append("Stacktrace");
		for(StackTraceElement element : ex.getStackTrace()) {
			System.out.println("Klasse: " + element.getClassName());
			textArea.append("Klasse: " + element.getClassName());
			System.out.println("Methode: " + element.getMethodName());
			textArea.append("Methode: " + element.getMethodName());
			System.out.println("Zeile: " + element.getLineNumber());
			textArea.append("Zeile: " + element.getLineNumber());
		}
	}

}
